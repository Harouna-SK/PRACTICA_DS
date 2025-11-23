package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa el estado desbloqueado temporalmente de una puerta.
 * En este estado, la puerta está desbloqueada por un período corto (10 segundos).
 * Después de este tiempo, si está cerrada cambia a Locked, si está abierta cambia a Propped.
 * No se puede bloquear ni desbloquear manualmente en este estado.
 *
 * @author Sistema ACS
 */
public class UnlockedShortly extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(UnlockedShortly.class);

  /**
   * Constructor de UnlockedShortly.
   *
   * @param door La puerta que estará en estado desbloqueado temporalmente
   */
  public UnlockedShortly(Door door) {
    super(door, "unlocked_shortly");
    LOG.debug("UnlockedShortly object initialized calling DoorState constructor");
  }

  /**
   * Abre la puerta si está cerrada.
   */
  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      LOG.debug("Door {} opened (was unlocked shortly)", door.getId());
    } else {
      LOG.debug("Can't open door {} because it's already open", door.getId());
    }
  }

  /**
   * Cierra la puerta si está abierta.
   */
  @Override
  public void close() {
    if (door.isClosed()) {
      LOG.debug("Can't close door {} because it's already closed", door.getId());
    } else {
      door.setClosed(true);
      LOG.debug("Door {} closed (was unlocked shortly)", door.getId());
    }
  }

  /**
   * Intenta bloquear la puerta. No es posible en este estado.
   */
  @Override
  public void lock() {
    LOG.debug("Can't lock door {} when in unlocked shortly state", door.getId());
  }

  /**
   * Intenta desbloquear la puerta. No es posible en este estado.
   */
  @Override
  public void unlock() {
    LOG.debug("Can't unlock door {} when in unlocked shortly state", door.getId());
  }

  /**
   * Intenta desbloquear temporalmente la puerta. Ya está en este estado, no hace nada.
   */
  @Override
  public void unlockShortly() {
    LOG.debug("Door {} is already in unlocked shortly state", door.getId());
  }
}