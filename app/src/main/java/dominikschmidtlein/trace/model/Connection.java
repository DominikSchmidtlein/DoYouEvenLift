package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dominik Schmidtlein on 2016-11-23.
 *
 * Connection is a orderless set of 2 unequal points that are connected together.
 */
class Connection {

    private Set<TracePoint> points = new HashSet<>();

    private Set<Connection> subConnections = new HashSet<>();
    private Set<Connection> superConnections = new HashSet<>();

    private State state = State.FREE;

    /**
     * Creates a connection between 2 unique points. Checks if this connection can be concatenated with
     * other connections. If so, creates a new connection and sets super/subconnections accordingly.
     *
     * Intended use is to create base connections and all other connections will be automatically
     * generated.
     * @param p1
     * @param p2
     */
    Connection(@NonNull TracePoint p1, @NonNull TracePoint p2) {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException();
        }
        addPoint(p1);
        addPoint(p2);

        p1.addConnection(this);
        p2.addConnection(this);
    }

    private boolean addPoint(TracePoint point) {
        return points.add(point);
    }

    /**
     * Superconnection is a connection that:
     *  - shares 1 point with this point
     *  - has the same direction from the common point
     *  - is larger than this connection (cannot be superconnection of self)
     * @param superConnection the potential superconnection
     * @return true if this is a superconnection else false
     */
    boolean addSuperConnection(Connection superConnection) {
        TracePoint commonPoint = commonPoint(superConnection);
        if (commonPoint != null) {
            if (directionFrom(commonPoint).equals(superConnection.directionFrom(commonPoint))) {
                if (commonPoint.distanceTo(otherEnd(commonPoint)) <
                        commonPoint.distanceTo(superConnection.otherEnd(commonPoint))) {
                    superConnections.add(superConnection);
                    superConnection.subConnections.add(this);
                    return true;
                }
            }
        }
        return false;
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

    Set<TracePoint> getPoints() {
        return points;
    }

    /**
     * Checks if this is a connection between these 2 unique points.
     * @param point1
     * @param point2
     * @return
     */
    boolean connects(TracePoint point1, TracePoint point2) {
        return points.contains(point1) && points.contains(point2) && !point1.equals(point2);
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
        TracePoint commonPoint = this.commonPoint(connection);
        if (commonPoint != null) {
            if (directionFrom(commonPoint).isOppositeOrigin(connection.directionFrom(commonPoint))) {
                Connection concatConnection = otherEnd(commonPoint).connectedTo(connection.otherEnd(commonPoint));
                if (concatConnection == null) {
                    concatConnection = new Connection(otherEnd(commonPoint), connection.otherEnd(commonPoint));
                }
                this.addSuperConnection(concatConnection);
                connection.addSuperConnection(concatConnection);
                return concatConnection;
            }
        }
        return null;
    }


    /**
     * Returns a point iff the 2 connections have this 1 point in common.
     * @param connection
     * @return
     */
    TracePoint commonPoint(Connection connection) {
        if (!connection.equals(this)) {
            for (TracePoint point : connection.points) {
                if (points.contains(point)) {
                    return point;
                }
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

    /**
     * Returns a point whose x and y coordinates represent a direction with a magnitude 1.
     * @param tracePoint the starting point for the direction calculation
     * @return
     */
    Point directionFrom(TracePoint tracePoint) {
        if (!points.contains(tracePoint)) {
            return null;
        }
        TracePoint otherEnd = otherEnd(tracePoint);
        Point direction = new Point(otherEnd.getX() - tracePoint.getX(), otherEnd.getY() - tracePoint.getY());
        return direction.unitMagnitude();
    }

    /**
     * Connections are equal if they connect the same points together.
     * @param o
     * @return True if equal, False otherwise
     */
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
