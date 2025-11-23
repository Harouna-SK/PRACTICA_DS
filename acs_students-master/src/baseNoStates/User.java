package baseNoStates;

import baseNoStates.spaces.Area;
import baseNoStates.spaces.GetSpaceVisitor;
import baseNoStates.spaces.Space;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un usuario del sistema.
 * Un usuario tiene un nombre, una credencial, un grupo asociado
 * y una lista de espacios autorizados.
 *
 * @author Sistema ACS
 */
public class User {
  private final String name;
  private final String credential;
  private final Group group;
  private List<Space> authorizedSpaces;

  private static final Logger LOG = LoggerFactory.getLogger(User.class);

  /**
   * Constructor de User.
   *
   * @param name             Nombre del usuario
   * @param credential       Credencial única del usuario
   * @param authorizedSpaces Lista de espacios autorizados para el usuario
   * @param group            Grupo al que pertenece el usuario
   */
  public User(String name, String credential, List<Space> authorizedSpaces, Group group) {
    this.name = name;
    this.credential = credential;
    this.authorizedSpaces = authorizedSpaces;
    this.group = group;
    LOG.debug("Initialized User object with name {} credential {} authorizedSpaces {} group {}",
        name, credential, authorizedSpaces, group);
  }

  /**
   * Obtiene la credencial del usuario.
   *
   * @return La credencial del usuario
   */
  public String getCredential() {
    return credential;
  }

  /**
   * Representa el usuario como una cadena de texto.
   *
   * @return Representación en texto del usuario
   */
  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + ", group=" + group.getName() + "}";
  }

  /**
   * Obtiene el grupo al que pertenece el usuario.
   *
   * @return El grupo del usuario
   */
  public Group getGroup() {
    return group;
  }

  /**
   * Obtiene el nombre del usuario.
   *
   * @return El nombre del usuario
   */
  public String getName() {
    return name;
  }

  /**
   * Comprueba si el usuario puede estar en un área específica.
   * Verifica si el área está dentro de los espacios autorizados del usuario.
   *
   * @param s El área a verificar
   * @return true si el usuario puede estar en el área, false en caso contrario
   */
  public boolean canBeInSpace(Area s) {
    LOG.debug("Checking if user {} can be in space {}", name, s.getId());
    for (Space space : authorizedSpaces) {
      List<Space> spaces = space.accept(new GetSpaceVisitor());

      if (spaces.contains(s)) {
        LOG.debug("Can be in space {}", s.getId());
        return true;
      }
    }
    LOG.debug("Cannot be in space {}", s.getId());
    return false;
  }
}
