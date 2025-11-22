package baseNoStates.clock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Clock {
    private static final Clock instance = new Clock(1); // periodo de 1s
    private final Timer timer;
    private final int periodSeconds;
    private final List<ClockObserver> observers = new ArrayList<>();
    private volatile LocalDateTime date; // última fecha actualizada por el clock

    private static final Logger LOG = LoggerFactory.getLogger(Clock.class);

    private Clock(int periodSeconds) {
        this.periodSeconds = periodSeconds;
        this.timer = new Timer(true); // daemon
        // LOG.debug("Initialized clock object with {} periodSeconds", periodSeconds);
    }

    public static Clock getInstance() {
        return instance;
    }

    public void start() {
        LOG.debug("Starting clock");
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
        LOG.debug("Stopping clock");
        timer.cancel();
    }

    public LocalDateTime now() {
        return date;
    }

    public void registerObserver(ClockObserver obs) {
        LOG.debug("Adding observer");
        synchronized (observers) {
            if (!observers.contains(obs)) observers.add(obs);
        }
    }

    public void unregisterObserver(ClockObserver obs) {
        LOG.debug("Removing observer");
        synchronized (observers) {
            observers.remove(obs);
        }
    }
}