package baseNoStates.spaces;
import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

public class Partition extends Area{
  private List<Area> subAreas = new ArrayList<>();

  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addSubArea(Area area) {
    subAreas.add(area);
  }

  @Override
  public Area findAreaById(String id) {
    if (this.id.equals(id)) return this;
    for (Area sub : subAreas) {
      Area found = sub.findAreaById(id);
      if (found != null) return found;
    }
    return null;
  }

  @Override
  public List<Door> getDoorsGivingAccess() {
    List<Door> doors = new ArrayList<>();
    for (Area sub : subAreas) {
      doors.addAll(sub.getDoorsGivingAccess());
    }
    return doors;
  }

  @Override
  public List<Space> getSpaces() {
    List<Space> spaces = new ArrayList<>();
    for (Area sub : subAreas) {
      spaces.addAll(sub.getSpaces());
    }
    return spaces;
  }
}
