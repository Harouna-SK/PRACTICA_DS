package baseNoStates;

import baseNoStates.spaces.Area;
import baseNoStates.spaces.Space;
import java.util.List;

public class User {
  private final String name;
  private final String credential;
  private List<Space> authorizedSpaces;

  public User(String name, String credential, List<Space> authorizedSpaces) {
    this.name = name;
    this.credential = credential;
    this.authorizedSpaces = authorizedSpaces;
  }

  public String getCredential() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }

  public boolean canBeInSpace(Area s) {
    for (Space space : authorizedSpaces) {
      List<Space> spaces = space.getSpaces();

      if (spaces.contains(s)) {
        return true;
      }
    }
    return false;
  }
}
