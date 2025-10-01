package baseNoStates;

public class Unlocked extends DoorState {

  public Unlocked(Door door) {
    super(door);
    this.name = States.UNLOCKED;
  }

  @Override
  public void open() {
    System.out.println("Door open.");
  }

  @Override
  public void close() {
    System.out.println("Door closed, but unlocked");
  }

  @Override
  public void lock() {
    System.out.println("Locking door...");
  }

  @Override
  public void unlock() {
    System.out.println("Door is already unlocked.");
  }
}
