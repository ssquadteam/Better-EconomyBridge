package su.nightexpress.economybridge.currency.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.BridgePlugin;
import su.nightexpress.economybridge.currency.CurrencyManager;
import su.nightexpress.economybridge.hook.VaultHook;
import su.nightexpress.nightcore.manager.AbstractListener;
import su.nightexpress.nightcore.util.Plugins;

public class CurrencyListener extends AbstractListener<BridgePlugin> {

    private final CurrencyManager manager;

    public CurrencyListener(@NotNull BridgePlugin plugin, @NotNull CurrencyManager manager) {
        super(plugin);
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServiceRegister(ServiceRegisterEvent event) {
        if (Plugins.hasVault() && VaultHook.setupEconomy()) {
            this.manager.handlePluginLoad(Plugins.VAULT);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(PluginEnableEvent event) {
        this.manager.handlePluginLoad(event.getPlugin().getName());
    }
}
