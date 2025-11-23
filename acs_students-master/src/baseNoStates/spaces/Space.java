package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un espacio concreto en el edificio.
 * Un Space es un área terminal que puede contener puertas.
 * Ejemplos de espacios: parking, hall, room1, room2, etc.
 *
 * @author Sistema ACS
 */
public class Space extends Area {
  private List<Door> doors = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Space.class);

  /**
   * Constructor de Space.
   *
   * @param id          Identificador único del espacio
   * @param description Descripción del espacio
   * @param parent      Partición padre a la que pertenece este espacio
   */
  public Space(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Called Area class to initialize Space object with id {} "
        + "description {} partition {}", id, description, parent.getId());
  }

  /**
   * Añade una puerta a este espacio.
   *
   * @param door La puerta a añadir
   */
  public void addDoor(Door door) {
    doors.add(door);
  }

  /**
   * Obtiene una copia de la lista de puertas de este espacio.
   * Este método es utilizado por el visitor DoorsVisitor.
   *
   * @return Una nueva lista con las puertas del espacio
   */
  public List<Door> getDoors() {
    return new ArrayList<>(doors);
  }

  /**
   * Implementación del patrón Visitor para Space.
   * Delega la visita al método visitSpace del visitante.
   *
   * @param <T>     Tipo de retorno del visitante
   * @param visitor El visitante que procesará este espacio
   * @return El resultado de la visita
   */
  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitSpace(this);
  }
}
