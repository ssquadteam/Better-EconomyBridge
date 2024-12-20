package su.nightexpress.economybridge.currency;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.EconomyBridge;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.impl.*;
import su.nightexpress.economybridge.currency.listener.CurrencyListener;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;
import su.nightexpress.economybridge.hook.VaultHook;
import su.nightexpress.nightcore.config.FileConfig;
import su.nightexpress.nightcore.manager.AbstractManager;
import su.nightexpress.nightcore.util.ItemNbt;
import su.nightexpress.nightcore.util.Plugins;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CurrencyManager extends AbstractManager<BridgePlugin> {

    public static final String FILE_CURRENCIES = "currencies.yml";
    public static final String FILE_ITEMS      = "items.yml";

    public static final DummyCurrency DUMMY_CURRENCY = new DummyCurrency();

    private final Map<String, Currency> currencyMap;
    private final Map<String, Runnable> pluginProviders;

    private FileConfig currencyConfig;
    private FileConfig itemsConfig;

    public CurrencyManager(@NotNull BridgePlugin plugin) {
        super(plugin);
        this.currencyMap = new HashMap<>();
        this.pluginProviders = new HashMap<>();
    }

    @NotNull
    public FileConfig getItemsConfig() {
        if (this.itemsConfig == null) {
            this.itemsConfig = FileConfig.loadOrExtract(this.plugin, FILE_ITEMS);
        }
        return this.itemsConfig;
    }

    @NotNull
    public FileConfig getCurrenciesConfig() {
        if (this.currencyConfig == null) {
            this.currencyConfig = FileConfig.loadOrExtract(this.plugin, FILE_CURRENCIES);
        }
        return this.currencyConfig;
    }

    @Override
    protected void onLoad() {
        this.loadHooks();
        this.loadProviders();
        this.loadBuiltInCurrencies();
        this.loadItemCurrencies();

        this.currencyConfig.saveChanges();
        this.itemsConfig.saveChanges();

        this.addListener(new CurrencyListener(this.plugin, this));

        // Clean up when all plugins are loaded.
        this.plugin.runTask(task -> this.pluginProviders.clear());
    }

    @Override
    protected void onShutdown() {
        this.currencyMap.clear();
        this.pluginProviders.clear();

        if (Plugins.hasVault()) {
            VaultHook.shutdown();
        }
    }

    public void handlePluginLoad(@NotNull String pluginName) {
        var provider = this.pluginProviders.get(pluginName);
        if (provider != null) {
            plugin.info(pluginName + " detected! Loading currency...");
            provider.run();
        }
    }

    private void loadHooks() {
        if (Plugins.hasVault()) {
            VaultHook.setupEconomy();
        }
    }

    private void loadProviders() {
        this.pluginProviders.put(CurrencyPlugins.PLAYER_POINTS, () -> this.loadCurrency(CurrencyId.PLAYER_POINTS, PlayerPointsCurrency::new));
        this.pluginProviders.put(CurrencyPlugins.BEAST_TOKENS, () -> this.loadCurrency(CurrencyId.BEAST_TOKENS, BeastTokensCurrency::new));
        this.pluginProviders.put(CurrencyPlugins.VOTING_PLUGIN, () -> this.loadCurrency(CurrencyId.VOTING_PLUGIN, VotingCurrency::new));
        this.pluginProviders.put(CurrencyPlugins.ELITEMOBS, () -> this.loadCurrency(CurrencyId.ELITE_MOBS, EliteMobsCurrency::new));

        this.pluginProviders.put(Plugins.VAULT, () -> {
            if (VaultHook.hasEconomy()) {
                this.loadCurrency(CurrencyId.VAULT, VaultEconomyCurrency::new);
            }
        });

        this.pluginProviders.put(CurrencyPlugins.COINS_ENGINE, () -> {
            CoinsEngineCurrency.getCurrencies().forEach(this::registerCurrency);
        });

        this.pluginProviders.put(CurrencyPlugins.ULTRA_ECONOMY, () -> {
            UltraEconomyCurrency.getCurrencies().forEach(this::registerCurrency);
        });

        // Try load any provider(s) of the plugins that are already enabled aka loaded.
        this.pluginProviders.keySet().forEach(pluginName -> {
            Plugin currencyPlugin = this.plugin.getPluginManager().getPlugin(pluginName);
            if (currencyPlugin == null || !currencyPlugin.isEnabled()) return;

            this.handlePluginLoad(pluginName);
        });
    }

    public void loadBuiltInCurrencies() {
        this.loadCurrency(CurrencyId.XP_POINTS, XPPointsCurrency::new);
        this.loadCurrency(CurrencyId.XP_LEVELS, XPLevelsCurrency::new);
    }

    public void loadItemCurrencies() {
        FileConfig config = this.getItemsConfig();

        if (!config.contains("Items")) {
            config.set("Items.gold.Value", ItemNbt.getTagString(new ItemStack(Material.GOLD_INGOT)));
            config.set("Items.diamond.Value", ItemNbt.getTagString(new ItemStack(Material.DIAMOND)));
            config.set("Items.emerald.Value", ItemNbt.getTagString(new ItemStack(Material.EMERALD)));
        }

        config.getSection("Items").forEach(id -> {
            String tag = config.getString("Items." + id + ".Value");
            if (tag == null || tag.isBlank()) return;

            ItemStack itemStack = ItemNbt.fromTagString(tag);
            if (itemStack == null) {
                this.plugin.error("[" + FILE_ITEMS + "] Could not decode NBT tag '" + tag + "' for '" + id + "' item.");
                return;
            }

            this.loadCurrency(id, id2 -> new ItemStackCurrency(id2, itemStack));
        });
    }

    public void saveCurrency(@NotNull ItemStackCurrency currency) {
        FileConfig config = this.getItemsConfig();

        String id = currency.getInternalId();
        String path = "Items." + id;

        config.set(path + ".Value", ItemNbt.getTagString(currency.getItem()));
        config.save();
    }

    public void loadCurrency(@NotNull String pluginName, @NotNull String id, @NotNull Function<String, AbstractCurrency> function) {
        if (!Plugins.isInstalled(pluginName)) return;

        this.loadCurrency(id, function);
    }

    public void loadCurrency(@NotNull String id, @NotNull Function<String, AbstractCurrency> function) {
        AbstractCurrency currency = function.apply(id);

        this.loadCurrency(currency);
    }

    public void loadCurrency(@NotNull AbstractCurrency currency) {
        CurrencySettings settings = CurrencySettings.fromDefaults(currency);

        this.loadCurrency(currency, settings);
    }

    public void loadCurrency(@NotNull AbstractCurrency currency, @NotNull CurrencySettings settings) {
        String id = currency.getInternalId();
        if (EconomyBridge.isDisabled(id)) return;

        settings.load(this.getCurrenciesConfig(), "Currencies." + id);
        currency.load(settings);

        this.registerCurrency(currency);
    }

    public void registerCurrency(@NotNull Currency currency) {
        String id = currency.getInternalId();
        if (EconomyBridge.isDisabled(id)) return;

        this.currencyMap.put(id, currency);
        this.plugin.info("Currency registered: '" + id + "'.");
    }

    public boolean hasCurrency() {
        return !this.currencyMap.isEmpty();
    }

    @NotNull
    public Map<String, Currency> getCurrencyMap() {
        return this.currencyMap;
    }

    @NotNull
    public Set<Currency> getCurrencies() {
        return new HashSet<>(this.currencyMap.values());
    }

    @NotNull
    public Set<String> getCurrencyIds() {
        return new HashSet<>(this.currencyMap.keySet());
    }

    @Nullable
    public Currency getCurrency(@NotNull String id) {
        return this.currencyMap.get(id.toLowerCase());
    }

    @NotNull
    public Currency getCurrencyOrDummy(@NotNull String id) {
        return this.currencyMap.getOrDefault(id.toLowerCase(), DUMMY_CURRENCY);
    }
}
