package su.nightexpress.economybridge.config;

import su.nightexpress.nightcore.core.CoreLang;
import su.nightexpress.nightcore.language.entry.LangString;
import su.nightexpress.nightcore.language.entry.LangText;

import static su.nightexpress.nightcore.util.text.tag.Tags.*;
import static su.nightexpress.economybridge.Placeholders.*;

public class Lang extends CoreLang {

    public static final LangString COMMAND_FROM_ITEM_DESC = LangString.of("Command.FromItem.Desc", "Create item currency.");

    public static final LangText COMMAND_FROM_ITEM_ERROR_NO_ITEM = LangText.of("Command.FromItem.Error.NoItem",
        LIGHT_RED.enclose("You must hold an item in hand!")
    );

    public static final LangText COMMAND_FROM_ITEM_CREATED = LangText.of("Command.FromItem.Created",
        LIGHT_GRAY.enclose("Created " + LIGHT_YELLOW.enclose(CURRENCY_NAME) + " currency as " + LIGHT_YELLOW.enclose(CURRENCY_ID) + ".")
    );
}
