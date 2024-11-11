package su.nightexpress.economybridge;

import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.command.BaseCommands;
import su.nightexpress.economybridge.config.Config;
import su.nightexpress.economybridge.config.Lang;
import su.nightexpress.economybridge.config.Perms;
import su.nightexpress.economybridge.currency.CurrencyManager;
import su.nightexpress.nightcore.NightPlugin;
import su.nightexpress.nightcore.command.experimental.ImprovedCommands;
import su.nightexpress.nightcore.config.PluginDetails;

public class BridgePlugin extends NightPlugin implements ImprovedCommands {

    private CurrencyManager currencyManager;

    @Override
    @NotNull
    protected PluginDetails getDefaultDetails() {
        return PluginDetails.create("EconomyBridge", new String[]{"economybridge", "econbridge", "ebridge"})
            .setConfigClass(Config.class)
            .setLangClass(Lang.class)
            .setPermissionsClass(Perms.class)
            ;
    }

    @Override
    public void enable() {
        EconomyBridge.load(this);
        BaseCommands.load(this);

        this.currencyManager = new CurrencyManager(this);
        this.currencyManager.setup();
    }

    @Override
    public void disable() {
        if (this.currencyManager != null) this.currencyManager.shutdown();

        EconomyBridge.unload();
    }

    @NotNull
    public CurrencyManager getCurrencyManager() {
        return this.currencyManager;
    }
}
