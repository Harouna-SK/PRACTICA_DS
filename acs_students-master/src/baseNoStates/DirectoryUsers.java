package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.spaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.*;
import java.util.*;


public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();
  private static final ArrayList<Group> groups = new ArrayList<>();

  public static void makeUsers() {
    //TODO: make user groups according to the specifications in the comments, because
    // now all are the same
    
    // dias habiles
    List<DayOfWeek> allDays = Arrays.asList(DayOfWeek.values());
    List<DayOfWeek> weekDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    List<DayOfWeek> managerDays = new ArrayList<DayOfWeek>(weekDays); 
    managerDays.add(DayOfWeek.SATURDAY);
    
    // schedules
    Schedule adminSchedule = new Schedule(LocalDate.MIN, LocalDate.MAX, allDays, LocalTime.MIN, LocalTime.MAX);
    Schedule managerSchedule = new Schedule(LocalDate.of(2025, 9, 1), LocalDate.of(2026, 3, 1), managerDays, LocalTime.of(8, 0), LocalTime.of(20, 0));
    Schedule employeeSchedule = new Schedule(LocalDate.of(2025, 9, 1), LocalDate.of(2026, 3, 1), weekDays, LocalTime.of(8, 0), LocalTime.of(17, 0)); 

    // espacios
    List<Space> allSpaces = DirectoryAreas.getAllSpaces();
    List<Space> employeeSpaces = Arrays.asList(
        (Space) DirectoryAreas.findAreaById("hall"),
        (Space) DirectoryAreas.findAreaById("room1"),
        (Space) DirectoryAreas.findAreaById("room2"),
        (Space) DirectoryAreas.findAreaById("corridor")
    );

    // grupos
    Group admin = new Group("Administrator", new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")), new ArrayList<>(allSpaces), adminSchedule);
    Group manager = new Group("Manager", new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")), new ArrayList<>(allSpaces), managerSchedule);
    Group employee = new Group("Employee", new ArrayList<>(Arrays.asList("open", "close", "unlock_shortly")), new ArrayList<>(employeeSpaces), employeeSchedule);
    Group blank = new Group("Blank", new ArrayList<>(), new ArrayList<>(), null);

    // usuarios
    users.add(new User("Bernat", "12345", new ArrayList<>(), blank));
    users.add(new User("Blai", "77532", new ArrayList<>(), blank));

    users.add(new User("Ernest", "74984", new ArrayList<>(employeeSpaces), employee));
    users.add(new User("Eulalia", "43295", new ArrayList<>(employeeSpaces), employee));

    users.add(new User("Manel", "95783", new ArrayList<>(allSpaces), admin));
    users.add(new User("Marta", "05827", new ArrayList<>(allSpaces), admin));
    users.add(new User("Ana", "11343", new ArrayList<>(allSpaces), admin));
    /*
    • Manager: Sept 1 2025 to Mar. 1 2026, week days plus Saturday, 8:00
    to 20:00, all actions, all spaces.
    • Employee : Sept 1 2025 to Mar. 1 2026, week days 9:00 to 17:00, open,
    close, unlock shortly everywhere but the parking.
    • Blank : group without any privilege, just to keep temporally users
    instead of deleting them, this is to withdraw all permissions but still
    to keep a user’s data to give back permissions later if needed.
    */
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
