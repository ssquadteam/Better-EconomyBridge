package su.nightexpress.economybridge.item.handler.impl;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;

public class DummyHandler extends AbstractItemHandler {

    public static final String NAME = "dummy";

    public DummyHandler() {
        super();
    }

    @Override
    public boolean isDummy() {
        return true;
    }

    @Override
    @NotNull
    public String getName() {
        return NAME;
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        return null;
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack itemStack) {
        return NAME;
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        return false;
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return false;
    }
}
