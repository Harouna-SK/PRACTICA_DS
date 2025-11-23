package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

import baseNoStates.Door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un espacio físico (habitación, sala, etc.) en el edificio.
 * Un espacio es un área terminal que puede tener puertas asociadas.
 * Extiende de Area y representa los nodos hoja en la jerarquía de áreas.
 */
public class Space extends Area {
  /** Lista de puertas que dan acceso a este espacio */
  private List<Door> doors = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Space.class);

  /**
   * Constructor de Space.
   * @param id Identificador único del espacio
   * @param description Descripción del espacio
   * @param parent Partición padre que contiene este espacio
   */
  public Space(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Called Area class to initialize Space object with id {} description {} partition {}", id, description, parent.getId());
  }

  /**
   * Añade una puerta a este espacio.
   * @param door La puerta que se añade al espacio
   */
  public void addDoor(Door door) {
    doors.add(door);
  }

  /**
   * Obtiene una copia de la lista de puertas de este espacio.
   * Este método es utilizado por el visitor DoorsVisitor.
   * @return Una nueva lista con las puertas del espacio
   */
  public List<Door> getDoors() {
    return new ArrayList<>(doors);
  }

  /**
   * Implementación del patrón Visitor para Space.
   * Delega la operación al método visitSpace del visitor.
   * @param visitor El visitor que realizará la operación
   * @param <T> Tipo de retorno de la operación del visitor
   * @return El resultado de visitSpace
   */
  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitSpace(this);
  }
}
