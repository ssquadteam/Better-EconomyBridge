package su.nightexpress.economybridge;

import org.jetbrains.annotations.NotNull;
import su.nightexpress.economybridge.api.Currency;
import su.nightexpress.nightcore.util.placeholder.PlaceholderList;

import java.util.function.UnaryOperator;

public class Placeholders extends su.nightexpress.nightcore.util.Placeholders {

    public static final String CURRENCY_NAME = "%currency_name%";
    public static final String CURRENCY_ID   = "%currency_id%";

    public static final PlaceholderList<Currency> CURRENCY = new PlaceholderList<Currency>()
        .add(CURRENCY_ID, Currency::getInternalId)
        .add(CURRENCY_NAME, Currency::getName)
        ;

    @NotNull
    public static UnaryOperator<String> forCurrency(@NotNull Currency currency) {
        return CURRENCY.replacer(currency);
    }
}
