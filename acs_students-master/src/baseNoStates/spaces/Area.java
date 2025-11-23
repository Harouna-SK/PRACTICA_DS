package baseNoStates.spaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase abstracta que representa una área genérica en el sistema de control de acceso.
 * Las áreas pueden ser particiones (contenedores) o espacios (habitaciones).
 * Implementa el patrón Visitor para permitir operaciones sobre la estructura jerárquica.
 */
public abstract class Area {
  /** Identificador único del área */
  protected String id;
  /** Descripción del área */
  protected String description;
  /** Partición padre que contiene esta área (null si es la raíz) */
  protected Partition parent;
  private static final Logger LOG = LoggerFactory.getLogger(Area.class);
  
  /**
   * Constructor de Area.
   * @param id Identificador único del área
   * @param description Descripción del área
   * @param parent Partición padre que contiene esta área (puede ser null para la raíz)
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
   * @return El identificador del área
   */
  public String getId() {
    return id;
  }

  /**
   * Obtiene la partición padre de esta área.
   * @return La partición padre, o null si es la raíz
   */
  public Partition getParent() {
    return parent;
  }

  /**
   * Método del patrón Visitor que permite aplicar operaciones sobre el área.
   * Cada subclase implementa este método para delegar al visitor correspondiente.
   * @param visitor El visitor que realizará la operación
   * @param <T> Tipo de retorno de la operación del visitor
   * @return El resultado de la operación del visitor
   */
  public abstract <T> T accept(VisitorAreas<T> visitor);
}
