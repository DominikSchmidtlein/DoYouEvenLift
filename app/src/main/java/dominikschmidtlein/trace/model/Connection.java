package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Connection {

    private Set<TracePoint> points;

    private Set<Connection> subConnections = new HashSet<>();
    private Set<Connection> superConnections = new HashSet<>();

    private State state = State.FREE;

    public Connection(@NonNull TracePoint p1, @NonNull TracePoint p2) {
        if (p1.equals(p2)) {
            throw new InvalidParameterException();
        }
        points = new HashSet<>();
        points.add(p1);
        points.add(p2);
        p1.addConnection(this);
        p2.addConnection(this);
    }

    private void addSubConnection(Connection connection) {
        subConnections.add(connection);
    }

    public void addSuperConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        superConnections.add(connection);
        connection.addSubConnection(this);
    }

    boolean isFree() {
        return state == State.FREE;
    }

    boolean isBlocked() {
        return state == State.BLOCKED;
    }

    boolean isOccupied() {
        return state == State.OCCUPIED;
    }

    private void setState(State state) {
        this.state = state;
    }

    private State getState() {
        return state;
    }

    public Set<Connection> getSubConnections() {
        return subConnections;
    }

    public Set<Connection> getSuperConnections() {
        return superConnections;
    }

    public boolean connects(TracePoint point1, TracePoint point2) {
        for (TracePoint point : points) {
            if (!point.equals(point1) && !point.equals(point2)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Sets current connection to occupied, subconnections to occupied and superconnections to
     * blocked.
     */
    public void setOccupied() {
        State prevState = getState();

        if (prevState == State.BLOCKED || prevState == State.FREE) {
            for (Connection connection : subConnections) {
                connection.setOccupied();
            }
            this.setState(State.OCCUPIED);
        }

        if (prevState == State.FREE) {
            for (Connection connection : superConnections) {
                connection.setBlocked();
            }
        }
    }

    private void setBlocked() {
        if (getState() == State.FREE) {
            this.setState(State.BLOCKED);
            for (Connection connection: superConnections) {
                connection.setBlocked();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Connection)) {
            return false;
        }
        Connection c = (Connection) o;
        return points.equals(c.points);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (TracePoint point : points) {
            hash += point.hashCode();
        }
        return hash * 733;
    }

    private enum State {
        OCCUPIED,
        BLOCKED,
        FREE
    }

}
