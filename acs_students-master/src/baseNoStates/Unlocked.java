package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Unlocked extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(Unlocked.class);

  public Unlocked(Door door) {
    super(door, States.UNLOCKED);
    LOG.debug("Unlocked object initialized calling DoorState constructor");
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      LOG.debug("Door " + door.getId() + " opened");
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
      LOG.debug("Door " + door.getId() + " closed");
    }
  }

  @Override
  public void lock() {
    if (!door.isClosed()) {
      LOG.debug("Can't lock door " + door.getId() + " because it's open");
    } else {
      door.setState(new Locked(door));
      LOG.debug("Door " + door.getId() + " locked");
    }
  }

  @Override
  public void unlock() {
    LOG.debug("Door " + door.getId() + " is already unlocked");
  }

  @Override
    public void unlockShortly() {
        LOG.debug("Door " + door.getId() + " is already unlocked");
    }
}

