package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dominikschmidtlein.trace.BuildConfig;

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
        if (BuildConfig.DEBUG) if (connections.contains(connection)) throw new IllegalArgumentException();
        connections.add(connection);
    }

    Connection connectedTo(TracePoint tracePoint) {
        for (Connection connection : connections) {
            if (connection.connects(this, tracePoint)) {
                return connection;
            }
        }
        return null;
    }

    boolean on(Connection connection) {
        if (connection.squareContains(this)) {
//            return getY() - connection.slo
        }
        return false;
    }

    Set<Connection> getConnections() {
        return new HashSet<>(connections);
    }

    Connection getConnection() {
        return connections.iterator().next();
    }

    Set<Connection> getBaseConnections() {
        Set<Connection> baseConnections = getConnections();
        Iterator<Connection> connectionIterator = baseConnections.iterator();
        for (Connection connection; connectionIterator.hasNext(); ) {
            connection = connectionIterator.next();
            if (!connection.isBaseConnection()) {
                connectionIterator.remove();
            }
        }
        return baseConnections;
    }
}
