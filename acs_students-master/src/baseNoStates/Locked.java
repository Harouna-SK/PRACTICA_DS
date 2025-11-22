package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Locked extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(Locked.class);
  private static final Marker ACTIVITY = MarkerFactory.getMarker("ACTIVITY");
  public Locked(Door door) {
    super(door, States.LOCKED);
    LOG.debug("calling Door class to initialize Locked object");
  }

  @Override
  public void open() {
    // System.out.println("Can't open door " + door.getId() + " because it's locked");
    LOG.warn("Can't open door: {} because it's locked", door.getId());
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      // System.out.println("Can't close door " + door.getId() + " because it's already closed");
      LOG.warn("Can't close door: {} because it's alredy closed", door.getId());
    } else {
      LOG.debug("Door " + door.getId() + " closed");
    }
  }

  @Override
  public void lock() {
    // System.out.println("Door " + door.getId() + " is already locked");
    LOG.warn("Door {} is alredy locked", door.getId());
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
    // System.out.println("Door " + door.getId() + " unlocked");
    LOG.debug("Door {} unlocked", door.getId());
  }
    @Override
    public void unlockShortly() {
        door.setState(new UnlockedShortly(door));
        // System.out.println("Door " + door.getId() + " unlocked shortly");
        LOG.debug("Door {} unlocked shortly", door.getId());
    }
}
