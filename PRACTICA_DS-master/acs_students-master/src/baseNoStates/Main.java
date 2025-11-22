package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.clock.Clock;

public class Main {
    public static void main(String[] args) {
        Clock.getInstance().start();

        // Llamadas actualizadas a Singleton
        DirectoryAreas.getInstance().makeAreas();
        DirectoryUsers.getInstance().makeUsers();

        // Iniciar el servidor
        WebServer.getInstance();
    }
}
