package su.nightexpress.economybridge.currency.impl;

import me.xanium.gemseconomy.GemsEconomy;
import me.xanium.gemseconomy.account.Account;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.currency.type.AbstractCurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GemsEconomyCurrency extends AbstractCurrency {

    private final me.xanium.gemseconomy.currency.Currency currency;

    public GemsEconomyCurrency(@NotNull String id, @NotNull me.xanium.gemseconomy.currency.Currency currency) {
        super(id);
        this.currency = currency;
    }

    @NotNull
    public static Set<GemsEconomyCurrency> getCurrencies() {
        Set<GemsEconomyCurrency> currencies = new HashSet<>();

        for (me.xanium.gemseconomy.currency.Currency currency : GemsEconomy.getInstance().getCurrencyManager().getCurrencies()) {
            currencies.add(new GemsEconomyCurrency("gemseconomy_" + currency.getSingular(), currency));
        }

        return currencies;
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
        return this.currency.getSingular();
    }

    @Override
    @NotNull
    public ItemStack getDefaultIcon() {
        return new ItemStack(Material.EMERALD);
    }

    @Override
    public double getBalance(@NotNull Player player) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(player);
        validateAccount(account, player);
        return account.getBalance(this.currency);
    }

    @Override
    public double getBalance(@NotNull UUID playerId) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(playerId);
        validateAccount(account, playerId);
        return account.getBalance(this.currency);
    }

    @Override
    public void give(@NotNull Player player, double amount) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(player);
        validateAccount(account, player);
        account.deposit(this.currency, amount);
    }

    @Override
    public void give(@NotNull UUID playerId, double amount) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(playerId);
        validateAccount(account, playerId);
        account.deposit(this.currency, amount);
    }

    @Override
    public void take(@NotNull Player player, double amount) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(player);
        validateAccount(account, player);
        account.withdraw(this.currency, amount);
    }

    @Override
    public void take(@NotNull UUID playerId, double amount) {
        Account account = GemsEconomy.getInstance().getAccountManager().getAccount(playerId);
        validateAccount(account, playerId);
        account.withdraw(this.currency, amount);
    }

    private <T> void validateAccount(T account, Player player) {
        if (account == null) {
            throw new IllegalStateException("Cannot find GemsEconomy account for player: " + player.getName() + ". "
                + "Please report this error to the author of GemsEconomy at: "
                + "https://github.com/Ghost-chu/GemsEconomy/issues");
        }
    }

    private <T> void validateAccount(T account, UUID player) {
        if (account == null) {
            throw new IllegalStateException("Cannot find GemsEconomy account for player: " + player + ". "
                + "Please report this error to the author of GemsEconomy at: "
                + "https://github.com/Ghost-chu/GemsEconomy/issues");
        }
    }
}
