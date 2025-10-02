package baseNoStates;

public class Locked extends DoorState {

  public Locked(Door door) {
    super(door, States.LOCKED);
  }

  @Override
  public void open() {
    System.out.println("Can't open door " + door.getId() + " because it's locked");
  }

  @Override
  public void close() {
    if (door.isClosed()) {
      System.out.println("Can't close door " + door.getId() + " because it's already closed");
    } else {
      System.out.println("Door " + door.getId() + " closed");
    }
  }

  @Override
  public void lock() {
    System.out.println("Door " + door.getId() + " is already locked");
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
    System.out.println("Door " + door.getId() + " unlocked");
  }
}
