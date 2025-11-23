package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import baseNoStates.Door;

/**
 * Clase que gestiona la estructura jerárquica de áreas del edificio.
 * Actúa como un directorio centralizado que mantiene la referencia a la raíz del árbol
 * de áreas y proporciona métodos estáticos para buscar áreas y puertas.
 * También contiene el método que inicializa toda la estructura del edificio.
 */
public class DirectoryAreas {
  /** Área raíz del árbol jerárquico (normalmente el edificio completo) */
  private static Area rootArea;
  /** Lista de todas las puertas del edificio */
  private static ArrayList<Door> allDoors;
  private static Logger logger;

  /**
   * Inicializa la estructura completa del edificio creando todas las particiones,
   * espacios y puertas. Construye la jerarquía:
   * - building (raíz)
   *   - basement (sótano)
   *   - exterior (exterior)
   *   - ground_floor (planta baja)
   *   - floor1 (primera planta)
   *   - stairs (escaleras)
   * Y los espacios correspondientes con sus puertas.
   */
  public static void makeAreas() {
    Partition building = new Partition("building", "...", null);
    Partition basement = new Partition("basement", "...", building);
    Partition exterior = new Partition("exterior", "...", building);
    Partition ground_floor = new Partition("ground_floor", "...", building);
    Partition floor1 = new Partition("floor1", "...", building);
    Partition stairs = new Partition("stairs", "...", building);

    Space parking = new Space("parking", "...", basement);
    Space hall  = new Space("hall", "...", ground_floor);
    Space room1 = new Space("room1", "...", ground_floor);
    Space room2 = new Space("room2", "...", ground_floor);
    Space room3 = new Space("room3", "...", floor1);
    Space corridor = new Space("corridor", "...", floor1);
    Space IT = new Space("IT", "...", floor1);

    // basement
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);
    parking.addDoor(d1);
    parking.addDoor(d2);

    // ground floor
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", stairs, hall);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);
    hall.addDoor(d3);
    hall.addDoor(d4);
    room1.addDoor(d5);
    room2.addDoor(d6);

    // first floor
    Door d7 = new Door("D7", stairs, corridor);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, IT);
    corridor.addDoor(d7);
    room3.addDoor(d8);
    IT.addDoor(d9);

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
    rootArea = building;
  }

  /**
   * Busca un área por su identificador dentro del árbol jerárquico.
   * Utiliza el patrón Visitor (FindAreaVisitor) para recorrer la estructura.
   * @param id El identificador del área a buscar
   * @return El área encontrada, o null si no existe
   */
  public static Area findAreaById(String id) {
    if (rootArea == null) {
      return null;
    }
    return rootArea.accept(new FindAreaVisitor(id));
  }

  /**
   * Busca una puerta por su identificador en la lista de todas las puertas.
   * @param id El identificador de la puerta a buscar
   * @return La puerta encontrada, o null si no existe
   */
  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    logger.warning("No Door found with id " + id);
    return null;
  }

  /**
   * Obtiene todas las puertas del edificio.
   * Este método es necesario para RequestRefresh.
   * @return Lista con todas las puertas del edificio
   */
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

  /**
   * Obtiene todos los espacios (habitaciones) del edificio.
   * Utiliza el patrón Visitor (GetSpaceVisitor) para recorrer la estructura.
   * @return Lista con todos los espacios del edificio
   */
  public static List<Space> getAllSpaces() {
    if (rootArea == null) {
      System.out.println("Root area not initialized. Call makeAreas() first.");
      return new ArrayList<>();
    }
    return rootArea.accept(new GetSpaceVisitor());
  }
}
