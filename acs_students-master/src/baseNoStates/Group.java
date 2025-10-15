// --- Group.java ---
package baseNoStates;

import baseNoStates.spaces.Space;
import java.time.*;
import java.util.*;

public class Group {
  private final String name;
  private final ArrayList<String> allowedActions;
  private final ArrayList<Space> allowedSpaces;
  private final Schedule schedule;

  public Group(String name, ArrayList<String> allowedActions, ArrayList<Space> allowedSpaces, Schedule schedule) {
    this.name = name;
    this.allowedActions = allowedActions;
    this.allowedSpaces = allowedSpaces;
    this.schedule = schedule;
  }

  public String getName() { return name; }
  public ArrayList<String> getAllowedActions() { return allowedActions; }
  public ArrayList<Space> getAllowedSpaces() { return allowedSpaces; }
  public Schedule getSchedule() { return schedule; }

  public boolean canPerform(String action, Space space, LocalDateTime dateTime) {
    return allowedActions.contains(action)
        && allowedSpaces.contains(space)
        && schedule.isWithinSchedule(dateTime);
  }
}
