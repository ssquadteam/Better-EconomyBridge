package su.nightexpress.economybridge.item.handler.impl;

import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.ItemPlugins;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;

import java.util.Optional;

public class ExecutableItemsHandler extends AbstractItemHandler {

    @Override
    @NotNull
    public String getName() {
        return ItemPlugins.EXECUTABLE_ITEMS;
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        var item = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(itemId).orElse(null);
        return item == null ? null : item.buildItem(1, Optional.empty());
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(item).isPresent();
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        return ExecutableItemsAPI.getExecutableItemsManager().isValidID(itemId);
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack item) {
        return ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(item).map(ExecutableItemInterface::getId).orElse(null);
    }
}
