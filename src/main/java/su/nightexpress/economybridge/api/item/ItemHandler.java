package su.nightexpress.economybridge.api.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemHandler {

    @NotNull String getName();

    boolean canHandle(@NotNull ItemStack item);

    //@NotNull ItemWrapper readWrapper(@NotNull FileConfig config, @NotNull String path);

//    @Deprecated
//    @NotNull ItemWrapper deserialize(@NotNull String str);

    //@NotNull ItemWrapper wrap(@NotNull ItemStack itemStack);

    //@NotNull ItemWrapper wrap(@NotNull String itemId, int amount);

    @Nullable ItemStack createItem(@NotNull String itemId);

    //@Nullable ItemStack createItem(@NotNull String itemId, int amount);

    @Nullable String getItemId(@NotNull ItemStack itemStack);

    boolean isValidId(@NotNull String itemId);

    boolean isDummy();
}
