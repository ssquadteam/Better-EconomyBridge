package su.nightexpress.economybridge.command;

import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.config.Perms;
import su.nightexpress.nightcore.command.experimental.impl.ReloadCommand;
import su.nightexpress.nightcore.command.experimental.node.ChainedNode;

public class BaseCommands {

    public static void load(@NotNull BridgePlugin plugin) {
        ChainedNode root = plugin.getRootNode();

        root.addChildren(ReloadCommand.builder(plugin, Perms.COMMAND_RELOAD));
    }
}
