package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

import baseNoStates.Door;

/**
 * Visitor que recopila todas las puertas de un área y sus subáreas.
 * Recorre recursivamente la estructura jerárquica para obtener todas las puertas.
 * Cuando visita un espacio, obtiene sus puertas directamente.
 * Cuando visita una partición, recopila las puertas de todas sus subáreas.
 */
public class DoorsVisitor implements VisitorAreas<List<Door>> {

  /**
   * Visita un espacio y devuelve sus puertas.
   * @param s El espacio visitado
   * @return Lista de puertas del espacio
   */
  @Override
  public List<Door> visitSpace(Space s) {
    return s.getDoors();
  }

  /**
   * Visita una partición y recopila las puertas de todas sus subáreas.
   * Recorre recursivamente todas las subáreas aplicando el visitor.
   * @param p La partición visitada
   * @return Lista con todas las puertas de las subáreas de la partición
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
