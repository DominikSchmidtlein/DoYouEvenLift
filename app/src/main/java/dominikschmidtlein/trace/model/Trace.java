package dominikschmidtlein.trace.model;

import java.util.List;
import java.util.Map;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Trace {

    private Map<TracePoint, TracePoint> points;
    private List<Connection> baseConnections;

    public Trace() {

    }

    public void addPoint(TracePoint tracePoint) {

    }

    public void addConnection(TracePoint p1, TracePoint p2) {

    }

    public TracePoint nearPoint(TracePoint tracePoint) {
        return null;
    }

    public boolean isComplete() {
        return false;
    }

    /**
     * Sets all connections to unoccupied.
     */
    public void reset() {

    }

}
