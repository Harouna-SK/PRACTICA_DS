package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un horario de acceso.
 * Define un período de tiempo con fechas de inicio y fin, días válidos
 * y horas de inicio y fin durante los cuales se permite el acceso.
 *
 * @author Sistema ACS
 */
public class Schedule {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final List<DayOfWeek> validDays;
  private final LocalTime startHour;
  private final LocalTime endHour;

  private static final Logger LOG = LoggerFactory.getLogger(Schedule.class);

  /**
   * Constructor de Schedule.
   *
   * @param startDate  Fecha de inicio del horario
   * @param endDate    Fecha de fin del horario
   * @param validDays  Lista de días de la semana válidos
   * @param startHour  Hora de inicio del horario
   * @param endHour    Hora de fin del horario
   */
  public Schedule(LocalDate startDate, LocalDate endDate, List<DayOfWeek> validDays,
      LocalTime startHour, LocalTime endHour) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.validDays = validDays;
    this.startHour = startHour;
    this.endHour = endHour;
    LOG.debug("Initialized Schedule object with Startdate {} enddate {} validDays {} "
        + "starthour {} endhour {}", startDate, endDate, validDays, startHour, endHour);
  }

  /**
   * Representa el horario como una cadena de texto.
   *
   * @return Representación en texto del horario
   */
  @Override
  public String toString() {
    return "Schedule{"
        + "startDate=" + startDate
        + ", endDate=" + endDate
        + ", validDays=" + validDays
        + ", startHour=" + startHour
        + ", endHour=" + endHour
        + '}';
  }

  /**
   * Comprueba si una fecha/hora está dentro del horario.
   * Se comprueba si la fecha es igual o después de la fecha de inicio,
   * si la fecha es igual o antes de la fecha de fin, si el día es válido
   * y si la hora es válida.
   *
   * @param dateTime La fecha/hora a comprobar
   * @return true si está dentro del horario, false en caso contrario
   */
  public boolean isWithinSchedule(LocalDateTime dateTime) {
    LocalDate date = dateTime.toLocalDate();
    LocalTime time = dateTime.toLocalTime();
    LOG.debug("Checking if {} is within schedule", dateTime);
    return (date.isEqual(startDate) || date.isAfter(startDate))
        && (date.isEqual(endDate) || date.isBefore(endDate))
        && validDays.contains(dateTime.getDayOfWeek())
        && !time.isBefore(startHour)
        && !time.isAfter(endHour);
  }
}
