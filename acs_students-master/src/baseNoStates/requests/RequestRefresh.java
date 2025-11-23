package baseNoStates.requests;

import baseNoStates.Door;
import java.util.ArrayList;

import baseNoStates.spaces.DirectoryAreas;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase que representa una petición de actualización (refresh) del estado
 * de todas las puertas del sistema.
 * Se utiliza para pintar el simulador cuando se carga la página, y para
 * mostrar las puertas y lectores después de cambiar de locked a propped
 * o viceversa, presionando el botón Refresh Request del simulador.
 * También se usa para probar rápidamente si las peticiones de partición
 * enviadas por la aplicación cliente en Flutter funcionan o no, recuperando
 * el estado de todas las puertas para que el simulador pueda repintar los lectores.
 *
 * @author Sistema ACS
 */
public class RequestRefresh implements Request {
  private final ArrayList<JSONObject> jsonsDoors = new ArrayList<>();

  /**
   * Convierte la respuesta de la petición a formato JSON.
   * Incluye el estado de todas las puertas en formato JSON.
   *
   * @return Un objeto JSON con el estado de todas las puertas
   */
  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("doors", new JSONArray(jsonsDoors));
    // jsonDoors has been set previously by process()
    return json;
  }

  /**
   * Representa la petición como una cadena de texto.
   *
   * @return Representación en texto de la petición
   */
  @Override
  public String toString() {
    return "RequestRefresh{"
        + jsonsDoors
        + "}";
  }

  /**
   * Procesa la petición de actualización.
   * Recupera el estado de todas las puertas del sistema y las convierte
   * a formato JSON para su posterior envío.
   */
  @Override
  public void process() {
    for (Door door : DirectoryAreas.getInstance().getAllDoors()) {
      jsonsDoors.add(door.toJson());
    }
  }
}
