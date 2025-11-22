package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class UnlockedShortly extends DoorState {
    private static final Logger LOG = LoggerFactory.getLogger(UnlockedShortly.class);

    public UnlockedShortly(Door door) {
        super(door, "unlocked_shortly");
        LOG.debug("UnlockedShortly object initialized calling DoorState constructor");
    }

    @Override
    public void open() {
        if (door.isClosed()) {
            door.setClosed(false);
            LOG.debug("Door " + door.getId() + " opened (was unlocked shortly)");
        } else {
            LOG.debug("Can't open door " + door.getId() + " because it's already open");
        }
    }

    @Override
    public void close() {
        if (door.isClosed()) {
            LOG.debug("Can't close door " + door.getId() + " because it's already closed");
        } else {
            door.setClosed(true);
            LOG.debug("Door " + door.getId() + " closed (was unlocked shortly)");
        }
    }

    @Override
    public void lock() {
        LOG.debug("Can't lock door " + door.getId() + " when in unlocked shortly state");
    }

    @Override
    public void unlock() {
        LOG.debug("Can't unlock door " + door.getId() + " when in unlocked shortly state");
    }

    @Override
    public void unlockShortly() {
        LOG.debug("Door " + door.getId() + " is already in unlocked shortly state");
    }
}