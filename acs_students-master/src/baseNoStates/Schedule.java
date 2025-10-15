// --- Schedule.java ---
package baseNoStates;

import java.time.*;
import java.util.*;

public class Schedule {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final ArrayList<DayOfWeek> validDays;
  private final LocalTime startHour;
  private final LocalTime endHour;

  public Schedule(LocalDate startDate, LocalDate endDate, ArrayList<DayOfWeek> validDays,
                  LocalTime startHour, LocalTime endHour) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.validDays = validDays;
    this.startHour = startHour;
    this.endHour = endHour;
  }

  public boolean isWithinSchedule(LocalDateTime dateTime) {
    LocalDate date = dateTime.toLocalDate();
    LocalTime time = dateTime.toLocalTime();

    return (date.isEqual(startDate) || date.isAfter(startDate)) &&
           (date.isEqual(endDate) || date.isBefore(endDate)) &&
           validDays.contains(dateTime.getDayOfWeek()) &&
           !time.isBefore(startHour) && !time.isAfter(endHour);
  }
}
