package baseNoStates;

public class Unlocked extends DoorState {

  public Unlocked(Door door) {
    super(door, States.UNLOCKED);
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      System.out.println("Door " + door.getId() + " opened");
    } else {
      System.out.println("Can't open door " + door.getId() + " because it's already open");
    }
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close door " + door.getId() + " because it's already closed");
    } else {
      door.setClosed(true);
      System.out.println("Door " + door.getId() + " closed");
    }
  }

  @Override
  public void lock() {
    if (!door.isClosed()) {
      System.out.println("Can't lock door " + door.getId() + " because it's open");
    } else {
      door.setState(new Locked(door));
      System.out.println("Door " + door.getId() + " locked");
    }
  }

  @Override
  public void unlock() {
    System.out.println("Door " + door.getId() + " is already unlocked");
  }

  @Override
    public void unlockShortly() {
        System.out.println("Door " + door.getId() + " is already unlocked");
    }
}

