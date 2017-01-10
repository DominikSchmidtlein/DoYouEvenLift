package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Trace {

    private TracePointMap tracePointMap;
    private TraceApi traceApi = new TraceApi();

    Trace(TracePointMap tracePointMap) {
        this.tracePointMap = tracePointMap;
    }

    void addConnection(TracePoint p1, TracePoint p2) {
        tracePointMap.addTracePoint(p1);
        tracePointMap.addTracePoint(p2);
        traceApi.addConnection(new Connection(p1, p2));
    }

    TracePoint nearPoint(TracePoint tracePoint) {
        return tracePointMap.nearPoint(tracePoint);
    }

    boolean isComplete() {
        ConnectionIterator connectionIterator = baseConnectionIterator();
        for (Connection connection; connectionIterator.hasNext(); ) {
            connection = connectionIterator.next();
            if (!connection.isOccupied()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets all connections to unoccupied.
     */
    void reset() {
        ConnectionIterator connectionIterator = connectionIterator();
        for (Connection connection; connectionIterator.hasNext(); ) {
            connection = connectionIterator.next();
            connection.setFree();
        }
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
        return new TracePointIterator(tracePointMap.getTracePoint());
    }

    ConnectionIterator baseConnectionIterator() {
        Connection connection = tracePointMap.getTracePoint().getConnection().getBaseConnection();
        return new ConnectionIterator(connection, true);
    }

    ConnectionIterator connectionIterator() {
        return new ConnectionIterator(tracePointMap.getTracePoint().getConnection(), false);
    }

}
