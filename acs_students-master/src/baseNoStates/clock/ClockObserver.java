package baseNoStates.clock;

import java.time.LocalDateTime;

public interface ClockObserver {
    // El clock llamará a this.tick(now) cada periodo
    void tick(LocalDateTime now);
}