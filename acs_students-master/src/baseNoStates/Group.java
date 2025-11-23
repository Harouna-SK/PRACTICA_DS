package baseNoStates;

import baseNoStates.spaces.Space;
import baseNoStates.spaces.Area;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un grupo de usuarios con permisos específicos.
 * Define qué acciones pueden realizar, en qué espacios y en qué horario.
 *
 * @author Sistema ACS
 */
public class Group {
  private final String name;
  private final ArrayList<String> allowedActions;
  private final ArrayList<Space> allowedSpaces;
  private final Schedule schedule;
  private static final Logger LOG = LoggerFactory.getLogger(Group.class);

  /**
   * Constructor de Group.
   *
   * @param name           Nombre del grupo
   * @param allowedActions Lista de acciones permitidas
   * @param allowedSpaces Lista de espacios permitidos
   * @param schedule      Horario de acceso (null para grupos sin permisos)
   */
  public Group(String name, ArrayList<String> allowedActions,
      ArrayList<Space> allowedSpaces, Schedule schedule) {
    this.name = name;
    this.allowedActions = allowedActions;
    this.allowedSpaces = allowedSpaces;
    this.schedule = schedule;
    LOG.debug("Initialized group with name {} allowedActions {} allowedSpaces {}",
        name, allowedActions, allowedSpaces);
  }

  /**
   * Obtiene el nombre del grupo.
   *
   * @return El nombre del grupo
   */
  public String getName() {
    return name;
  }

  /**
   * Obtiene la lista de acciones permitidas.
   *
   * @return La lista de acciones permitidas
   */
  public ArrayList<String> getAllowedActions() {
    return allowedActions;
  }

  /**
   * Obtiene la lista de espacios permitidos.
   *
   * @return La lista de espacios permitidos
   */
  public ArrayList<Space> getAllowedSpaces() {
    return allowedSpaces;
  }

  /**
   * Obtiene el horario de acceso del grupo.
   *
   * @return El horario de acceso, o null si no tiene horario
   */
  public Schedule getSchedule() {
    return schedule;
  }

  /**
   * Comprueba si el grupo puede realizar una acción en un área en un momento dado.
   * Verifica que la acción esté permitida, el área esté permitida y
   * la fecha/hora esté dentro del horario.
   *
   * @param action   La acción a verificar
   * @param area     El área donde se realizará la acción
   * @param dateTime La fecha/hora de la acción
   * @return true si puede realizar la acción, false en caso contrario
   */
  public boolean canPerform(String action, Area area, LocalDateTime dateTime) {
    if (schedule == null) {
      LOG.debug("Schedule is null");
      return false;
    } // caso 'Blank' schedule
    LOG.debug("Evaluating if {} is allowed at {} {}", action, area.getId(), dateTime);
    return allowedActions.contains(action)
        && allowedSpaces.contains(area)
        && schedule.isWithinSchedule(dateTime);
  }
}
