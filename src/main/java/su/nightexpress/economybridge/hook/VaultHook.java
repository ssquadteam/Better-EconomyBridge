package su.nightexpress.economybridge.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VaultHook {

    private static Economy economy;

    @Nullable
    private static <T> T getProvider(@NotNull Class<T> clazz) {
        RegisteredServiceProvider<T> provider = Bukkit.getServer().getServicesManager().getRegistration(clazz);
        return provider == null ? null : provider.getProvider();
    }

    public static boolean setupEconomy() {
        if (economy != null) return false;

        economy = getProvider(Economy.class);

        return economy != null;
    }

    public static void shutdown() {
        economy = null;
    }

    public static boolean hasEconomy() {
        return economy != null;
    }

    @Nullable
    public static Economy getEconomy() {
        return economy;
    }

    @NotNull
    public static String getEconomyName() {
        return hasEconomy() ? economy.getName() : "null";
    }

    public static double getBalance(@NotNull Player player) {
        return getBalance((OfflinePlayer) player);
    }

    public static double getBalance(@NotNull OfflinePlayer player) {
        return economy.getBalance(player);
    }

    public static boolean deposit(@NotNull Player player, double amount) {
        return deposit((OfflinePlayer) player, amount);
    }

    public static boolean deposit(@NotNull OfflinePlayer player, double amount) {
        return economy.depositPlayer(player, Math.abs(amount)).transactionSuccess();
    }

    public static boolean withdraw(@NotNull Player player, double amount) {
        return withdraw((OfflinePlayer) player, amount);
    }

    public static boolean withdraw(@NotNull OfflinePlayer player, double amount) {
        return economy.withdrawPlayer(player, Math.abs(amount)).transactionSuccess();
    }
}
