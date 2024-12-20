package su.nightexpress.economybridge.command;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.config.Lang;
import su.nightexpress.economybridge.config.Perms;
import su.nightexpress.economybridge.currency.impl.ItemStackCurrency;
import su.nightexpress.nightcore.command.experimental.CommandContext;
import su.nightexpress.nightcore.command.experimental.argument.ArgumentTypes;
import su.nightexpress.nightcore.command.experimental.argument.ParsedArguments;
import su.nightexpress.nightcore.command.experimental.impl.ReloadCommand;
import su.nightexpress.nightcore.command.experimental.node.ChainedNode;
import su.nightexpress.nightcore.command.experimental.node.DirectNode;

public class BaseCommands {

    public static void load(@NotNull BridgePlugin plugin) {
        ChainedNode root = plugin.getRootNode();

        root.addChildren(ReloadCommand.builder(plugin, Perms.COMMAND_RELOAD));

        root.addChildren(DirectNode.builder(plugin, "fromitem")
            .description(Lang.COMMAND_FROM_ITEM_DESC)
            .permission(Perms.COMMAND_FROM_ITEM)
            .playerOnly()
            .withArgument(ArgumentTypes.string(CommandArguments.NAME).required().localized(Lang.COMMAND_ARGUMENT_NAME_NAME))
            .executes((context, arguments) -> createFromItem(plugin, context, arguments))
        );

        // TODO give, take commands
    }

    private static boolean createFromItem(@NotNull BridgePlugin plugin, @NotNull CommandContext context, @NotNull ParsedArguments arguments) {
        Player player = context.getPlayerOrThrow();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType().isAir()) {
            Lang.COMMAND_FROM_ITEM_ERROR_NO_ITEM.getMessage().send(player);
            return false;
        }

        String id = arguments.getStringArgument(CommandArguments.NAME);
        ItemStackCurrency currency = new ItemStackCurrency(id, itemStack);

        plugin.getCurrencyManager().saveCurrency(currency);
        plugin.getCurrencyManager().loadCurrency(currency);
        Lang.COMMAND_FROM_ITEM_CREATED.getMessage().send(player, replacer -> replacer.replace(currency.replacePlaceholders()));
        return true;
    }
}
