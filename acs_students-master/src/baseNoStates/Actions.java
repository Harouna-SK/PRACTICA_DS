package baseNoStates;

/**
 * Clase que define las constantes de acciones disponibles en el sistema.
 * Contiene las acciones posibles para peticiones de lectores y áreas,
 * así como las acciones para peticiones de puertas.
 *
 * @author Sistema ACS
 */
public final class Actions {
  /** Acción de bloquear (usada en peticiones de lectores y áreas). */
  public static final String LOCK = "lock";
  /** Acción de desbloquear (usada en peticiones de lectores y áreas). */
  public static final String UNLOCK = "unlock";
  /** Acción de desbloquear temporalmente (usada en peticiones de lectores y áreas). */
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  /** Acción de abrir (usada en peticiones de puertas). */
  public static final String OPEN = "open";
  /** Acción de cerrar (usada en peticiones de puertas). */
  public static final String CLOSE = "close";
}
