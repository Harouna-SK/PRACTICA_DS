package baseNoStates;

import baseNoStates.spaces.Area;
import baseNoStates.spaces.GetSpaceVisitor;
import baseNoStates.spaces.Space;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class User {
  private final String name;
  private final String credential;
  private final Group group;
  private List<Space> authorizedSpaces;

  private static final Logger LOG = LoggerFactory.getLogger(User.class);

  public User(String name, String credential, List<Space> authorizedSpaces, Group group) {
    this.name = name;
    this.credential = credential;
    this.authorizedSpaces = authorizedSpaces;
    this.group = group;
    LOG.debug("Initialized User object with name {} credential {} authorizedSpaces {} group {}", name, credential, authorizedSpaces, group);
  }

  public String getCredential() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + ", group=" + group.getName() + "}";
  }

  public Group getGroup(){
    return group;
  }

  public String getName(){
    return name;
  }
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
