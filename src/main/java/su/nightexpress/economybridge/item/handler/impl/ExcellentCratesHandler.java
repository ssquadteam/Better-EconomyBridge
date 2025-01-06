package su.nightexpress.economybridge.item.handler.impl;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.item.ItemPlugins;
import su.nightexpress.economybridge.item.handler.AbstractItemHandler;
import su.nightexpress.excellentcrates.CratesAPI;
import su.nightexpress.excellentcrates.crate.impl.Crate;
import su.nightexpress.excellentcrates.key.CrateKey;

public class ExcellentCratesHandler extends AbstractItemHandler {

    private static final String PREFIX_CRATE = "crate_";
    private static final String PREFIX_KEY   = "key_";

    @Override
    @NotNull
    public String getName() {
        return ItemPlugins.EXCELLENT_CRATES;
    }

    @Override
    public boolean canHandle(@NotNull ItemStack item) {
        return CratesAPI.getCrateManager().isCrate(item) || CratesAPI.getKeyManager().isKey(item);
    }

    @Override
    @Nullable
    public ItemStack createItem(@NotNull String itemId) {
        if (itemId.startsWith(PREFIX_CRATE)) {
            String id = itemId.substring(PREFIX_CRATE.length());
            Crate crate = CratesAPI.getCrateManager().getCrateById(id);
            return crate == null ? null : crate.getItem();
        }

        if (itemId.startsWith(PREFIX_KEY)) {
            String id = itemId.substring(PREFIX_KEY.length());
            CrateKey key = CratesAPI.getKeyManager().getKeyById(id);
            return key == null ? null : key.getItem();
        }

        return null;
    }

    @Override
    @Nullable
    public String getItemId(@NotNull ItemStack item) {
        Crate crate = CratesAPI.getCrateManager().getCrateByItem(item);
        if (crate != null) return PREFIX_CRATE + crate.getId();

        CrateKey key = CratesAPI.getKeyManager().getKeyByItem(item);
        if (key != null) return PREFIX_KEY + key.getId();

        return null;
    }

    @Override
    public boolean isValidId(@NotNull String itemId) {
        if (itemId.startsWith(PREFIX_CRATE)) {
            String id = itemId.substring(PREFIX_CRATE.length());
            return CratesAPI.getCrateManager().getCrateById(id) != null;
        }

        if (itemId.startsWith(PREFIX_KEY)) {
            String id = itemId.substring(PREFIX_KEY.length());
            return CratesAPI.getKeyManager().getKeyById(id) != null;
        }

        return false;
    }
}
