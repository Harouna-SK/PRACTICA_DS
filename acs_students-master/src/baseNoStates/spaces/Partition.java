package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class Partition extends Area {
=======
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Partition extends Area{
>>>>>>> 2773080 (LOGGER implemented without selecting milestone logs)
  private List<Area> subAreas = new ArrayList<>();
  private static final Logger LOG = LoggerFactory.getLogger(Partition.class);

  public Partition(String id, String description, Partition parent) {
    super(id, description, parent);
    LOG.debug("Partition initialized");
  }

  public void addSubArea(Area area) {
    subAreas.add(area);
  }
<<<<<<< HEAD

  public List<Area> getSubAreas() {
    return subAreas;
  }

  @Override
  public <T> T accept(VisitorAreas<T> visitor) {
    return visitor.visitPartition(this);
=======
  public String getId(){ return id; }
  @Override
  public Area findAreaById(String id) {
    LOG.debug("Finding area by id");
    if (this.id.equals(id)) { 
      LOG.debug("Area with id {} was found", id);
      return this;
    }
    for (Area sub : subAreas) {
      Area found = sub.findAreaById(id);
      if (found != null) { 
        LOG.debug("Area with id {} was found", id);
        return found;
      }
    }
    LOG.warn("Area with id {} was not found", id);
    return null;
  }

  @Override
  public List<Door> getDoorsGivingAccess() {
    LOG.debug("Function getDoorsGivingAccess was called");
    List<Door> doors = new ArrayList<>();
    for (Area sub : subAreas) {
      doors.addAll(sub.getDoorsGivingAccess());
    }
    return doors;
  }

  @Override
  public List<Space> getSpaces() {
    LOG.debug("Function getSpaces was called");
    List<Space> spaces = new ArrayList<>();
    for (Area sub : subAreas) {
      spaces.addAll(sub.getSpaces());
    }
    return spaces;
>>>>>>> 2773080 (LOGGER implemented without selecting milestone logs)
  }
}
