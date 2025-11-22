package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.spaces.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.*;
import java.util.*;

public final class DirectoryUsers {
    // 1. La única instancia se guarda aquí
    private static DirectoryUsers instance;

    // 2. La lista ya NO es static (ahora pertenece al objeto)
    private final ArrayList<User> users = new ArrayList<>();

    // 3. Constructor privado para evitar 'new' desde fuera
    private DirectoryUsers() {
    }

    // 4. Método público para obtener la instancia
    public static synchronized DirectoryUsers getInstance() {
        if (instance == null) {
            instance = new DirectoryUsers();
        }
        return instance;
    }

    // 5. Método de lógica (se quita el 'static')
    public void makeUsers() {
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

        // espacios - NOTA: Aquí asumo que DirectoryAreas TAMBIÉN será Singleton.
        // Si aún no lo has cambiado, usa DirectoryAreas.getAllSpaces() sin getInstance().
        List<Space> allSpaces = DirectoryAreas.getInstance().getAllSpaces();

        List<Space> employeeSpaces = Arrays.asList(
                (Space) DirectoryAreas.getInstance().findAreaById("hall"),
                (Space) DirectoryAreas.getInstance().findAreaById("room1"),
                (Space) DirectoryAreas.getInstance().findAreaById("room2"),
                (Space) DirectoryAreas.getInstance().findAreaById("corridor")
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

        users.add(new User("Manel", "95783", new ArrayList<>(allSpaces), manager));
        users.add(new User("Marta", "05827", new ArrayList<>(allSpaces), admin));
        users.add(new User("Ana", "11343", new ArrayList<>(allSpaces), admin));
    }

    // 6. Método de lógica (se quita el 'static')
    public User findUserByCredential(String credential) {
        for (User user : users) {
            if (user.getCredential().equals(credential)) {
                return user;
            }
        }
        System.out.println("user with credential " + credential + " not found");
        return null; // otherwise we get a Java error
    }

}