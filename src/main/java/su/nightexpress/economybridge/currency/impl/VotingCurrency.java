package su.nightexpress.economybridge.currency.impl;

import com.bencodez.votingplugin.VotingPluginHooks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.EconomyBridge;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.UUID;

public class VotingCurrency extends AbstractCurrency {

    public VotingCurrency(@NotNull String id) {
        super(id);
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
        return "Voting Points";
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.SUNFLOWER);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(player).getPoints();
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(playerId).getPoints();
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        EconomyBridge.getPlugin().runTaskAsync(() -> {
            VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(player).addPoints((int) amount);
        });
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        EconomyBridge.getPlugin().runTaskAsync(() -> {
            VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(playerId).addPoints((int) amount);
        });
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(player).removePoints((int) amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        VotingPluginHooks.getInstance().getUserManager().getVotingPluginUser(playerId).removePoints((int) amount);
    }
}
