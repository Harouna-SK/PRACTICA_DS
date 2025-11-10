package baseNoStates.spaces;

import baseNoStates.Door;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DirectoryAreas {
  private static Area rootArea;
  private static ArrayList<Door> allDoors;
  private static Logger logger;

  public static void makeAreas(){
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

  // Busca un área por su id dentro del árbol
  public static Area findAreaById(String id) {
    if (rootArea == null) return null;
    return rootArea.findAreaById(id);
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    //System.out.println("door with id " + id + " not found");
    logger.warning("No Door found with id " + id);
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

  public static List<Space> getAllSpaces() {
    if (rootArea == null) {
      System.out.println("Root area not initialized. Call makeAreas() first.");
      return new ArrayList<>();
    }
    return rootArea.getSpaces();
  }

}
