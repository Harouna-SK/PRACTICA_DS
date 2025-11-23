package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa una partición en el edificio.
 * Una Partition es un contenedor que puede tener subáreas (otras particiones o espacios).
 * Ejemplos de particiones: building, basement, ground_floor, floor1, etc.
 * Forma una estructura jerárquica de áreas.
 *
 * @author Sistema ACS
 */
public class Partition extends Area {
  private List<Area> subAreas = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Partition.class);

  /**
   * Constructor de Partition.
   *
   * @param id          Identificador único de la partición
   * @param description Descripción de la partición
   * @param parent      Partición padre a la que pertenece (null si es la raíz)
   */
  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Partition initialized");
  }

  /**
   * Añade una subárea a esta partición.
   *
   * @param area La subárea a añadir
   */
  public void addSubArea(Area area) {
    subAreas.add(area);
  }

  /**
   * Obtiene la lista de subáreas de esta partición.
   *
   * @return La lista de subáreas
   */
  public List<Area> getSubAreas() {
    return subAreas;
  }

  /**
   * Implementación del patrón Visitor para Partition.
   * Delega la visita al método visitPartition del visitante.
   *
   * @param <T>     Tipo de retorno del visitante
   * @param visitor El visitante que procesará esta partición
   * @return El resultado de la visita
   */
  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitPartition(this);
  }
}