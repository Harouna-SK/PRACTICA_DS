package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

public class GetSpaceVisitor implements VisitorAreas<List<Space>> {

  @Override
  public List<Space> visitSpace(Space s) {
    List<Space> list = new ArrayList<>();
    list.add(s);
    return list;
  }

  @Override
  public List<Space> visitPartition(Partition p) {
    List<Space> spaces = new ArrayList<>();
    for (Area sub : p.getSubAreas()) {
      spaces.addAll(sub.accept(this));
    }
    return spaces;
  }
}
