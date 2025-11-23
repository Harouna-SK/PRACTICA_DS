package baseNoStates.spaces;

/**
 * Visitor que busca un área por su identificador en la estructura jerárquica.
 * Recorre recursivamente todas las áreas hasta encontrar la que coincide
 * con el ID buscado.
 *
 * @author Sistema ACS
 */
public class FindAreaVisitor implements VisitorAreas<Area> {

  private String targetId;

  /**
   * Constructor del visitor de búsqueda.
   *
   * @param id El identificador del área a buscar
   */
  public FindAreaVisitor(String id) {
    this.targetId = id;
  }

  /**
   * Visita un espacio y comprueba si su ID coincide con el buscado.
   *
   * @param s El espacio a visitar
   * @return El espacio si coincide con el ID, null en caso contrario
   */
  @Override
  public Area visitSpace(Space s) {
    return s.getId().equals(targetId) ? s : null;
  }

  /**
   * Visita una partición y busca el área en ella y sus subáreas.
   * Primero comprueba si la partición coincide, luego busca recursivamente
   * en sus subáreas.
   *
   * @param p La partición a visitar
   * @return El área encontrada, o null si no se encuentra
   */
  @Override
  public Area visitPartition(Partition p) {
    if (p.getId().equals(targetId)) {
      return p;
    }

    for (Area sub : p.getSubAreas()) {
      Area found = sub.accept(this);
      if (found != null) {
        return found;
      }
    }
    return null;
  }
}
