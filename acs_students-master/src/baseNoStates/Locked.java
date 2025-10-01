package baseNoStates;

// Clase concreta (C)
public class Locked extends DoorState {

  public Locked(Door door) {
    super(door);
    this.name = States.LOCKED;
  }

  @Override
  public void open() {
    System.out.println("Can't open, door is locked.");
  }

  @Override
  public void close() {
    System.out.println("Door already closed and locked.");
  }

  @Override
  public void lock() {
    System.out.println("Door already locked.");
  }

  @Override
  public void unlock() {
    System.out.println("Unlocking door...");
  }
}

