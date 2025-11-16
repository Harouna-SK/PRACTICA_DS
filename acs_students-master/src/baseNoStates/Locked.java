package baseNoStates;

import baseNoStates.requests.RequestReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Locked extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(RequestReader.class);
  private static final Marker ACTIVITY = MarkerFactory.getMarker("ACTIVITY");

  public Locked(Door door) {
    super(door, States.LOCKED);
  }

  @Override
  public void open() {
    // System.out.println("Can't open door " + door.getId() + " because it's locked");
    LOG.warn(ACTIVITY, "Can't open door: {} because it's locked", door.getId());
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      // System.out.println("Can't close door " + door.getId() + " because it's already closed");
      LOG.warn(ACTIVITY, "Can't close door: {} because it's alredy closed", door.getId());
    } else {
      // System.out.println("Door " + door.getId() + " closed");
      LOG.info(ACTIVITY, "Door {} closed", door.getId());
    }
  }

  @Override
  public void lock() {
    // System.out.println("Door " + door.getId() + " is already locked");
    LOG.warn(ACTIVITY, "Door {} is alredy locked", door.getId());
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
    // System.out.println("Door " + door.getId() + " unlocked");
    LOG.info(ACTIVITY, "Door {} unlocked", door.getId());
  }
    @Override
    public void unlockShortly() {
        door.setState(new UnlockedShortly(door));
        // System.out.println("Door " + door.getId() + " unlocked shortly");
        LOG.info(ACTIVITY, "Door {} unlocked shortly", door.getId());
    }
}
