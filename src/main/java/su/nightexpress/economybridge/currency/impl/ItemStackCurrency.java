package su.nightexpress.economybridge.currency.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;
import su.nightexpress.nightcore.util.ItemUtil;
import su.nightexpress.nightcore.util.Players;

import java.util.UUID;

public class ItemStackCurrency extends AbstractCurrency {

    private ItemStack item;

    public ItemStackCurrency(@NotNull String id, @NotNull ItemStack item) {
        super(id);
        this.setItem(item);
    }

    @Override
    public boolean canHandleDecimals() {
        return false;
    }

    @Override
    public boolean canHandleOffline() {
        return false;
    }

    public void setItem(@NotNull ItemStack item) {
        this.item = new ItemStack(item);
    }

    @NotNull
    public ItemStack getItem() {
        return new ItemStack(this.item);
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return ItemUtil.getItemName(this.item);
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(this.item);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return Players.countItem(player, this.getItem());
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return 0;
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        Players.addItem(player, this.getItem(), (int) amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {

    }

    @Override
    public void take(@NotNull Player player, double amount) {
        Players.takeItem(player, this.getItem(), (int) amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {

    }
}
