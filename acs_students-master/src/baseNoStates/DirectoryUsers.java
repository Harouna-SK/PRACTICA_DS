package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.spaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {
    //TODO: make user groups according to the specifications in the comments, because
    // now all are the same

    // Users sense privilegis
    // No tenen cap espai autoritzat
    users.add(new User("Bernat", "12345", new ArrayList<>()));
    users.add(new User("Blai", "77532", new ArrayList<>()));

    // Employees
    // Accés: ground floor, floor1, exterior, stairs
    List<Space> employeeSpaces = Arrays.asList(
        (Space) DirectoryAreas.findAreaById("hall"),
        (Space) DirectoryAreas.findAreaById("room1"),
        (Space) DirectoryAreas.findAreaById("room2"),
        (Space) DirectoryAreas.findAreaById("room3"),
        (Space) DirectoryAreas.findAreaById("corridor"),
        (Space) DirectoryAreas.findAreaById("IT")
    );
    users.add(new User("Ernest", "74984", new ArrayList<>(employeeSpaces)));
    users.add(new User("Eulalia", "43295", new ArrayList<>(employeeSpaces)));

    // Managers
    // Accés a tots els espais
    List<Space> allSpaces = DirectoryAreas.getAllSpaces(); // funció que retorna tots els Space
    users.add(new User("Manel", "95783", new ArrayList<>(allSpaces)));
    users.add(new User("Marta", "05827", new ArrayList<>(allSpaces)));

    // Admin
    // Accés complet també
    users.add(new User("Ana", "11343", new ArrayList<>(allSpaces)));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
