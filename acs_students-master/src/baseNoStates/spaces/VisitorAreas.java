package baseNoStates.spaces;

import baseNoStates.spaces.Partition;
import baseNoStates.spaces.Space;

public interface VisitorAreas<T> {
  T visitPartition(Partition p);
  T visitSpace(Space s);
}
