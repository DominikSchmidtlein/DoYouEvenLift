package dominikschmidtlein.trace.model;

import dominikschmidtlein.trace.Log;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Trace {

    private static final String TAG = "Trace";
    private TracePointMap tracePointMap;

    Trace(TracePointMap tracePointMap) {
        this.tracePointMap = tracePointMap;
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
            Log.d(TAG, tracePoint + " has " + tracePoint.getBaseConnections().size() + " connections");
        }
        boolean legality = true;
        if (pointCount != tracePointMap.getCount()) {
            legality = false;
            Log.i(TAG, "Illegal trace: Not all points are connected. Traversal: " + pointCount + ", Map: " + tracePointMap.getCount());
        }
        if (!(oddConnectionCount == 0 || oddConnectionCount == 2)) {
            legality = false;
            Log.i(TAG, "Illegal trace: " + oddConnectionCount + " points with odd number of connections");
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
