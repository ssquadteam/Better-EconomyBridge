package su.nightexpress.economybridge.currency.type;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.economybridge.currency.CurrencySettings;

public abstract class AbstractCurrency implements Currency {

    protected final String originalId;
    protected final String internalId;

    protected CurrencySettings settings;

    public AbstractCurrency(@NotNull String id) {
        this(id, id);
    }

    public AbstractCurrency(@NotNull String originalId, @NotNull String internalId) {
        this.originalId = originalId;
        this.internalId = internalId.toLowerCase();
    }

    public void load(@NotNull CurrencySettings settings) {
        this.settings = settings;
    }

    @NotNull
    public CurrencySettings getSettings() {
        return this.settings;
    }

    @Override
    @NotNull
    public String getOriginalId() {
        return this.originalId;
    }

    @Override
    @NotNull
    public String getInternalId() {
        return this.internalId;
    }

    @Override
    @NotNull
    public String getName() {
        return this.settings == null ? this.getDefaultName() : this.settings.getName();
    }

    @Override
    @NotNull
    public String getFormat() {
        return this.settings == null ? this.getDefaultFormat() : this.settings.getFormat();
    }

    @Override
    @NotNull
    public ItemStack getIcon() {
        return this.settings == null ? this.getDefaultIcon() : this.settings.getIcon();
    }
}
