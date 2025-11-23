package baseNoStates.clock;

import java.time.LocalDateTime;

/**
 * Interfaz que define un observador del reloj del sistema.
 * Los objetos que implementen esta interfaz recibirán notificaciones
 * periódicas del reloj cada vez que se actualice la fecha/hora.
 *
 * @author Sistema ACS
 */
public interface ClockObserver {
  /**
   * Método llamado por el reloj cada periodo para notificar
   * la actualización de la fecha/hora.
   *
   * @param now La fecha/hora actual del sistema
   */
  void tick(LocalDateTime now);
}