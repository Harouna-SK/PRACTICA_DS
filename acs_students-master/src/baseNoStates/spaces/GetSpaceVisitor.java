package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor que recopila todos los espacios (habitaciones) de un área y sus subáreas.
 * Recorre recursivamente la estructura jerárquica para obtener todos los espacios.
 * Cuando visita un espacio, lo añade a la lista.
 * Cuando visita una partición, recopila los espacios de todas sus subáreas.
 */
public class GetSpaceVisitor implements VisitorAreas<List<Space>> {

  /**
   * Visita un espacio y lo añade a la lista de espacios.
   * @param s El espacio visitado
   * @return Lista con el espacio visitado
   */
  @Override
  public List<Space> visitSpace(Space s) {
    List<Space> list = new ArrayList<>();
    list.add(s);
    return list;
  }

  /**
   * Visita una partición y recopila todos los espacios de sus subáreas.
   * Recorre recursivamente todas las subáreas aplicando el visitor.
   * @param p La partición visitada
   * @return Lista con todos los espacios contenidos en la partición y sus subáreas
   */
  @Override
  public List<Space> visitPartition(Partition p) {
    List<Space> spaces = new ArrayList<>();
    for (Area sub : p.getSubAreas()) {
      spaces.addAll(sub.accept(this));
    }
    return spaces;
  }
}
