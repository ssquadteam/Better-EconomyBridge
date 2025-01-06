package su.nightexpress.economybridge.item.handler.impl;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.ItemPlugins;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;

public class ItemsAdderHandler extends AbstractItemHandler {

    @Override
    @NotNull
    public String getName() {
        return ItemPlugins.ITEMS_ADDER;
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        CustomStack customStack = CustomStack.getInstance(itemId);
        return customStack == null ? null : customStack.getItemStack();
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return CustomStack.byItemStack(item) != null;
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        return CustomStack.isInRegistry(itemId);
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack item) {
        if (item.getType().isAir()) return null;

        CustomStack stack = CustomStack.byItemStack(item);
        return stack == null ? null : stack.getNamespacedID();
    }
}
