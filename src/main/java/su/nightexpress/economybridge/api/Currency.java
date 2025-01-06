package su.nightexpress.economybridge.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.Placeholders;
import su.nightexpress.economybridge.config.Config;
import su.nightexpress.economybridge.currency.CurrencyId;
import su.nightexpress.nightcore.util.NumberUtil;

import java.util.UUID;
import java.util.function.UnaryOperator;

public interface Currency {

    default boolean isDummy() {
        return this.getInternalId().equalsIgnoreCase(CurrencyId.DUMMY);
    }

    @NotNull
    default UnaryOperator<String> replacePlaceholders() {
        return Placeholders.forCurrency(this);
    }

    default double fineValue(double amount) {
        if (!this.canHandleDecimals()) amount = Math.floor(amount);

        return amount;
    }

    @NotNull
    default String formatValue(double amount) {
        if (!this.canHandleDecimals()) {
            amount = Math.floor(amount);
        }
        return NumberUtil.format(amount);
    }

    @NotNull
    default String format(double amount) {
//        String format = this.getFormat();
//        if (Config.isPlaceholderAPIInFormat()) {
//            format = PlaceholderAPI.setPlaceholders(null, format);
//        }
//
//        return this.replacePlaceholders().apply(format
//            .replace(Placeholders.GENERIC_AMOUNT, this.formatValue(amount))
//            .replace(Placeholders.GENERIC_NAME, this.getName())
//        );

        return this.applyFormat(this.getFormat(), amount);
    }

    @NotNull
    default String applyFormat(@NotNull String format, double amount) {
        if (Config.isPlaceholderAPIInFormat()) {
            format = PlaceholderAPI.setPlaceholders(null, format);
        }

        return this.replacePlaceholders().apply(format
            .replace(Placeholders.GENERIC_AMOUNT, this.formatValue(amount))
            .replace(Placeholders.GENERIC_NAME, this.getName())
        );
    }



    double getBalance(@NotNull Player player);

    double getBalance(@NotNull UUID playerId);

    void give(@NotNull Player player, double amount);

    void give(@NotNull UUID playerId, double amount);

    void take(@NotNull Player player, double amount);

    void take(@NotNull UUID playerId, double amount);



    boolean canHandleDecimals();

    boolean canHandleOffline();



    @NotNull String getOriginalId();

    @NotNull String getInternalId();

    @NotNull String getName();

    @NotNull String getDefaultName();

    @NotNull String getFormat();

    @NotNull
    default String getDefaultFormat() {
        return Placeholders.GENERIC_AMOUNT + " " + Placeholders.GENERIC_NAME;
    }

    @NotNull ItemStack getIcon();

    @NotNull ItemStack getDefaultIcon();
}
