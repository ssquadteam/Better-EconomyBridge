package su.nightexpress.economybridge.currency.impl;

import me.TechsCode.UltraEconomy.UltraEconomy;
import me.TechsCode.UltraEconomy.objects.Account;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.api.Currency;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UltraEconomyCurrency implements Currency {

    private final me.TechsCode.UltraEconomy.objects.Currency currency;

    public UltraEconomyCurrency(me.TechsCode.UltraEconomy.objects.Currency currency) {
        this.currency = currency;
    }

    @NotNull
    public static Set<UltraEconomyCurrency> getCurrencies() {
        return UltraEconomy.getAPI().getCurrencies().stream().map(UltraEconomyCurrency::new).collect(Collectors.toSet());
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
    public String format(double amount) {
        return this.currency.getFormat().format(amount);
    }

    @Override
    @NotNull
    public String formatValue(double amount) {
        return this.currency.getFormat().format(amount);
    }

    @Override
    @NotNull
    public String getOriginalId() {
        return this.currency.getKey();
    }

    @Override
    @NotNull
    public String getInternalId() {
        return ("ultraeconomy_" + this.currency.getName()).toLowerCase();
    }

    @Override
    @NotNull
    public String getName() {
        return this.currency.getName();
    }

    @Override
    @NotNull
    public String getDefaultName() {
        return this.getName();
    }

    @Override
    @NotNull
    public String getFormat() {
        return this.currency.getFormat().format(0.0);
    }

    @Override
    @NotNull
    public ItemStack getIcon() {
        return currency.getIcon().getAsItemStack().orElseGet(() -> new ItemStack(Material.GOLD_INGOT));
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return this.currency.getIcon().getAsItemStack().orElse(new ItemStack(Material.GOLD_INGOT));
    }



    @Override
    public double getBalance(@NotNull Player player) {
        return this.getAccount(player).map(account -> account.getBalance(currency).getOnHand()).orElse(0D);
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        return this.getAccount(playerId).map(account -> account.getBalance(currency).getOnHand()).orElse(0D);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        this.getAccount(player).ifPresent(account -> account.getBalance(currency).addHand(amount));
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        this.getAccount(playerId).ifPresent(account -> account.getBalance(currency).addHand(amount));
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        this.getAccount(player).ifPresent(account -> account.getBalance(currency).removeHand(amount));
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        this.getAccount(playerId).ifPresent(account -> account.getBalance(currency).removeHand(amount));
    }



    private Optional<Account> getAccount(@NotNull Player player) {
        return UltraEconomy.getAPI().getAccounts().name(player.getName());
    }

    private Optional<Account> getAccount(@NotNull UUID playerId) {
        return UltraEconomy.getAPI().getAccounts().uuid(playerId);
    }
}
