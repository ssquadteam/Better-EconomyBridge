package su.nightexpress.economybridge.item.handler.impl;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.ItemPlugins;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;

public class OraxenHandler extends AbstractItemHandler {

    @Override
    @NotNull
    public String getName() {
        return ItemPlugins.ORAXEN;
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        ItemBuilder builder = OraxenItems.getItemById(itemId);
        return builder == null ? null : builder.build();
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return OraxenItems.exists(item);
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        return OraxenItems.exists(itemId);
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack item) {
        return OraxenItems.getIdByItem(item);
    }
}
