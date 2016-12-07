package dominikschmidtlein.trace.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class TracePoint {

    double x;
    double y;

    private Set<Connection> connections;

    public static final int DEFAULT_CAPACITY = 4;

    public TracePoint(double x, double y) {
        this(x, y, DEFAULT_CAPACITY);
    }

    public TracePoint(double x, double y, int capacity) {
        this.x = x;
        this.y = y;
        this.connections = new HashSet<>(capacity);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public Connection connectedTo(TracePoint tracePoint) {
        for (Connection connection : connections) {
            if (connection.connects(this, tracePoint)) {
                return connection;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TracePoint)) {
            return false;
        }
        TracePoint tp = (TracePoint) o;
        return getX() == tp.getX() && getY() == tp.getY();
    }

    @Override
    public int hashCode() {
        return (int)(x * 997 + y * 83);
    }
}
