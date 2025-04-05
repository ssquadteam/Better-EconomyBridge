package su.nightexpress.economybridge.currency;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.nightcore.config.ConfigValue;
import su.nightexpress.nightcore.config.FileConfig;

public class CurrencySettings {

    private String    name;
    private String    format;
    private ItemStack icon;

    public CurrencySettings(String name, String format, ItemStack icon) {
        this.name = name;
        this.format = format;
        this.icon = new ItemStack(icon);
    }

    @NotNull
    public static CurrencySettings fromDefaults(@NotNull Currency currency) {
        return new CurrencySettings(currency.getDefaultName(), currency.getDefaultFormat(), currency.getDefaultIcon());
    }

    public void load(@NotNull FileConfig config, @NotNull String path) {
        this.name = ConfigValue.create(path + ".Name", this.name).read(config);
        this.format = ConfigValue.create(path + ".Format", this.format).read(config);
        this.icon = ConfigValue.create(path + ".Icon", this.icon).read(config);
    }

    public void write(@NotNull FileConfig config, @NotNull String path) {
        config.set(path + ".Name", this.getName());
        config.set(path + ".Format", this.getFormat());
        config.setItem(path + ".Icon", this.getIcon());
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getFormat() {
        return this.format;
    }

    @NotNull
    public ItemStack getIcon() {
        return new ItemStack(this.icon);
    }
}
