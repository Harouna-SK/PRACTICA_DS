package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa una partición (contenedor) en el edificio.
 * Una partición puede contener otras particiones o espacios, formando una estructura jerárquica.
 * Extiende de Area y representa los nodos intermedios en la jerarquía de áreas.
 * Ejemplos: edificio, planta, sótano, etc.
 */
public class Partition extends Area {
  /** Lista de subáreas contenidas en esta partición (pueden ser particiones o espacios) */
  private List<Area> subAreas = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Partition.class);

  /**
   * Constructor de Partition.
   * @param id Identificador único de la partición
   * @param description Descripción de la partición
   * @param parent Partición padre que contiene esta partición (null si es la raíz)
   */
  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Partition initialized");
  }

  /**
   * Añade una subárea a esta partición.
   * @param area El área (partición o espacio) que se añade como subárea
   */
  public void addSubArea(Area area) {
    subAreas.add(area);
  }

  /**
   * Obtiene la lista de subáreas contenidas en esta partición.
   * @return La lista de subáreas
   */
  public List<Area> getSubAreas() {
    return subAreas;
  }

  /**
   * Implementación del patrón Visitor para Partition.
   * Delega la operación al método visitPartition del visitor.
   * @param visitor El visitor que realizará la operación
   * @param <T> Tipo de retorno de la operación del visitor
   * @return El resultado de visitPartition
   */
  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitPartition(this);
  }
}
