package dominikschmidtlein.trace.model;

import java.util.Set;

import dominikschmidtlein.trace.Log;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
class TraceCreator {

    private static final String TAG = "TraceCreator";

    private TracePointMap tracePointMap;
    private Trace trace;

    TraceCreator(int width, int height, double margin) {
        tracePointMap = new TracePointMap(width, height, margin);
        trace = new Trace(tracePointMap);
    }

    Trace getTrace() {
        return trace;
    }

    Connection createConnection(Point p1, Point p2) {
        TracePoint tp1 = tracePointMap.addTracePoint(p1);
        TracePoint tp2 = tracePointMap.addTracePoint(p2);
        Connection existingConnection = tp1.connectedTo(tp2);
        if (null != existingConnection) {
            Log.i(TAG, tp1 + " and " + tp2 + " are already connected");
            return existingConnection;
        }
        Connection connection = new Connection(tp1, tp2);
        connection.addSelfToPoints();
        // superconnections
        Set<Connection> extensionConnections = tp1.getConnections();
        extensionConnections.addAll(tp2.getConnections());
        for (Connection extensionConnection : extensionConnections) {
            Connection concatConnection = extensionConnection.concat(connection);
            if (null != concatConnection) {
                concatConnection = createConnection(concatConnection);
                connection.addSuperConnection(concatConnection);
                extensionConnection.addSuperConnection(concatConnection);
            }
        }
        return connection;
    }

    private Connection createConnection(Connection connection) {
        TracePoint tp1 = connection.getPoint();
        TracePoint tp2 = connection.otherEnd(tp1);
        Point p1 = new Point(tp1.getX(), tp1.getY());
        Point p2 = new Point(tp2.getX(), tp2.getY());
        return createConnection(p1, p2);
    }

//    private void checkSuperConnections(Connection connection) {
//        TracePoint point1 = connection.getPoint();
//        Set<Connection> extensionConnections = point1.getConnections();
//        extensionConnections.addAll(connection.otherEnd(point1).getConnections());
//        for (Connection extensionConnection : extensionConnections) {
//            Connection concatConnection = concatConnections(extensionConnection, connection);
//            if (null != concatConnection) {
//                Connection existingConnection = concatConnection.existingConnection();
//                if (null == existingConnection) {
//                    createConnection(concatConnection);
//                } else {
//                    concatConnection = existingConnection;
//                }
//                connection.addSuperConnection(concatConnection);
//                extensionConnection.addSuperConnection(concatConnection);
//            }
//        }
//    }
//
//    private void checkIntersectionConnection(Connection connection) {
//        ConnectionIterator connectionIterator = new ConnectionIterator(connection, true);
//        for (Connection intersectConnection; connectionIterator.hasNext(); ) {
//            intersectConnection = connectionIterator.next();
//            TracePoint intersectionPoint = connection.intersects(intersectConnection);
//            if (null != intersectionPoint) {
//                TracePoint existingPoint = tracePointMap.existingPoint(intersectionPoint);
//                if (null != existingPoint) {
//                    intersectionPoint = existingPoint;
//                } else {
//                    tracePointMap.addTracePoint(intersectionPoint);
//                }
//                if (null == intersectConnection.connectsTo(intersectionPoint)) {
//                    TracePoint p1 = intersectConnection.getPoint();
//                    TracePoint p2 = intersectConnection.otherEnd(p1);
//                    Connection c1 = new Connection(p1, intersectionPoint);
//                    Connection c2 = new Connection(p2, intersectionPoint);
//                    if (null == c1.existingConnection()) {
//                        createConnection(new Connection(p1, intersectionPoint));
//                    }
//                    if (null == c2.existingConnection()) {
//                        createConnection(new Connection(p2, intersectionPoint));
//                    }
//                }
//                if (null == connection.connectsTo(intersectionPoint)) {
//                    TracePoint p1 = connection.getPoint();
//                    TracePoint p2 = connection.otherEnd(p1);
//                    Connection c1 = new Connection(p1, intersectionPoint);
//                    Connection c2 = new Connection(p2, intersectionPoint);
//                    if (null == c1.existingConnection()) {
//                        createConnection(new Connection(p1, intersectionPoint));
//                    }
//                    if (null == c2.existingConnection()) {
//                        createConnection(new Connection(p2, intersectionPoint));
//                    }
//                }
//            }
//        }
//    }

    boolean removeConnection(Point p1, Point p2) {
        return false;
    }
}
