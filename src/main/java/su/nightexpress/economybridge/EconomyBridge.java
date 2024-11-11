package su.nightexpress.economybridge;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.CurrencyManager;
import su.nightexpress.economybridge.currency.impl.DummyCurrency;

import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class EconomyBridge {

    private static BridgePlugin plugin;

    static void load(@NotNull BridgePlugin origin) {
        plugin = origin;
    }

    static void unload() {
        plugin = null;
    }

    @NotNull
    public static BridgePlugin getPlugin() {
        return plugin;
    }

    public static boolean hasCurrency(@NotNull String id) {
        return getCurrency(id) != null;
    }

    public static boolean handle(@NotNull String id, @NotNull Consumer<Currency> consumer) {
        Currency currency = getCurrency(id);
        if (currency == null) return false;

        consumer.accept(currency);
        return true;
    }

    public static boolean hasEnough(@NotNull Player player, @NotNull String id, double amount) {
        return getBalance(player, id) >= amount;
    }

    public static boolean hasEnough(@NotNull UUID playerId, @NotNull String id, double amount) {
        return getBalance(playerId, id) >= amount;
    }

    public static double getBalance(@NotNull Player player, @NotNull String id) {
        Currency currency = getCurrency(id);
        return currency == null ? 0D : currency.getBalance(player);
    }

    public static double getBalance(@NotNull UUID playerId, @NotNull String id) {
        Currency currency = getCurrency(id);
        return currency == null ? 0D : currency.getBalance(playerId);
    }

    public static boolean deposit(@NotNull Player player, @NotNull String id, double amount) {
        return handle(id, currency -> currency.give(player, amount));
    }

    public static boolean deposit(@NotNull UUID playerId, @NotNull String id, double amount) {
        return handle(id, currency -> currency.give(playerId, amount));
    }

    public static boolean withdraw(@NotNull Player player, @NotNull String id, double amount) {
        return handle(id, currency -> currency.take(player, amount));
    }

    public static boolean withdraw(@NotNull UUID playerId, @NotNull String id, double amount) {
        return handle(id, currency -> currency.take(playerId, amount));
    }



    public static boolean hasCurrency() {
        return getCurrencyManager().hasCurrency();
    }

    @NotNull
    public static CurrencyManager getCurrencyManager() {
        return plugin.getCurrencyManager();
    }

    @NotNull
    public static Set<Currency> getCurrencies() {
        return getCurrencyManager().getCurrencies();
    }

    @NotNull
    public static Set<String> getCurrencyIds() {
        return getCurrencyManager().getCurrencyIds();
    }

    @Nullable
    public static Currency getCurrency(@NotNull String internalId) {
        return getCurrencyManager().getCurrency(internalId);
    }

    @NotNull
    public static Currency getCurrencyOrDummy(@NotNull String internalId) {
        return getCurrencyManager().getCurrencyOrDummy(internalId);
    }

    @NotNull
    public static DummyCurrency getDummyCurrency() {
        return CurrencyManager.DUMMY_CURRENCY;
    }
}
