package dominikschmidtlein.trace.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Trace implements Iterable<TracePoint> {

    private TracePointMap tracePointMap;

    Trace(TracePointMap tracePointMap) {
        this.tracePointMap = tracePointMap;
    }

    void addConnection(TracePoint p1, TracePoint p2) {
        p1 = tracePointMap.addTracePoint(p1);
        p2 = tracePointMap.addTracePoint(p2);
        if (p1.connectedTo(p2) == null) {
            new Connection(p1, p2);
        }
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

    TracePoint getPoint() {
        return tracePointMap.getPoint();
    }

    @Override
    public Iterator<TracePoint> iterator() {
        return new TraceIterator(this);
    }
}
