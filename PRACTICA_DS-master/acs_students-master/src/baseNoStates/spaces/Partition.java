package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

public class Partition extends Area {
  private List<Area> subAreas = new ArrayList<>();

  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
  }

  public void addSubArea(Area area) {
    subAreas.add(area);
  }

  public List<Area> getSubAreas() {
    return subAreas;
  }

  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitPartition(this);
  }
}
