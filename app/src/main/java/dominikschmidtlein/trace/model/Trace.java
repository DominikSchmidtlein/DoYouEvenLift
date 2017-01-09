package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Trace {

    private TracePointMap tracePointMap;
    private TracePoint recentTracePoint;
    private Connection recentConnection;

    Trace(TracePointMap tracePointMap) {
        this.tracePointMap = tracePointMap;
    }

    void addConnection(TracePoint p1, TracePoint p2) {
        p1 = tracePointMap.addTracePoint(p1);
        p2 = tracePointMap.addTracePoint(p2);
        if (p1.connectedTo(p2) == null) {
            recentTracePoint = p2;
            recentConnection = new Connection(p1, p2);
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
        int pointCount = 0;
        int oddConnectionCount = 0;
        TracePointIterator tracePointIterator = tracePointIterator();
        for (TracePoint tracePoint;  tracePointIterator.hasNext(); ) {
            tracePoint = tracePointIterator.next();
            pointCount++;
            if (tracePoint.getBaseConnections().size() % 2 == 1) {
                oddConnectionCount++;
            }
        }
        return pointCount == tracePointMap.getCount() && (oddConnectionCount == 0 || oddConnectionCount == 2);
    }

    TracePointIterator tracePointIterator() {
        return new TracePointIterator(recentTracePoint);
    }

    BaseConnectionIterator baseConnectionIterator() {
        while (!recentConnection.getSubConnections().isEmpty()) {
            recentConnection = recentConnection.getSubConnections().iterator().next();
        }
        return new BaseConnectionIterator(recentConnection);
    }

}
