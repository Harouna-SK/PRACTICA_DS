package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

public class Space extends Area {
  private List<Door> doors = new ArrayList<>();

  public Space(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addDoor(Door door) {
    doors.add(door);
  }

  // Este getter lo usa el visitor DoorsVisitor
  public List<Door> getDoors() {
    return new ArrayList<>(doors);
  }

  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitSpace(this);
  }
}
