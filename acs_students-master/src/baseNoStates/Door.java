package baseNoStates;

import baseNoStates.requests.RequestReader;
import baseNoStates.spaces.Area;
import baseNoStates.spaces.Space;
import baseNoStates.clock.Clock;
import baseNoStates.clock.ClockObserver;
import org.json.JSONObject;
import java.time.LocalDateTime;


public class Door implements ClockObserver { // use of ClockObserver
    private final String id;
    private boolean closed; // physically
    private DoorState state; // logical state
    private Area from;
    private Area to;

    //new atributes for the clock
    private LocalDateTime checkAt = null;
    private boolean waitingForAutoLock = false;

    public Door(String id, Area from, Area to) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.closed = true;
        this.state = new Locked(this);
    }

    public void processRequest(RequestReader request) {
        // it is the Door that process the request because the door has and knows
        // its state, and if closed or open
        if (request.isAuthorized()) {
            String action = request.getAction();
            doAction(action);
        } else {
            System.out.println("not authorized");
        }
        request.setDoorStateName(getStateName());
    }

    private void doAction(String action) {
        switch (action) {
            case Actions.OPEN:
                state.open();
                break;
            case Actions.CLOSE:
                state.close();
                break;
            case Actions.LOCK:
                state.lock();
                break;
            case Actions.UNLOCK:
                state.unlock();
                break;
            case Actions.UNLOCK_SHORTLY:
                state.unlockShortly(); // new call
                break;
            default:
                System.out.println("Unknown action: " + action);
                break;
        }
    }

    public void setState(DoorState newState) {
        this.state = newState;

        // if UnlockedShortly, activate timer
        if (getStateName().equalsIgnoreCase("unlocked_shortly")) {
            this.waitingForAutoLock = true;
            this.checkAt = LocalDateTime.now().plusSeconds(10);
            Clock.getInstance().registerObserver(this);
            System.out.println("Door " + id + " will auto-check in 10s");
        } else {
            // if not, we don't use it
            this.waitingForAutoLock = false;
            this.checkAt = null;
            Clock.getInstance().unregisterObserver(this);
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return state.getName();
    }

    public Area getTo() {
        return to;
    }
    public Area getFrom() {
        return from;
    }

    // We call tick every second
    @Override
    public void tick(LocalDateTime now) {
        if (!waitingForAutoLock || checkAt == null) return;

        if (now.isAfter(checkAt)) {
            // after 10 seconds
            if (isClosed()) {
                setState(new Locked(this));
                System.out.println("Door " + id + " auto-locked after 10s");
            } else {
                setState(new Propped(this));
                System.out.println("Door " + id + " became propped after 10s");
            }
            waitingForAutoLock = false;
            Clock.getInstance().unregisterObserver(this);
        }
    }


    @Override
    public String toString() {
        return "Door{"
                + ", id='" + id + '\''
                + ", closed=" + closed
                + ", state=" + getStateName()
                + "}";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("state", getStateName());
        json.put("closed", closed);
        return json;
    }
}
