package baseNoStates.spaces;

/**
 * Interfaz que define el patrón Visitor para las áreas.
 * Permite realizar operaciones sobre la estructura jerárquica de áreas
 * sin modificar las clases Area, Partition y Space.
 * Cada visitor implementa operaciones específicas visitando particiones y espacios.
 * 
 * @param <T> Tipo de retorno de las operaciones del visitor
 */
public interface VisitorAreas<T> {
  /**
   * Método que se ejecuta cuando se visita una partición.
   * @param p La partición visitada
   * @return El resultado de la operación sobre la partición
   */
  T visitPartition(Partition p);

  /**
   * Método que se ejecuta cuando se visita un espacio.
   * @param s El espacio visitado
   * @return El resultado de la operación sobre el espacio
   */
  T visitSpace(Space s);
}
