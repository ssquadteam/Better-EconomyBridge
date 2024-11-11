package su.nightexpress.economybridge.currency.impl;

import me.mraxetv.beasttokens.api.BeastTokensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.UUID;

public class BeastTokensCurrency extends AbstractCurrency {

    public BeastTokensCurrency(@NotNull String id) {
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
        return "Tokens";
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.SUNFLOWER);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return BeastTokensAPI.getTokensManager().getTokens(player);
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return BeastTokensAPI.getTokensManager().getTokens(Bukkit.getOfflinePlayer(playerId));
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        BeastTokensAPI.getTokensManager().addTokens(player, amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        BeastTokensAPI.getTokensManager().addTokens(Bukkit.getOfflinePlayer(playerId), amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        BeastTokensAPI.getTokensManager().removeTokens(player, amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        BeastTokensAPI.getTokensManager().removeTokens(Bukkit.getOfflinePlayer(playerId), amount);
    }
}
