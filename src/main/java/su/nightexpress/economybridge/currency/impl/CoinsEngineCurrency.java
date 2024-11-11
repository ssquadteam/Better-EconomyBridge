package su.nightexpress.economybridge.currency.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.CurrencyId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class CoinsEngineCurrency implements Currency {

    private final String id;
    private final su.nightexpress.coinsengine.api.currency.Currency currency;

    public CoinsEngineCurrency(@NotNull su.nightexpress.coinsengine.api.currency.Currency currency) {
        this.id = CurrencyId.forCoinsEngine(currency.getId());
        this.currency = currency;
    }

    @NotNull
    public static Set<CoinsEngineCurrency> getCurrencies() {
        Set<CoinsEngineCurrency> currencies = new HashSet<>();
        CoinsEngineAPI.getCurrencyManager().getCurrencies().forEach(currency -> {
            if (!currency.isVaultEconomy()) {
                currencies.add(new CoinsEngineCurrency(currency));
            }
        });
        return currencies;
    }

    @Override
    @NotNull
    public UnaryOperator<String> replacePlaceholders() {
        return this.currency.replacePlaceholders();
    }

    @Override
    public boolean canHandleDecimals() {
        return this.currency.isDecimal();
    }

    @Override
    public boolean canHandleOffline() {
        return true;
    }

    @Override
    @NotNull
    public String formatValue(double amount) {
        return this.currency.formatValue(amount);
    }

    @Override
    @NotNull
    public String getOriginalId() {
        return this.currency.getId();
    }

    @Override
    @NotNull
    public String getInternalId() {
        return this.id;
    }

    @Override
    @NotNull
    public String getName() {
        return this.currency.getName();
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return this.currency.getName();
    }

    @Override
    @NotNull
    public String getFormat() {
        return this.currency.getFormat();
    }

    @Override
    @NotNull
    public String getDefaultFormat() {
        return this.currency.getFormat();
    }

    @Override
    @NotNull
    public ItemStack getIcon() {
        return this.currency.getIcon();
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return this.currency.getIcon();
    }



    @Override
    public double getBalance(@NotNull Player player) {
        return CoinsEngineAPI.getBalance(player, this.currency);
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return CoinsEngineAPI.getBalance(playerId, this.currency);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        CoinsEngineAPI.addBalance(player, this.currency, amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        CoinsEngineAPI.addBalance(playerId, this.currency, amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        CoinsEngineAPI.removeBalance(player, this.currency, amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        CoinsEngineAPI.removeBalance(playerId, this.currency, amount);
    }
}
