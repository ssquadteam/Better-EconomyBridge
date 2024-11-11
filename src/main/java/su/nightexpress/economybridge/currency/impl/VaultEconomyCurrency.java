package su.nightexpress.economybridge.currency.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.Placeholders;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;
import su.nightexpress.nightcore.integration.VaultHook;

import java.util.UUID;

public class VaultEconomyCurrency extends AbstractCurrency {

    public VaultEconomyCurrency(@NotNull String id) {
        super(id);
    }

    @Override
    public boolean canHandleDecimals() {
        return true;
    }

    @Override
    public boolean canHandleOffline() {
        return true;
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return "Money";
    }

    @Override
    @NotNull
    public String getDefaultFormat() {
        return "$" + Placeholders.GENERIC_AMOUNT;
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.GOLD_NUGGET);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return VaultHook.getBalance(player);
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        return VaultHook.getBalance(offlinePlayer);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        VaultHook.deposit(player, amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        VaultHook.deposit(offlinePlayer, amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        VaultHook.withdraw(player, amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        VaultHook.withdraw(offlinePlayer, amount);
    }
}
