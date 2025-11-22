package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Space extends Area {
  private List<Door> doors = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Space.class);

  public Space(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Called Area class to initialize Space object with id {} description {} partition {}", id, description, parent.getId());
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
