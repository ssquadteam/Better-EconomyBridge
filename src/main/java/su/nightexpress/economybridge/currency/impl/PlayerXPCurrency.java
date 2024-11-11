package su.nightexpress.economybridge.currency.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.UUID;

public class PlayerXPCurrency extends AbstractCurrency {

    public static final String ID = "xp";

    public PlayerXPCurrency(@NotNull String id) {
        super(id);
    }

    @Override
    public boolean canHandleDecimals() {
        return false;
    }

    @Override
    public boolean canHandleOffline() {
        return false;
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return "XP";
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.EXPERIENCE_BOTTLE);
    }

    private void modify(@NotNull Player player, double amount) {
        player.giveExp((int) amount);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return player.getTotalExperience();
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return 0;
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        this.modify(player, amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {

    }

    @Override
    public void take(@NotNull Player player, double amount) {
        this.modify(player, -amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {

    }
}
