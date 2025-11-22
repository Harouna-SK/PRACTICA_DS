package baseNoStates;

import java.time.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Schedule {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final List<DayOfWeek> validDays;
  private final LocalTime startHour;
  private final LocalTime endHour;

  private static final Logger LOG = LoggerFactory.getLogger(Propped.class);

  public Schedule(LocalDate startDate, LocalDate endDate, List<DayOfWeek> validDays, LocalTime startHour, LocalTime endHour) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.validDays = validDays;
    this.startHour = startHour;
    this.endHour = endHour;
    LOG.debug("Initialized Schedule object with Startdate {} enddate {} validDays {} starthour {} endhour {}", startDate, endDate, validDays, startHour, endHour);
  }

  @Override
  public String toString() {
    return "Schedule{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            ", validDays=" + validDays +
            ", startHour=" + startHour +
            ", endHour=" + endHour +
            '}';
  }


  public boolean isWithinSchedule(LocalDateTime dateTime) {
    LocalDate date = dateTime.toLocalDate();
    LocalTime time = dateTime.toLocalTime();
    /*
    Se comprueba si la fecha es igual o despues de la fecha de inicio
    si la fecha es igual o antes de la fecha de fin, si el dia es valido y si la hora es valida
    */
    LOG.debug("Checking if {} is within schedule", dateTime);
    return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate)) && validDays.contains(dateTime.getDayOfWeek()) && !time.isBefore(startHour) && !time.isAfter(endHour);
  }
}
