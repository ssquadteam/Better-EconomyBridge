package su.nightexpress.economybridge.config;

import org.jetbrains.annotations.NotNull;
import su.nightexpress.nightcore.config.ConfigValue;
import su.nightexpress.nightcore.util.Lists;
import su.nightexpress.nightcore.util.Plugins;

import java.util.Set;

public class Config {

    public static final ConfigValue<Set<String>> DISABLED_CURRENCIES = ConfigValue.create("General.Disabled_Currencies",
        Lists.newSet("currency1", "currency2"),
        "List of disabled currencies"
    ).onRead(set -> Lists.modify(set, String::toLowerCase));

    public static final ConfigValue<Boolean> PLACEHOLDER_API_IN_FORMAT = ConfigValue.create("General.PlaceholderAPI_In_Format",
        true,
        "Sets whether to apply " + Plugins.PLACEHOLDER_API + " placeholders for the currency's 'Format' setting.",
        "Allows you to use custom images from Oraxen or ItemsAdder, as well as any other player unrelated placeholders."
    );

    public static boolean isPlaceholderAPIInFormat() {
        return PLACEHOLDER_API_IN_FORMAT.get() && Plugins.hasPlaceholderAPI();
    }

    public static boolean isDisabledCurrency(@NotNull String id) {
        return DISABLED_CURRENCIES.get().contains(id.toLowerCase());
    }
}
