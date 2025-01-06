package su.nightexpress.economybridge.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.api.item.ItemHandler;
import su.nightexpress.economybridge.config.Config;
import su.nightexpress.economybridge.item.handler.impl.*;
import su.nightexpress.nightcore.manager.AbstractManager;
import su.nightexpress.nightcore.util.Plugins;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ItemManager extends AbstractManager<BridgePlugin> {

    public static final DummyHandler DUMMY_HANDLER = new DummyHandler();

    private final Map<String, ItemHandler> handlerMap;

    public ItemManager(@NotNull BridgePlugin plugin) {
        super(plugin);
        this.handlerMap = new HashMap<>();
    }

    @Override
    protected void onLoad() {
        this.register(DUMMY_HANDLER);

        this.register(ItemPlugins.EXCELLENT_CRATES, ExcellentCratesHandler::new);
        this.register(ItemPlugins.EXECUTABLE_ITEMS, ExecutableItemsHandler::new);
        this.register(ItemPlugins.ITEMS_ADDER, ItemsAdderHandler::new);
        this.register(ItemPlugins.MMOITEMS, MMOItemsHandler::new);
        this.register(ItemPlugins.NEXO, NexoHandler::new);
        this.register(ItemPlugins.ORAXEN, OraxenHandler::new);
    }

    @Override
    protected void onShutdown() {
        this.handlerMap.clear();
    }

    public boolean register(@NotNull String pluginName, @NotNull Supplier<ItemHandler> supplier) {
        if (!Plugins.isInstalled(pluginName)) return false;

        return register(supplier.get());
    }

    public boolean register(@NotNull ItemHandler handler) {
        String name = handler.getName().toLowerCase();
        if (Config.DISABLED_ITEM_HANDLERS.get().contains(name)) {
            return false;
        }

        this.handlerMap.put(name, handler);
        this.plugin.info("Item Handler registered: '" + name + "'.");
        return true;
    }

    @NotNull
    public DummyHandler getDummyHandler() {
        return DUMMY_HANDLER;
    }

    @NotNull
    public Set<ItemHandler> getHandlers() {
        return new HashSet<>(this.handlerMap.values());
    }

    @Nullable
    public ItemHandler getHandler(@NotNull String name) {
        return this.handlerMap.get(name.toLowerCase());
    }

    @Nullable
    public ItemHandler getHandler(@Nullable ItemStack item) {
        if (item == null) return null;

        return this.getHandlers().stream().filter(handler -> handler.canHandle(item)).findFirst().orElse(null);
    }
}
