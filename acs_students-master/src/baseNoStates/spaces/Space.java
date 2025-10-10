package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.Arrays;
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

  @Override
  public Area findAreaById(String id) {
    return this.id.equals(id) ? this : null;
  }

  @Override
  public List<Door> getDoorsGivingAccess() {
    return new ArrayList<>(doors);
  }

  @Override
  public List<Space> getSpaces() {
    return new ArrayList<>(Arrays.asList(this));
  }
}

