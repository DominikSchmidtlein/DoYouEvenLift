package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dominikschmidtlein.trace.Log;


/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class TracePoint extends Point{
    private static final String TAG = "TracePoint";
    private Set<Connection> connections;
    static final int DEFAULT_CAPACITY = 16;

    TracePoint(double x, double y) {
        this(x, y, DEFAULT_CAPACITY);
    }

    TracePoint(double x, double y, int capacity) {
        super(x, y);
        Log.v(TAG, "TracePoint created " + this);
        this.connections = new HashSet<>(capacity);
    }

    void addConnection(Connection connection) {
        Log.v(TAG, "Adding connection " + connection + " to " + this);
        if (connections.contains(connection)) Log.w(TAG, this + " already connected to " + connection);
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

    /**
     * Inclusive to the points in connection
     * @param connection
     * @return
     */
    boolean on(Connection connection) {
        double m = connection.slope();
        double b = connection.yIntercept();
        boolean onLine;
        if (m == Double.POSITIVE_INFINITY) {
            onLine = getX() == connection.getPoint().getX();
        } else {
            onLine = Math.abs(getY() - m * getX() - b) < EPSILON;
        }
        return onLine && connection.squareContains(this);
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

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}
