package su.nightexpress.economybridge.api.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemHandler {

    @NotNull String getName();

    boolean canHandle(@NotNull ItemStack item);

    @Nullable ItemStack createItem(@NotNull String itemId);

    @Nullable String getItemId(@NotNull ItemStack itemStack);

    boolean isValidId(@NotNull String itemId);

    boolean isDummy();
}
