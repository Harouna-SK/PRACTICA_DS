package baseNoStates.clock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase singleton que implementa un reloj del sistema.
 * El reloj actualiza periódicamente la fecha/hora y notifica a todos
 * los observadores registrados mediante el patrón Observer.
 * El periodo por defecto es de 1 segundo.
 *
 * @author Sistema ACS
 */
public class Clock {
  private static final Clock instance = new Clock(1); // periodo de 1s
  private final Timer timer;
  private final int periodSeconds;
  private final List<ClockObserver> observers = new ArrayList<>();
  private volatile LocalDateTime date; // última fecha actualizada por el clock

  private static final Logger LOG = LoggerFactory.getLogger(Clock.class);

  /**
   * Constructor privado para implementar el patrón Singleton.
   *
   * @param periodSeconds Periodo en segundos entre actualizaciones
   */
  private Clock(int periodSeconds) {
    this.periodSeconds = periodSeconds;
    this.timer = new Timer(true); // daemon
  }

  /**
   * Obtiene la instancia única del reloj (patrón Singleton).
   *
   * @return La instancia única de Clock
   */
  public static Clock getInstance() {
    return instance;
  }

  /**
   * Inicia el reloj y comienza a actualizar periódicamente la fecha/hora.
   * Notifica a todos los observadores registrados en cada tick.
   */
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

  /**
   * Detiene el reloj y cancela todas las tareas programadas.
   */
  public void stop() {
    LOG.debug("Stopping clock");
    timer.cancel();
  }

  /**
   * Obtiene la fecha/hora actual del reloj.
   *
   * @return La última fecha/hora actualizada por el reloj
   */
  public LocalDateTime now() {
    return date;
  }

  /**
   * Registra un observador para recibir notificaciones en cada tick del reloj.
   *
   * @param obs El observador a registrar
   */
  public void registerObserver(ClockObserver obs) {
    LOG.debug("Adding observer");
    synchronized (observers) {
      if (!observers.contains(obs)) {
        observers.add(obs);
      }
    }
  }

  /**
   * Desregistra un observador para que deje de recibir notificaciones.
   *
   * @param obs El observador a desregistrar
   */
  public void unregisterObserver(ClockObserver obs) {
    LOG.debug("Removing observer");
    synchronized (observers) {
      observers.remove(obs);
    }
  }
}