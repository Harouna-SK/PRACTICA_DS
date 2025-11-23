package baseNoStates;

/**
 * Clase abstracta que representa un estado de una puerta.
 * Implementa el patrón State para gestionar los diferentes estados
 * que puede tener una puerta (Locked, Unlocked, UnlockedShortly, Propped).
 * Cada estado define el comportamiento de las acciones sobre la puerta.
 *
 * @author Sistema ACS
 */
public abstract class DoorState {
  protected Door door;
  protected String name;

  /**
   * Constructor de DoorState.
   *
   * @param door La puerta asociada a este estado
   * @param name El nombre del estado
   */
  public DoorState(Door door, String name) {
    this.door = door;
    this.name = name;
  }

  /**
   * Obtiene el nombre del estado.
   *
   * @return El nombre del estado
   */
  public String getName() {
    return name;
  }

  /**
   * Abre la puerta. El comportamiento depende del estado concreto.
   */
  public abstract void open();

  /**
   * Cierra la puerta. El comportamiento depende del estado concreto.
   */
  public abstract void close();

  /**
   * Bloquea la puerta. El comportamiento depende del estado concreto.
   */
  public abstract void lock();

  /**
   * Desbloquea la puerta. El comportamiento depende del estado concreto.
   */
  public abstract void unlock();

  /**
   * Desbloquea la puerta temporalmente (por un corto período).
   * El comportamiento depende del estado concreto.
   */
  public abstract void unlockShortly();
}
