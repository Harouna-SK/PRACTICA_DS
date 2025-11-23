package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Clase que representa el estado bloqueado de una puerta.
 * En este estado, la puerta no puede abrirse hasta que se desbloquee.
 *
 * @author Sistema ACS
 */
public class Locked extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(Locked.class);
  private static final Marker ACTIVITY = MarkerFactory.getMarker("ACTIVITY");

  /**
   * Constructor de Locked.
   *
   * @param door La puerta que estará en estado bloqueado
   */
  public Locked(Door door) {
    super(door, States.LOCKED);
    LOG.debug("calling Door class to initialize Locked object");
  }

  /**
   * Intenta abrir la puerta. No es posible porque está bloqueada.
   */
  @Override
  public void open() {
    LOG.warn("Can't open door: {} because it's locked", door.getId());
  }

  /**
   * Intenta cerrar la puerta. Si ya está cerrada, no hace nada.
   */
  @Override
  public void close() {
    if (door.isClosed()) {
      LOG.warn("Can't close door: {} because it's already closed", door.getId());
    } else {
      LOG.debug("Door {} closed", door.getId());
    }
  }

  /**
   * Intenta bloquear la puerta. Ya está bloqueada, no hace nada.
   */
  @Override
  public void lock() {
    LOG.warn("Door {} is already locked", door.getId());
  }

  /**
   * Desbloquea la puerta, cambiando su estado a Unlocked.
   */
  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
    LOG.debug("Door {} unlocked", door.getId());
  }

  /**
   * Desbloquea la puerta temporalmente, cambiando su estado a UnlockedShortly.
   */
  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
    LOG.debug("Door {} unlocked shortly", door.getId());
  }
}
