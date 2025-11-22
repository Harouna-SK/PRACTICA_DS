package baseNoStates;

public abstract class DoorState {
    protected Door door;
    protected String name;

    public DoorState(Door door, String name) {
        this.door = door;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void open();

    public abstract void close();

    public abstract void lock();

    public abstract void unlock();

    public abstract void unlockShortly();
}
