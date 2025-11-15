package baseNoStates.spaces;

public class FindAreaVisitor implements VisitorAreas<Area> {

  private String targetId;

  public FindAreaVisitor(String id) {
    this.targetId = id;
  }

  @Override
  public Area visitSpace(Space s) {
    return s.getId().equals(targetId) ? s : null;
  }

  @Override
  public Area visitPartition(Partition p) {
    if (p.getId().equals(targetId)) return p;

    for (Area sub : p.getSubAreas()) {
      Area found = sub.accept(this);
      if (found != null) return found;
    }
    return null;
  }
}
