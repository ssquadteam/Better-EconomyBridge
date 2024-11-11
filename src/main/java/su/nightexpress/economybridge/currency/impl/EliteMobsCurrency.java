package su.nightexpress.economybridge.currency.impl;

import com.magmaguy.elitemobs.economy.EconomyHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.UUID;

public class EliteMobsCurrency extends AbstractCurrency {

    public static final String ID = "elitemobs";

    public EliteMobsCurrency(@NotNull String id) {
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
        return "Elite Currency";
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.EMERALD);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return this.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return EconomyHandler.checkCurrency(playerId, true);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        this.give(player.getUniqueId(), amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        EconomyHandler.addCurrency(playerId, amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        this.take(player.getUniqueId(), amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        EconomyHandler.subtractCurrency(playerId, amount);
    }
}
