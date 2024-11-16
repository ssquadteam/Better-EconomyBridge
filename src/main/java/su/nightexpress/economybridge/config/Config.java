package su.nightexpress.economybridge.config;

import su.nightexpress.nightcore.config.ConfigValue;
import su.nightexpress.nightcore.util.Plugins;

public class Config {

    public static final ConfigValue<Boolean> PLACEHOLDER_API_IN_FORMAT = ConfigValue.create("General.PlaceholderAPI_In_Format",
        true,
        "Sets whether to apply " + Plugins.PLACEHOLDER_API + " placeholders for the currency's 'Format' setting.",
        "Allows you to use custom images from Oraxen or ItemsAdder, as well as any other player unrelated placeholders."
    );

    public static boolean isPlaceholderAPIInFormat() {
        return PLACEHOLDER_API_IN_FORMAT.get() && Plugins.hasPlaceholderAPI();
    }
}
