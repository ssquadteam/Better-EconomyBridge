package su.nightexpress.economybridge;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.api.item.ItemHandler;
import su.nightexpress.economybridge.item.ItemManager;
import su.nightexpress.economybridge.item.handler.impl.DummyHandler;

import java.util.Set;

public class ItemBridge {

    @NotNull
    public static ItemManager getItemManager() {
        return EconomyBridge.getPlugin().getItemManager();
    }

    @NotNull
    public static Set<ItemHandler> getHandlers() {
        return getItemManager().getHandlers();
    }

    @Nullable
    @Deprecated
    public static ItemHandler getItemHandler(@NotNull String name) {
        return getHandler(name);
    }

    @Nullable
    @Deprecated
    public static ItemHandler getItemHandler(@NotNull ItemStack itemStack) {
        return getHandler(itemStack);
    }

    @NotNull
    public static DummyHandler getDummyHandler() {
        return ItemManager.DUMMY_HANDLER;
    }

    @Nullable
    public static ItemHandler getHandler(@NotNull String name) {
        return getItemManager().getHandler(name);
    }

    @NotNull
    public static ItemHandler getHandlerOrDummy(@NotNull String name) {
        ItemHandler handler = getHandler(name);
        return handler == null ? getDummyHandler() : handler;
    }

    @Nullable
    public static ItemHandler getHandler(@NotNull ItemStack itemStack) {
        return getItemManager().getHandler(itemStack);
    }

    @NotNull
    public static ItemHandler getHandlerOrDummy(@NotNull ItemStack itemStack) {
        ItemHandler handler = getHandler(itemStack);
        return handler == null ? getDummyHandler() : handler;
    }

    @Nullable
    public static ItemStack createItem(@NotNull String handlerName, @NotNull String itemId) {
        ItemHandler handler = getHandler(handlerName);
        return handler == null ? null : handler.createItem(itemId);
    }

    @Nullable
    public static String getItemId(@NotNull String handlerName, @NotNull ItemStack itemStack) {
        ItemHandler handler = getHandler(handlerName);
        return handler == null ? null : handler.getItemId(itemStack);
    }

    @Nullable
    public static String getItemId(@NotNull ItemStack itemStack) {
        ItemHandler handler = getHandler(itemStack);
        return handler == null ? null : handler.getItemId(itemStack);
    }

    public static boolean isCustomItem(@NotNull ItemStack itemStack) {
        return getHandler(itemStack) != null;
    }
}
