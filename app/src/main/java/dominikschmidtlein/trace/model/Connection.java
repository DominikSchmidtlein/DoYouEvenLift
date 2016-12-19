package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
class Connection {

    private Set<TracePoint> points = new HashSet<>();

    private Set<Connection> subConnections = new HashSet<>();
    private Set<Connection> superConnections = new HashSet<>();

    private State state = State.FREE;

    /**
     * Add this connection to both points. Check if this connection can be combined with another
     * connection of p1 or p2 to create a superconnection.
     * @param p1
     * @param p2
     */
    Connection(@NonNull TracePoint p1, @NonNull TracePoint p2) {
        this(p1, p2, true);
    }

    Connection(@NonNull TracePoint p1, @NonNull TracePoint p2, boolean smart) {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException();
        }
        points.add(p1);
        points.add(p2);
        if (smart) {
            p1.addConnection(this);
            p2.addConnection(this);
            for (Connection connection : p1.getConnections()) {
                connection.concat(this);
            }
            for (Connection connection : p2.getConnections()) {
                connection.concat(this);
            }
        }
    }

    private void addSubConnection(Connection connection) {
        subConnections.add(connection);
    }

    void addSuperConnection(Connection connection) {
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

    Set<Connection> getSubConnections() {
        return subConnections;
    }

    Set<Connection> getSuperConnections() {
        return superConnections;
    }

    boolean connects(TracePoint point1, TracePoint point2) {
        for (TracePoint point : points) {
            if (!point1.equals(point) && !point2.equals(point)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Sets current connection to occupied, subconnections to occupied and superconnections to
     * blocked.
     */
    void setOccupied() {
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

    /**
     * Check if connections have points in common. Then check if direction from common point is
     * opposite.
     * @param connection
     * @return
     */
    Connection concat(Connection connection) {
        if (connection.equals(this)) {
            return null;
        }
        TracePoint commonPoint = this.commonPoint(connection);
        if (commonPoint == null) {
            return null;
        }
        if (directionFrom(commonPoint).isOppositeOrigin(connection.directionFrom(commonPoint))) {
            return new Connection(otherEnd(commonPoint), connection.otherEnd(commonPoint));
        }
        return null;
    }

    TracePoint commonPoint(Connection connection) {
        for (TracePoint point : connection.points) {
            if (points.contains(point)) {
                return point;
            }
        }
        return null;
    }

    TracePoint otherEnd(TracePoint tracePoint) {
        TracePoint otherEnd = null;
        TracePoint thisEnd = null;

        for (TracePoint point : points) {
            if (tracePoint.equals(point)) {
                thisEnd = point;
            } else {
              otherEnd = point;
            }
        }
        return thisEnd == null ? null : otherEnd;
    }

    Point directionFrom(TracePoint tracePoint) {
        if (!points.contains(tracePoint)) {
            return null;
        }
        TracePoint otherEnd = otherEnd(tracePoint);
        Point direction = new Point(otherEnd.getX() - tracePoint.getX(), otherEnd.getY() - tracePoint.getY());
        return direction.unitMagnitude();
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
