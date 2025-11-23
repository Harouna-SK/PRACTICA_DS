package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa el estado desbloqueado de una puerta.
 * En este estado, la puerta puede abrirse y cerrarse libremente.
 * Si se intenta bloquear y está cerrada, cambia a estado Locked.
 *
 * @author Sistema ACS
 */
public class Unlocked extends DoorState {
  private static final Logger LOG = LoggerFactory.getLogger(Unlocked.class);

  /**
   * Constructor de Unlocked.
   *
   * @param door La puerta que estará en estado desbloqueado
   */
  public Unlocked(Door door) {
    super(door, States.UNLOCKED);
    LOG.debug("Unlocked object initialized calling DoorState constructor");
  }

  /**
   * Abre la puerta si está cerrada.
   */
  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClosed(false);
      LOG.debug("Door {} opened", door.getId());
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
      LOG.debug("Door {} closed", door.getId());
    }
  }

  /**
   * Bloquea la puerta si está cerrada, cambiando su estado a Locked.
   */
  @Override
  public void lock() {
    if (!door.isClosed()) {
      LOG.debug("Can't lock door {} because it's open", door.getId());
    } else {
      door.setState(new Locked(door));
      LOG.debug("Door {} locked", door.getId());
    }
  }

  /**
   * Intenta desbloquear la puerta. Ya está desbloqueada, no hace nada.
   */
  @Override
  public void unlock() {
    LOG.debug("Door {} is already unlocked", door.getId());
  }

  /**
   * Intenta desbloquear temporalmente la puerta. Ya está desbloqueada, no hace nada.
   */
  @Override
  public void unlockShortly() {
    LOG.debug("Door {} is already unlocked", door.getId());
  }
}

