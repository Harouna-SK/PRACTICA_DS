package baseNoStates.spaces;
import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

public abstract class Area {
  protected String id;
  protected String description;
  protected Partition parent;

  public Area(String id, String description, Partition parent) {
    this.id = id;
    this.description = description;
    this.parent = parent;
    if (parent != null) {
      parent.addSubArea(this);
    }
  }

  public String getId() {return id;}
  public Partition getParent() {return parent;}

  //visitor pattern
  public abstract <T> T accept(VisitorAreas<T> visitor);
}
