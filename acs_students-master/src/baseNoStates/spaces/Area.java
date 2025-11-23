package baseNoStates.spaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase abstracta que representa un área genérica en el sistema.
 * Las áreas pueden ser espacios (Space) o particiones (Partition).
 * Implementa el patrón Visitor para permitir operaciones sobre la estructura
 * jerárquica de áreas.
 *
 * @author Sistema ACS
 */
public abstract class Area {
  protected String id;
  protected String description;
  protected Partition parent;
  private static final Logger LOG = LoggerFactory.getLogger(Area.class);

  /**
   * Constructor de Area.
   *
   * @param id          Identificador único del área
   * @param description Descripción del área
   * @param parent      Partición padre a la que pertenece este área (null si es la raíz)
   */
  public Area(String id, String description, Partition parent) {
    this.id = id;
    this.description = description;
    this.parent = parent;
    if (parent != null) {
      parent.addSubArea(this);
    }
    LOG.debug("Initialized area with id {} description {}", id, description);
  }

  /**
   * Obtiene el identificador del área.
   *
   * @return El identificador del área
   */
  public String getId() {
    return id;
  }

  /**
   * Obtiene la partición padre de este área.
   *
   * @return La partición padre, o null si es la raíz
   */
  public Partition getParent() {
    return parent;
  }

  /**
   * Método del patrón Visitor que permite a los visitantes procesar
   * este área según su tipo concreto (Space o Partition).
   *
   * @param <T>     Tipo de retorno del visitante
   * @param visitor El visitante que procesará esta área
   * @return El resultado de la visita
   */
  public abstract <T> T accept(VisitorAreas<T> visitor);
}
