package baseNoStates;

public class Propped extends DoorState {

    public Propped(Door door) {
        super(door, "propped");
    }

    @Override
    public String getName() {
        return "propped";
    }

    @Override
    public void lock() {
        // Can't block if is open propped (atascada)
        System.out.println("Cannot lock: door " + door.getId() + " is propped open.");
    }

    @Override
    public void unlock() {
        // Is already open
        System.out.println("Door " + door.getId() + " already open (propped).");
    }

    @Override
    public void open() {
        System.out.println("Door " + door.getId() + " is already open (propped).");
    }

    @Override
    public void close() {
        // If it closes it turns locked
        door.setClosed(true);
        door.setState(new Locked(door));
        System.out.println("Door " + door.getId() + " closed and now locked.");
    }
    @Override
    public void unlockShortly() {
        System.out.println("Cannot unlock shortly: door " + door.getId() + " is propped open.");
    }
}
