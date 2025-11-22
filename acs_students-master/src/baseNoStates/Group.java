package baseNoStates;

import baseNoStates.spaces.Space;
import baseNoStates.spaces.Area;
import java.time.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Group {
  private final String name;
  private final ArrayList<String> allowedActions;
  private final ArrayList<Space> allowedSpaces;
  private final Schedule schedule;
  private static final Logger LOG = LoggerFactory.getLogger(Group.class);

  public Group(String name, ArrayList<String> allowedActions, ArrayList<Space> allowedSpaces, Schedule schedule) {
    this.name = name;
    this.allowedActions = allowedActions;
    this.allowedSpaces = allowedSpaces;
    this.schedule = schedule;
    LOG.debug("Initialized group with name {} allowedActions {} allowedSpaces {}", name, allowedActions, allowedSpaces);
  }

  public String getName() { return name; }
  public ArrayList<String> getAllowedActions() { return allowedActions; }
  public ArrayList<Space> getAllowedSpaces() { return allowedSpaces; }
  public Schedule getSchedule() { return schedule; }

  public boolean canPerform(String action, Area area, LocalDateTime dateTime) {
    if (schedule == null) {
      LOG.debug("Schedile is null");
      return false; 
    } // caso 'Blank' schedule
    LOG.debug("Evaluating if {} is allowed at {} {}", action, area.getId(), dateTime);
    return allowedActions.contains(action) && allowedSpaces.contains(area) && schedule.isWithinSchedule(dateTime);
  }
}
