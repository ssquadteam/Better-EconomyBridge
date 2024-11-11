package su.nightexpress.economybridge.currency.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.Placeholders;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.CurrencyId;

import java.util.UUID;

public class DummyCurrency implements Currency {

    @Override
    public boolean canHandleDecimals() {
        return true;
    }

    @Override
    public boolean canHandleOffline() {
        return false;
    }

    @Override
    @NotNull
    public String getInternalId() {
        return CurrencyId.DUMMY;
    }

    @Override
    @NotNull
    public String getOriginalId() {
        return CurrencyId.DUMMY;
    }

    @Override
    @NotNull
    public String getName() {
        return "Dummy";
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return this.getName();
    }

    @Override
    @NotNull
    public String getFormat() {
        return Placeholders.GENERIC_AMOUNT + " [Dummy]";
    }

    @Override
    @NotNull
    public String getDefaultFormat() {
        return this.getFormat();
    }

    @Override
    @NotNull
    public ItemStack getIcon() {
        return new ItemStack(Material.BRICK);
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.SUNFLOWER);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        return 0;
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return 0;
    }

    @Override
    public void give(@NotNull Player player, double amount) {

    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {

    }

    @Override
    public void take(@NotNull Player player, double amount) {

    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {

    }
}
