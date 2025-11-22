package baseNoStates.spaces;
import java.util.ArrayList;
import java.util.List;
import baseNoStates.Door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public abstract class Area {
  protected String id;
  protected String description;
  protected Partition parent;
  private static final Logger LOG = LoggerFactory.getLogger(Area.class);
  public Area(String id, String description, Partition parent) {
    this.id = id;
    this.description = description;
    this.parent = parent;
    if (parent != null) {
      parent.addSubArea(this);
    }
    LOG.debug("Initialized area with id {} description {}", id, description);
  }

  public String getId() {return id;}
  public Partition getParent() {return parent;}

  //visitor pattern
  public abstract <T> T accept(VisitorAreas<T> visitor);
}
