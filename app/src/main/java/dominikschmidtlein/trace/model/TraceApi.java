package dominikschmidtlein.trace.model;

import java.util.Set;

import dominikschmidtlein.trace.BuildConfig;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
class TraceApi {

    TraceApi() {

    }

    void addConnection(Connection connection) {
        assertConnection(connection);
        for (TracePoint tracePoint : connection.getPoints()) {
            tracePoint.addConnection(connection);
        }
        checkSuperConnections(connection);
        checkIntersectionConnection(connection);
        checkIntersectionPoint(connection);
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

    }

    void checkIntersectionPoint(Connection connection) {

    }

    void assertConnection(Connection connection) {
        if (BuildConfig.DEBUG) {
            TracePoint tracePoint = connection.getPoint();
            TracePoint otherPoint = connection.otherEnd(tracePoint);
            if (null != tracePoint.connectedTo(otherPoint)) {
                throw new IllegalArgumentException();
            }
        }
    }

    Connection concatConnections(Connection connection1, Connection connection2) {
        return connection1.concat(connection2);
    }

    void removeConnection(Connection connection) {

    }
}
