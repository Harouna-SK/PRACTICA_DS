package baseNoStates;

public class UnlockedShortly extends DoorState {

    public UnlockedShortly(Door door) {
        super(door, "unlocked_shortly");
    }

    @Override
    public void open() {
        if (door.isClosed()) {
            door.setClosed(false);
            System.out.println("Door " + door.getId() + " opened (was unlocked shortly)");
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
            System.out.println("Door " + door.getId() + " closed (was unlocked shortly)");
        }
    }

    @Override
    public void lock() {
        System.out.println("Can't lock door " + door.getId() + " when in unlocked shortly state");
    }

    @Override
    public void unlock() {
        System.out.println("Can't unlock door " + door.getId() + " when in unlocked shortly state");
    }

    @Override
    public void unlockShortly() {
        System.out.println("Door " + door.getId() + " is already in unlocked shortly state");
    }
}