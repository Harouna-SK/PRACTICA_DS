package baseNoStates;

public abstract class DoorState {
  protected Door door;
  protected String name; //state name

  public DoorState(Door door) {
    this.door = door;
  }

  // Abstract methods to implement
  public abstract void open();
  public abstract void close();
  public abstract void lock();
  public abstract void unlock();

}
