package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa el estado "propped" (atascada) de una puerta.
 * Este estado ocurre cuando una puerta en UnlockedShortly permanece abierta
 * después de 10 segundos. La puerta está abierta y no se puede bloquear
 * hasta que se cierre manualmente.
 *
 * @author Sistema ACS
 */
public class Propped extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(Propped.class);

  /**
   * Constructor de Propped.
   *
   * @param door La puerta que estará en estado propped
   */
  public Propped(Door door) {
    super(door, "propped");
    LOG.debug("Initializing Propped object by calling DoorState class");
  }

  /**
   * Obtiene el nombre del estado.
   *
   * @return El nombre "propped"
   */
  @Override
  public String getName() {
    return "propped";
  }

  /**
   * Intenta bloquear la puerta. No es posible porque está abierta y atascada.
   */
  @Override
  public void lock() {
    LOG.warn("Cannot lock: door {} is propped open.", door.getId());
  }

  /**
   * Intenta desbloquear la puerta. Ya está abierta, no hace nada.
   */
  @Override
  public void unlock() {
    LOG.warn("Door {} already open (propped).", door.getId());
  }

  /**
   * Intenta abrir la puerta. Ya está abierta, no hace nada.
   */
  @Override
  public void open() {
    LOG.warn("Door {} is already open (propped).", door.getId());
  }

  /**
   * Cierra la puerta y cambia su estado a Locked.
   */
  @Override
  public void close() {
    door.setClosed(true);
    door.setState(new Locked(door));
    LOG.debug("Door {} closed and now locked.", door.getId());
  }

  /**
   * Intenta desbloquear temporalmente la puerta. No es posible en este estado.
   */
  @Override
  public void unlockShortly() {
    LOG.debug("Cannot unlock shortly: door {} is propped open.", door.getId());
  }
}
