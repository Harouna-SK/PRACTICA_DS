package baseNoStates.requests;

import baseNoStates.DirectoryUsers;
import baseNoStates.Door;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;

import baseNoStates.spaces.DirectoryAreas;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa una petición de un lector sobre una puerta específica.
 * Contiene la información de quién (credential), qué (action), cuándo (now)
 * y dónde (doorId). Procesa la petición verificando la autorización del
 * usuario y ejecutando la acción sobre la puerta si está autorizada.
 *
 * @author Sistema ACS
 */
public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;
  private static final Logger LOG = LoggerFactory.getLogger(RequestReader.class);

  /**
   * Constructor de RequestReader.
   *
   * @param credential Credencial del usuario que realiza la petición
   * @param action     Acción a realizar
   * @param now        Fecha/hora de la petición
   * @param doorId     Identificador de la puerta sobre la que se realiza la petición
   */
  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    LOG.debug("Initializing RequestReader object with credentials {} action {} "
        + "doorId {} now {}", credential, action, doorId, now);
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  /**
   * Obtiene la credencial del usuario.
   *
   * @return La credencial del usuario
   */
  public String getCredential() {
    return credential;
  }

  /**
   * Obtiene la fecha/hora de la petición.
   *
   * @return La fecha/hora de la petición
   */
  public LocalDateTime getNow() {
    return now;
  }

  /**
   * Establece el nombre del estado de la puerta.
   *
   * @param name El nombre del estado de la puerta
   */
  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  /**
   * Obtiene la acción de la petición.
   *
   * @return La acción a realizar
   */
  public String getAction() {
    return action;
  }

  /**
   * Indica si la petición está autorizada.
   *
   * @return true si está autorizada, false en caso contrario
   */
  public boolean isAuthorized() {
    return authorized;
  }

  /**
   * Añade una razón por la que la petición no está autorizada.
   *
   * @param reason La razón de la no autorización
   */
  public void addReason(String reason) {
    reasons.add(reason);
  }

  /**
   * Representa la petición como una cadena de texto.
   *
   * @return Representación en texto de la petición
   */
  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
        + "credential=" + credential
        + ", userName=" + userName
        + ", action=" + action
        + ", now=" + now
        + ", doorID=" + doorId
        + ", closed=" + doorClosed
        + ", authorized=" + authorized
        + ", reasons=" + reasons
        + "}";
  }

  /**
   * Convierte la respuesta de la petición a formato JSON.
   * Incluye información sobre autorización, acción, puerta, estado y razones.
   *
   * @return Un objeto JSON con la respuesta de la petición
   */
  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    LOG.debug("JSONObject created");
    return json;
  }

  /**
   * Procesa la petición del lector.
   * Verifica si la petición está autorizada y la envía a la puerta.
   * Si está autorizada, se realiza la acción. Incluso si no está autorizada,
   * se procesa la petición para poder registrar todas las peticiones realizadas.
   */
  @Override
  public void process() {
    User user = DirectoryUsers.getInstance().findUserByCredential(credential);
    Door door = DirectoryAreas.getInstance().findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  /**
   * Autoriza la petición verificando si el usuario puede realizar
   * la acción sobre el espacio al que da acceso la puerta.
   * El resultado se guarda en el atributo authorized de la petición.
   * Si no está autorizada, se añaden las razones correspondientes.
   *
   * @param user El usuario que realiza la petición
   * @param door La puerta sobre la que se realiza la petición
   */
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("User doesn't exist");
      LOG.debug("User does not exist");
      return;
    }
    LOG.debug("Processing request for user {} and door {}", user.getCredential(), door.getId());

    var targetSpace = door.getTo(); // área a la que da acceso la puerta

    boolean canPerform = user.getGroup().canPerform(action, targetSpace, now);

    if (canPerform) {
      authorized = true;
      userName = user.toString();
    } else {
      authorized = false;

      // Verificar qué permiso no se ha cumplido
      var group = user.getGroup();

      if (!group.getAllowedActions().contains(action)) {
        addReason("Action '" + action + "' not allowed for group " + group.getName());
        LOG.debug("Action '" + action + "' not allowed for group " + group.getName());
      }
      if (!group.getAllowedSpaces().contains(targetSpace)) {
        addReason("Space '" + targetSpace.getId() + "' not allowed for group " + group.getName());
        LOG.debug("Space '" + targetSpace.getId() + "' not allowed for group " + group.getName());
      }
      if (group.getSchedule() == null) {
        addReason("Group has no schedule");
        LOG.debug("Group has no schedule");
      } else if (!group.getSchedule().isWithinSchedule(now)) {
        addReason("Current date/time not within schedule for group " + group.getName());
        LOG.debug("Current date/time not within schedule for group " + group.getName());
      }
    }
  }
}
