package su.nightexpress.economybridge.item.handler.impl;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.ItemPlugins;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;

public class NexoHandler extends AbstractItemHandler {

    @Override
    @NotNull
    public String getName() {
        return ItemPlugins.NEXO;
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        ItemBuilder builder = NexoItems.itemFromId(itemId);
        return builder == null ? null : builder.build();
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return NexoItems.exists(item);
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        return NexoItems.exists(itemId);
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack item) {
        return NexoItems.idFromItem(item);
    }
}
