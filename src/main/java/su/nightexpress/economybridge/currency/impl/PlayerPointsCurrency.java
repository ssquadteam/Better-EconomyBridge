package su.nightexpress.economybridge.currency.impl;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.UUID;

public class PlayerPointsCurrency extends AbstractCurrency {

    public static final String ID = "playerpoints";

    private final PlayerPointsAPI api;

    public PlayerPointsCurrency(@NotNull String id) {
        super(id);
        this.api = PlayerPoints.getInstance().getAPI();
    }

    @Override
    public boolean canHandleDecimals() {
        return false;
    }

    @Override
    public boolean canHandleOffline() {
        return true;
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return "Points";
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.SUNFLOWER);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return this.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return this.api.look(playerId);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        this.give(player.getUniqueId(), amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        this.api.give(playerId, (int) amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        this.take(player.getUniqueId(), amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        this.api.take(playerId, (int) amount);
    }
}
