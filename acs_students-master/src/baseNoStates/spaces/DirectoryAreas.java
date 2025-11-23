package baseNoStates.spaces;

import baseNoStates.Door;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase singleton que gestiona el directorio de áreas del edificio.
 * Mantiene la estructura jerárquica de áreas (particiones y espacios)
 * y proporciona métodos para buscar áreas, puertas y espacios.
 * Inicializa la estructura completa del edificio con todas sus áreas y puertas.
 *
 * @author Sistema ACS
 */
public class DirectoryAreas {
  private static DirectoryAreas instance;
  private Area rootArea;
  private ArrayList<Door> allDoors;
  private final Logger logger = Logger.getLogger(DirectoryAreas.class.getName());

  /**
   * Constructor privado para implementar el patrón Singleton.
   */
  private DirectoryAreas() {
  }

  /**
   * Obtiene la instancia única del directorio (patrón Singleton).
   *
   * @return La instancia única de DirectoryAreas
   */
  public static synchronized DirectoryAreas getInstance() {
    if (instance == null) {
      instance = new DirectoryAreas();
    }
    return instance;
  }

  /**
   * Crea la estructura completa del edificio con todas sus áreas y puertas.
   * Inicializa las particiones (building, basement, exterior, ground_floor, etc.),
   * los espacios (parking, hall, room1, room2, etc.) y las puertas que los conectan.
   */
  public void makeAreas() {
    Partition building = new Partition("building", "...", null);
    Partition basement = new Partition("basement", "...", building);
    Partition exterior = new Partition("exterior", "...", building);
    Partition groundFloor = new Partition("ground_floor", "...", building);
    Partition floor1 = new Partition("floor1", "...", building);
    Partition stairs = new Partition("stairs", "...", building);

    Space parking = new Space("parking", "...", basement);
    Space hall = new Space("hall", "...", groundFloor);
    Space room1 = new Space("room1", "...", groundFloor);
    Space room2 = new Space("room2", "...", groundFloor);
    Space room3 = new Space("room3", "...", floor1);
    Space corridor = new Space("corridor", "...", floor1);
    Space it = new Space("IT", "...", floor1);

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
    Door d9 = new Door("D9", corridor, it);
    corridor.addDoor(d7);
    room3.addDoor(d8);
    it.addDoor(d9);

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
    rootArea = building;
  }

  /**
   * Busca un área por su identificador dentro del árbol de áreas.
   *
   * @param id El identificador del área a buscar
   * @return El área encontrada, o null si no existe
   */
  public Area findAreaById(String id) {
    if (rootArea == null) {
      return null;
    }
    return rootArea.accept(new FindAreaVisitor(id));
  }

  /**
   * Busca una puerta por su identificador en la lista de todas las puertas.
   *
   * @param id El identificador de la puerta a buscar
   * @return La puerta encontrada, o null si no existe
   */
  public Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    logger.warning("No Door found with id " + id);
    return null;
  }

  /**
   * Obtiene todas las puertas del sistema.
   * Este método es utilizado por RequestRefresh.
   *
   * @return Lista con todas las puertas del sistema
   */
  public ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

  /**
   * Obtiene todos los espacios (Space) del edificio.
   * Recorre recursivamente la estructura de áreas para encontrar todos los espacios.
   *
   * @return Lista con todos los espacios del edificio
   */
  public List<Space> getAllSpaces() {
    if (rootArea == null) {
      System.out.println("Root area not initialized. Call makeAreas() first.");
      return new ArrayList<>();
    }
    return rootArea.accept(new GetSpaceVisitor());
  }
}

