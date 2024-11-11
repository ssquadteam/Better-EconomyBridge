package su.nightexpress.economybridge.currency;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.impl.*;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;
import su.nightexpress.nightcore.config.FileConfig;
import su.nightexpress.nightcore.integration.VaultHook;
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

    private FileConfig currencyConfig;
    private FileConfig itemsConfig;

    public CurrencyManager(@NotNull BridgePlugin plugin) {
        super(plugin);
        this.currencyMap = new HashMap<>();
    }

    @Override
    protected void onLoad() {
        this.loadCurrencies();
        this.loadItems();

        this.currencyConfig.saveChanges();
        this.itemsConfig.saveChanges();

        this.plugin.info("Loaded " + this.currencyMap.size() + " currencies.");
    }

    @Override
    protected void onShutdown() {
        this.currencyMap.clear();
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

    public void loadCurrencies() {
        if (Plugins.hasVault() && VaultHook.hasEconomy()) {
            this.loadCurrency(CurrencyId.VAULT, VaultEconomyCurrency::new);
        }

        this.loadCurrency(CurrencyId.XP_POINTS, XPPointsCurrency::new);
        this.loadCurrency(CurrencyId.XP_LEVELS, XPLevelsCurrency::new);

        this.loadCurrency(CurrencyPlugins.PLAYER_POINTS, CurrencyId.PLAYER_POINTS, PlayerPointsCurrency::new);
        this.loadCurrency(CurrencyPlugins.BEAST_TOKENS, CurrencyId.BEAST_TOKENS, BeastTokensCurrency::new);
        this.loadCurrency(CurrencyPlugins.VOTING_PLUGIN, CurrencyId.VOTING_PLUGIN, VotingCurrency::new);
        this.loadCurrency(CurrencyPlugins.ELITEMOBS, CurrencyId.ELITE_MOBS, EliteMobsCurrency::new);

        if (Plugins.isInstalled(CurrencyPlugins.COINS_ENGINE)) {
            CoinsEngineCurrency.getCurrencies().forEach(this::registerCurrency);
        }

        if (Plugins.isInstalled(CurrencyPlugins.ULTRA_ECONOMY)) {
            UltraEconomyCurrency.getCurrencies().forEach(this::registerCurrency);
        }

        if (Plugins.isInstalled(CurrencyPlugins.GEMS_ECONOMY)) {
            GemsEconomyCurrency.getCurrencies().forEach(this::loadCurrency);
        }
    }

    public void loadItems() {
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

        settings.load(this.getCurrenciesConfig(), "Currencies." + id);
        currency.load(settings);

        this.registerCurrency(currency);
    }

    public void registerCurrency(@NotNull Currency currency) {
        this.currencyMap.put(currency.getInternalId(), currency);
        this.plugin.info("Registered currency: " + currency.getInternalId());
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
