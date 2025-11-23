package baseNoStates.requests;

import org.json.JSONObject;

/**
 * Interfaz que define una petición del sistema.
 * Todas las peticiones deben poder procesarse, convertirse a JSON
 * y representarse como cadena de texto.
 *
 * @author Sistema ACS
 */
public interface Request {
  /**
   * Convierte la respuesta de la petición a formato JSON.
   *
   * @return Un objeto JSON con la respuesta de la petición
   */
  JSONObject answerToJson();

  /**
   * Representa la petición como una cadena de texto.
   *
   * @return Representación en texto de la petición
   */
  String toString();

  /**
   * Procesa la petición, realizando las operaciones necesarias
   * según el tipo de petición.
   */
  void process();
}
