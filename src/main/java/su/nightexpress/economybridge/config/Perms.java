package su.nightexpress.economybridge.config;

import su.nightexpress.economybridge.Placeholders;
import su.nightexpress.nightcore.util.wrapper.UniPermission;

public class Perms {

    public static final String PREFIX = "economybridge.";
    public static final String PREFIX_COMMAND = PREFIX + "command.";

    public static final UniPermission PLUGIN = new UniPermission(PREFIX + Placeholders.WILDCARD);
    public static final UniPermission COMMAND = new UniPermission(PREFIX_COMMAND + Placeholders.WILDCARD);

    public static final UniPermission COMMAND_RELOAD = new UniPermission(PREFIX_COMMAND + "reload");

    static {
        PLUGIN.addChildren(COMMAND);

        COMMAND.addChildren(
            COMMAND_RELOAD
        );
    }
}
