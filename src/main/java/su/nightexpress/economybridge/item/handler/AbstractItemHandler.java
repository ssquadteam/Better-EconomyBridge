package su.nightexpress.economybridge.item.handler;

import su.nightexpress.economybridge.api.item.ItemHandler;

public abstract class AbstractItemHandler implements ItemHandler {

    public AbstractItemHandler() {

    }

    @Override
    public boolean isDummy() {
        return false;
    }
}
