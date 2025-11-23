package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

/**
 * Visitor que recopila todas las puertas de un área y sus subáreas.
 * Recorre recursivamente la estructura jerárquica de áreas para
 * obtener todas las puertas contenidas en espacios.
 *
 * @author Sistema ACS
 */
public class DoorsVisitor implements VisitorAreas<List<Door>> {

  /**
   * Visita un espacio y devuelve sus puertas.
   *
   * @param s El espacio a visitar
   * @return Lista de puertas del espacio
   */
  @Override
  public List<Door> visitSpace(Space s) {
    return s.getDoors();
  }

  /**
   * Visita una partición y recopila todas las puertas de sus subáreas.
   * Recorre recursivamente todas las subáreas para obtener todas las puertas.
   *
   * @param p La partición a visitar
   * @return Lista de todas las puertas encontradas en las subáreas
   */
  @Override
  public List<Door> visitPartition(Partition p) {
    List<Door> all = new ArrayList<>();
    for (Area sub : p.getSubAreas()) {
      all.addAll(sub.accept(this));
    }
    return all;
  }
}
