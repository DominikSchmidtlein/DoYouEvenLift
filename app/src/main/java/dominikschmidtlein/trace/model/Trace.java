package dominikschmidtlein.trace.model;

import java.util.List;
import java.util.Map;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Trace {

    private TracePointMap tracePointMap;
    private List<Connection> baseConnections;

    Trace(TracePointMap tracePointMap) {
        this.tracePointMap = tracePointMap;
    }

    void addConnection(TracePoint p1, TracePoint p2) {
        tracePointMap.addTracePoint(p1);
        tracePointMap.addTracePoint(p2);
    }

    TracePoint nearPoint(TracePoint tracePoint) {
        return tracePointMap.nearPoint(tracePoint);
    }

    boolean isComplete() {
        return false;
    }

    /**
     * Sets all connections to unoccupied.
     */
    void reset() {

    }

    boolean isLegal() {
        return false;
    }

}
