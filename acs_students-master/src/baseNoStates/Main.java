package baseNoStates;

import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.clock.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal que inicia el sistema.
 * Inicializa el reloj, crea las áreas y usuarios, y arranca el servidor web.
 *
 * @author Sistema ACS
 */
public class Main {
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  /**
   * Método principal que inicia la aplicación.
   *
   * @param args Argumentos de la línea de comandos (no utilizados)
   */
  public static void main(String[] args) {
    Clock.getInstance().start();
    DirectoryAreas.getInstance().makeAreas();
    DirectoryUsers.getInstance().makeUsers();
    LOG.debug("Starting webserver");
    WebServer.getInstance();
  }
}
