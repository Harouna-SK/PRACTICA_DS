package baseNoStates.spaces;

/**
 * Interfaz que define el patrón Visitor para las áreas del sistema.
 * Permite realizar operaciones sobre la estructura jerárquica de áreas
 * sin modificar las clases Area, Partition y Space.
 *
 * @param <T> Tipo de retorno de las operaciones del visitante
 * @author Sistema ACS
 */
public interface VisitorAreas<T> {
  /**
   * Visita una partición y realiza una operación sobre ella.
   *
   * @param p La partición a visitar
   * @return El resultado de la operación
   */
  T visitPartition(Partition p);

  /**
   * Visita un espacio y realiza una operación sobre él.
   *
   * @param s El espacio a visitar
   * @return El resultado de la operación
   */
  T visitSpace(Space s);
}
