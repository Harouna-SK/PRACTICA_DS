package baseNoStates.requests;

import baseNoStates.Actions;
import baseNoStates.Door;
import baseNoStates.spaces.Area;
import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.spaces.DoorsVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa una petición sobre un área completa.
 * Procesa la petición creando peticiones individuales para cada puerta
 * que da acceso al área. Para algunas puertas la acción puede estar
 * autorizada y se realizará, para otras no estará autorizada y no
 * ocurrirá nada.
 *
 * @author Sistema ACS
 */
public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private ArrayList<RequestReader> requests = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(RequestArea.class);

  /**
   * Constructor de RequestArea.
   *
   * @param credential Credencial del usuario que realiza la petición
   * @param action     Acción a realizar (LOCK o UNLOCK)
   * @param now        Fecha/hora de la petición
   * @param areaId     Identificador del área sobre la que se realiza la petición
   */
  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
        : "invalid action " + action + " for an area request";
    this.action = action;
    this.now = now;
    LOG.debug("Initialized RequestArea object with credential {} action {} "
        + "now {} areaId {}", credential, action, now, areaId);
  }

  /**
   * Obtiene la acción de la petición.
   *
   * @return La acción (LOCK o UNLOCK)
   */
  public String getAction() {
    return action;
  }

  /**
   * Convierte la respuesta de la petición a formato JSON.
   * Incluye la acción, el área y todas las peticiones de puertas resultantes.
   *
   * @return Un objeto JSON con la respuesta de la petición
   */
  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    return json;
  }

  /**
   * Representa la petición como una cadena de texto.
   *
   * @return Representación en texto de la petición
   */
  @Override
  public String toString() {
    String requestsDoorsStr;
    if (requests.size() == 0) {
      requestsDoorsStr = "";
    } else {
      requestsDoorsStr = requests.toString();
    }
    return "Request{"
        + "credential=" + credential
        + ", action=" + action
        + ", now=" + now
        + ", areaId=" + areaId
        + ", requestsDoors=" + requestsDoorsStr
        + "}";
  }

  /**
   * Procesa la petición del área.
   * Busca el área correspondiente, obtiene todas las puertas que dan acceso
   * a esa área, y crea y procesa una petición individual para cada puerta.
   * El resultado de cada petición de puerta se añade a la lista de sub-peticiones.
   */
  @Override
  public void process() {
    // Buscar el área correspondiente
    LOG.debug("Searching for area {}", areaId);
    Area area = DirectoryAreas.getInstance().findAreaById(areaId);

    if (area == null) {
      LOG.debug("Area with id " + areaId + " not found");
      return;
    }

    // Obtener todas las puertas que dan acceso a esta área
    List<Door> doors = area.accept(new DoorsVisitor());

    if (doors == null || doors.isEmpty()) {
      LOG.debug("No doors give access to area " + areaId);
      return;
    }

    // Crear y procesar una petición por cada puerta
    for (Door door : doors) {
      RequestReader requestReader = new RequestReader(credential, action, now, door.getId());

      // Procesa la petición del lector (autorización + acción)
      requestReader.process();

      // Añade la respuesta a la lista de sub-peticiones
      requests.add(requestReader);
    }
    LOG.debug("Area {} processed successfully", areaId);
  }
}
