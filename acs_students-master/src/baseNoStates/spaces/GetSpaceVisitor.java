package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor que recopila todos los espacios (Space) de un área y sus subáreas.
 * Recorre recursivamente la estructura jerárquica para obtener todos los
 * espacios contenidos, ignorando las particiones intermedias.
 *
 * @author Sistema ACS
 */
public class GetSpaceVisitor implements VisitorAreas<List<Space>> {

  /**
   * Visita un espacio y lo añade a la lista de resultados.
   *
   * @param s El espacio a visitar
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
   * Recorre recursivamente todas las subáreas para obtener todos los espacios.
   *
   * @param p La partición a visitar
   * @return Lista de todos los espacios encontrados en las subáreas
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
