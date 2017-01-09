package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class TracePoint extends Point{

    private Set<Connection> connections;

    static final int DEFAULT_CAPACITY = 16;

    TracePoint(double x, double y) {
        this(x, y, DEFAULT_CAPACITY);
    }

    TracePoint(double x, double y, int capacity) {
        super(x, y);
        this.connections = new HashSet<>(capacity);
    }

    void addConnection(Connection connection) {
        connections.add(connection);
        for (Connection existingConnection : getConnections()) {
            existingConnection.concat(connection);
        }
    }

    Connection connectedTo(TracePoint tracePoint) {
        for (Connection connection : connections) {
            if (connection.connects(this, tracePoint)) {
                return connection;
            }
        }
        return null;
    }

    Set<Connection> getConnections() {
        return connections;
    }

    Set<Connection> getBaseConnections() {
        Set<Connection> baseConnections = new HashSet<>();
        for (Connection connection : getConnections()) {
            if (connection.getSubConnections().isEmpty()) {
                baseConnections.add(connection);
            }
        }
        return baseConnections;
    }
}
