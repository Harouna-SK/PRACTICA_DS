package baseNoStates.spaces;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

        public class Partition extends Area {
            private List<Area> subAreas = new ArrayList<>();
            private static final Logger LOG = LoggerFactory.getLogger(Partition.class);

            public Partition(String id, String description, Partition parent) {
                super(id, description, parent);
                LOG.debug("Partition initialized");
            }

            public void addSubArea(Area area) {
                subAreas.add(area);
            }

            public List<Area> getSubAreas() {

                return subAreas;
            }

            @Override
            public <T> T accept(VisitorAreas<T> visitor) {
                return visitor.visitPartition(this);
            }
        }