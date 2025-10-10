package baseNoStates.spaces;

import baseNoStates.Door;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectoryAreas {
  private static Area rootArea;
  private static ArrayList<Door> allDoors;

  public static void makeAreas(){
    Partition building = new Partition("building", "...", null);
    Partition basement = new Partition("basement", "...", building);
    Partition exterior = new Partition("exterior", "...", building);
    Partition groundfloor = new Partition("groundfloor", "...", building);
    Partition floor1 = new Partition("floor1", "...", building);
    Partition stairs = new Partition("stairs", "...", building);

    Space parking = new Space("parking", "...", basement);
    Space hall  = new Space("hall", "...", groundfloor);
    Space room1 = new Space("room1", "...", groundfloor);
    Space room2 = new Space("room2", "...", groundfloor);
    Space room3 = new Space("room3", "...", floor1);
    Space corridor = new Space("corridor", "...", floor1);
    Space IT = new Space("IT", "...", floor1);

    // basement
    Door d1 = new Door("D1", exterior, parking); // exterior, parking
    Door d2 = new Door("D2", stairs, parking); // stairs, parking

    // ground floor
    Door d3 = new Door("D3", exterior, hall); // exterior, hall
    Door d4 = new Door("D4", stairs, hall); // stairs, hall
    Door d5 = new Door("D5", hall, room1); // hall, room1
    Door d6 = new Door("D6", hall, room2); // hall, room2

    // first floor
    Door d7 = new Door("D7", stairs, corridor); // stairs, corridor
    Door d8 = new Door("D8", corridor, room3); // corridor, room3
    Door d9 = new Door("D9", corridor, IT); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));

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
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

}
