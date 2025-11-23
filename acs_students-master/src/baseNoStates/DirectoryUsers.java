package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.spaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Clase singleton que gestiona el directorio de usuarios del sistema.
 * Mantiene la lista de usuarios y proporciona métodos para buscar usuarios
 * por credencial. Inicializa todos los usuarios con sus grupos y permisos.
 *
 * @author Sistema ACS
 */
public final class DirectoryUsers {
  private static DirectoryUsers instance;
  private final ArrayList<User> users = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(DirectoryUsers.class);
  private static final Marker ACTIVITY = MarkerFactory.getMarker("ACTIVITY");

  /**
   * Constructor privado para implementar el patrón Singleton.
   */
  private DirectoryUsers() {
  }

  /**
   * Obtiene la instancia única del directorio (patrón Singleton).
   *
   * @return La instancia única de DirectoryUsers
   */
  public static synchronized DirectoryUsers getInstance() {
    if (instance == null) {
      instance = new DirectoryUsers();
    }
    return instance;
  }

  /**
   * Crea todos los usuarios del sistema con sus grupos y permisos.
   * Define los horarios, espacios y grupos (Administrator, Manager, Employee, Blank)
   * y crea los usuarios correspondientes.
   */
  public void makeUsers() {
    LOG.debug("Initializing all users");
    // Días hábiles
    List<DayOfWeek> allDays = Arrays.asList(DayOfWeek.values());
    List<DayOfWeek> weekDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    List<DayOfWeek> managerDays = new ArrayList<DayOfWeek>(weekDays);
    managerDays.add(DayOfWeek.SATURDAY);

    // Horarios
    Schedule adminSchedule = new Schedule(LocalDate.MIN, LocalDate.MAX, allDays,
        LocalTime.MIN, LocalTime.MAX);
    Schedule managerSchedule = new Schedule(LocalDate.of(2025, 9, 1),
        LocalDate.of(2026, 3, 1), managerDays, LocalTime.of(8, 0), LocalTime.of(20, 0));
    Schedule employeeSchedule = new Schedule(LocalDate.of(2025, 9, 1),
        LocalDate.of(2026, 3, 1), weekDays, LocalTime.of(8, 0), LocalTime.of(17, 0));

    // Espacios
    List<Space> allSpaces = DirectoryAreas.getInstance().getAllSpaces();
    List<Space> employeeSpaces = Arrays.asList(
        (Space) DirectoryAreas.getInstance().findAreaById("hall"),
        (Space) DirectoryAreas.getInstance().findAreaById("room1"),
        (Space) DirectoryAreas.getInstance().findAreaById("room2"),
        (Space) DirectoryAreas.getInstance().findAreaById("corridor")
    );

    // Grupos
    Group admin = new Group("Administrator",
        new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")),
        new ArrayList<>(allSpaces), adminSchedule);
    Group manager = new Group("Manager",
        new ArrayList<>(Arrays.asList("open", "close", "lock", "unlock", "unlock_shortly")),
        new ArrayList<>(allSpaces), managerSchedule);
    Group employee = new Group("Employee",
        new ArrayList<>(Arrays.asList("open", "close", "unlock_shortly")),
        new ArrayList<>(employeeSpaces), employeeSchedule);
    Group blank = new Group("Blank", new ArrayList<>(), new ArrayList<>(), null);

    // Usuarios
    users.add(new User("Bernat", "12345", new ArrayList<>(), blank));
    users.add(new User("Blai", "77532", new ArrayList<>(), blank));

    users.add(new User("Ernest", "74984", new ArrayList<>(employeeSpaces), employee));
    users.add(new User("Eulalia", "43295", new ArrayList<>(employeeSpaces), employee));

    users.add(new User("Manel", "95783", new ArrayList<>(allSpaces), manager));
    users.add(new User("Marta", "05827", new ArrayList<>(allSpaces), admin));
    users.add(new User("Ana", "11343", new ArrayList<>(allSpaces), admin));
  }

  /**
   * Busca un usuario por su credencial.
   *
   * @param credential La credencial del usuario a buscar
   * @return El usuario encontrado, o null si no existe
   */
  public User findUserByCredential(String credential) {
    LOG.debug("searching user with credentials {}", credential);
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        LOG.debug("user with credentials {} found", credential);
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    LOG.warn(ACTIVITY, "User with credential {} not found", credential);
    return null;
  }
}
