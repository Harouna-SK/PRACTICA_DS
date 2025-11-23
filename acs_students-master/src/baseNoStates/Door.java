package baseNoStates;

import baseNoStates.requests.RequestReader;
import baseNoStates.spaces.Area;
import baseNoStates.clock.Clock;
import baseNoStates.clock.ClockObserver;
import org.json.JSONObject;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Clase que representa una puerta del sistema.
 * Una puerta conecta dos áreas y puede estar en diferentes estados
 * (Locked, Unlocked, UnlockedShortly, Propped). Implementa ClockObserver
 * para gestionar el auto-bloqueo después de un período de tiempo.
 *
 * @author Sistema ACS
 */
public class Door implements ClockObserver {
  private final String id;
  private boolean closed; // physically
  private DoorState state; // logical state
  private Area from;
  private Area to;

  // Nuevos atributos para el reloj
  private LocalDateTime checkAt = null;
  private boolean waitingForAutoLock = false;

  private static final Logger LOG = LoggerFactory.getLogger(Door.class);
  private static final Marker ACTIVITY = MarkerFactory.getMarker("ACTIVITY");

  /**
   * Constructor de Door.
   *
   * @param id   Identificador único de la puerta
   * @param from Área de origen
   * @param to   Área de destino
   */
  public Door(String id, Area from, Area to) {
    this.id = id;
    this.from = from;
    this.to = to;
    this.closed = true;
    this.state = new Locked(this);
  }

  /**
   * Procesa una petición de lector sobre esta puerta.
   * Es la puerta la que procesa la petición porque tiene y conoce
   * su estado, y si está cerrada o abierta.
   *
   * @param request La petición del lector a procesar
   */
  public void processRequest(RequestReader request) {
    LOG.info(ACTIVITY, "User with id {} trying to {} door {} at {}",
        request.getCredential(), request.getAction(), getId(), request.getNow());
    if (request.isAuthorized()) {
      LOG.info(ACTIVITY, "Request authorized");
      String action = request.getAction();
      doAction(action);
      LOG.info(ACTIVITY, "Action done");
    } else {
      LOG.warn(ACTIVITY, "Request not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  /**
   * Ejecuta una acción sobre la puerta según su estado actual.
   *
   * @param action La acción a ejecutar
   */
  private void doAction(String action) {
    LOG.debug("Doing action {}", action);
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
        state.unlockShortly();
        break;
      default:
        LOG.warn(ACTIVITY, "Unknown action: {}", action);
    }
  }

  /**
   * Establece un nuevo estado para la puerta.
   * Si el nuevo estado es UnlockedShortly, activa el temporizador
   * para el auto-bloqueo después de 10 segundos.
   *
   * @param newState El nuevo estado de la puerta
   */
  public void setState(DoorState newState) {
    this.state = newState;

    // Si es UnlockedShortly, activar temporizador
    if (getStateName().equalsIgnoreCase("unlocked_shortly")) {
      this.waitingForAutoLock = true;
      this.checkAt = LocalDateTime.now().plusSeconds(10);
      Clock.getInstance().registerObserver(this);
      LOG.debug("Door {} will auto-check in 10s", id);
    } else {
      // Si no, no lo usamos
      this.waitingForAutoLock = false;
      this.checkAt = null;
      Clock.getInstance().unregisterObserver(this);
    }
  }

  /**
   * Indica si la puerta está físicamente cerrada.
   *
   * @return true si está cerrada, false si está abierta
   */
  public boolean isClosed() {
    return closed;
  }

  /**
   * Establece si la puerta está físicamente cerrada.
   *
   * @param closed true para cerrar, false para abrir
   */
  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  /**
   * Obtiene el identificador de la puerta.
   *
   * @return El identificador de la puerta
   */
  public String getId() {
    return id;
  }

  /**
   * Obtiene el nombre del estado actual de la puerta.
   *
   * @return El nombre del estado
   */
  public String getStateName() {
    return state.getName();
  }

  /**
   * Obtiene el área de destino de la puerta.
   *
   * @return El área de destino
   */
  public Area getTo() {
    return to;
  }

  /**
   * Obtiene el área de origen de la puerta.
   *
   * @return El área de origen
   */
  public Area getFrom() {
    return from;
  }

  /**
   * Método llamado por el reloj cada segundo.
   * Si la puerta está esperando auto-bloqueo y ha pasado el tiempo,
   * cambia el estado según si está cerrada o no.
   *
   * @param now La fecha/hora actual del reloj
   */
  @Override
  public void tick(LocalDateTime now) {
    if (!waitingForAutoLock || checkAt == null) {
      return;
    }

    if (now.isAfter(checkAt)) {
      // Después de 10 segundos
      if (isClosed()) {
        setState(new Locked(this));
        LOG.debug("Door {} auto-locked after 10s", id);
      } else {
        setState(new Propped(this));
        LOG.debug("Door {} became propped after 10s", id);
      }
      waitingForAutoLock = false;
      Clock.getInstance().unregisterObserver(this);
    }
  }

  /**
   * Representa la puerta como una cadena de texto.
   *
   * @return Representación en texto de la puerta
   */
  @Override
  public String toString() {
    return "Door{"
        + "id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  /**
   * Convierte la puerta a formato JSON.
   *
   * @return Un objeto JSON con la información de la puerta
   */
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }
}
