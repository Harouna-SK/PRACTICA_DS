package baseNoStates.clock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private static final Clock instance = new Clock(1); // periodo de 1s
    private final Timer timer;
    private final int periodSeconds;
    private final List<ClockObserver> observers = new ArrayList<>();
    private volatile LocalDateTime date; // última fecha actualizada por el clock

    private Clock(int periodSeconds) {
        this.periodSeconds = periodSeconds;
        this.timer = new Timer(true); // daemon
    }

    public static Clock getInstance() {
        return instance;
    }

    public void start() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                date = LocalDateTime.now();
                // iterar sobre copia para evitar ConcurrentModificationException
                List<ClockObserver> copy;
                synchronized (observers) {
                    copy = new ArrayList<>(observers);
                }
                for (ClockObserver obs : copy) {
                    obs.tick(date);
                }
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 1000L * periodSeconds);
    }

    public void stop() {
        timer.cancel();
    }

    public LocalDateTime now() {
        return date;
    }

    public void registerObserver(ClockObserver obs) {
        synchronized (observers) {
            if (!observers.contains(obs)) observers.add(obs);
        }
    }

    public void unregisterObserver(ClockObserver obs) {
        synchronized (observers) {
            observers.remove(obs);
        }
    }
}