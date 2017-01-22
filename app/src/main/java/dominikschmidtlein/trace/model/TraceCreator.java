package dominikschmidtlein.trace.model;

import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
class TraceCreator {

    TraceCreator() {

    }

    void addConnection(Connection connection) {
        for (TracePoint tracePoint : connection.getPoints()) {
            tracePoint.addConnection(connection);
        }
        checkSuperConnections(connection);
        checkIntersectionConnection(connection);
    }

    void checkSuperConnections(Connection connection) {
        TracePoint point1 = connection.getPoint();
        Set<Connection> extensionConnections = point1.getConnections();
        extensionConnections.addAll(connection.otherEnd(point1).getConnections());
        for (Connection extensionConnection : extensionConnections) {
            Connection concatConnection = concatConnections(extensionConnection, connection);
            if (null != concatConnection) {
                Connection existingConnection = concatConnection.existingConnection();
                if (null == existingConnection) {
                    addConnection(concatConnection);
                } else {
                    concatConnection = existingConnection;
                }
                connection.addSuperConnection(concatConnection);
                extensionConnection.addSuperConnection(concatConnection);
            }
        }
    }

    void checkIntersectionConnection(Connection connection) {
        ConnectionIterator connectionIterator = new ConnectionIterator(connection, true);
        for (Connection intersectConnection; connectionIterator.hasNext(); ) {
            intersectConnection = connectionIterator.next();
            TracePoint intersectionPoint = connection.intersects(intersectConnection);

            if (null != intersectionPoint) {
                if (!intersectConnection.connectsTo(intersectionPoint)) {
                    TracePoint p1 = intersectConnection.getPoint();
                    TracePoint p2 = intersectConnection.otherEnd(p1);
                    Connection c1 = new Connection(p1, intersectionPoint);
                    Connection c2 = new Connection(p2, intersectionPoint);
                    if (null == c1.existingConnection()) {
                        addConnection(new Connection(p1, intersectionPoint));
                    }
                    if (null == c2.existingConnection()) {
                        addConnection(new Connection(p2, intersectionPoint));
                    }
                }
                if (!connection.connectsTo(intersectionPoint)) {
                    TracePoint p1 = connection.getPoint();
                    TracePoint p2 = connection.otherEnd(p1);
                    Connection c1 = new Connection(p1, intersectionPoint);
                    Connection c2 = new Connection(p2, intersectionPoint);
                    if (null == c1.existingConnection()) {
                        addConnection(new Connection(p1, intersectionPoint));
                    }
                    if (null == c2.existingConnection()) {
                        addConnection(new Connection(p2, intersectionPoint));
                    }
                }
            }
        }
    }

    Connection concatConnections(Connection connection1, Connection connection2) {
        return connection1.concat(connection2);
    }

    void removeConnection(Connection connection) {

    }
}
