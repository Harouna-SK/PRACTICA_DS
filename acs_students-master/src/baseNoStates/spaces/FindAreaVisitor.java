package baseNoStates.spaces;

/**
 * Visitor que busca un área por su identificador dentro de la estructura jerárquica.
 * Recorre recursivamente el árbol de áreas hasta encontrar el área con el ID buscado.
 * Si encuentra el área, la devuelve; si no, devuelve null.
 */
public class FindAreaVisitor implements VisitorAreas<Area> {

  /** El identificador del área que se está buscando */
  private String targetId;

  /**
   * Constructor del visitor.
   * @param id El identificador del área a buscar
   */
  public FindAreaVisitor(String id) {
    this.targetId = id;
  }

  /**
   * Visita un espacio y comprueba si su ID coincide con el buscado.
   * @param s El espacio visitado
   * @return El espacio si coincide con el ID, null en caso contrario
   */
  @Override
  public Area visitSpace(Space s) {
    return s.getId().equals(targetId) ? s : null;
  }

  /**
   * Visita una partición y busca el área en ella y sus subáreas.
   * Primero comprueba si la partición misma coincide con el ID buscado.
   * Luego busca recursivamente en todas sus subáreas.
   * @param p La partición visitada
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
