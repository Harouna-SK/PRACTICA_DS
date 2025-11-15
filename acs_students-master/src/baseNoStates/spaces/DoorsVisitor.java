package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

public class DoorsVisitor implements VisitorAreas<List<Door>> {

  @Override
  public List<Door> visitSpace(Space s) {
    return s.getDoorsGivingAccess();
  }

  @Override
  public List<Door> visitPartition(Partition p) {
    List<Door> all = new ArrayList<>();
    for (Area sub : p.getSubAreas()) {
      all.addAll(sub.accept(this));
    }
    return all;
  }
}
