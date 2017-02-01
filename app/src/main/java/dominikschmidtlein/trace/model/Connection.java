package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import dominikschmidtlein.trace.Log;

/**
 * Created by Dominik Schmidtlein on 2016-11-23.
 *
 * Connection is a orderless set of 2 unequal points that are connected together.
 */
class Connection {
    private static final String TAG = "Connection";
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
        addPoint(p1);
        addPoint(p2);
        Log.v(TAG, "Created connection " + this);
    }

    private boolean addPoint(TracePoint point) {
        if (points.contains(point)) Log.wtf(TAG, "Connection already connects to " + point);
        if (points.size() >= 2) Log.wtf(TAG, "Connection " + this + " already connects 2 points");
        if (point == null) Log.wtf(TAG, "Point is null");
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
    void addSuperConnection(Connection superConnection) {
        Log.v(TAG, "Adding superconnection " + superConnection + " to " + this);
        if (!isSuperConnection(superConnection)) Log.wtf(TAG, "Superconnection is not superconnection");
        superConnections.add(superConnection);
        superConnection.subConnections.add(this);
    }

    boolean isSuperConnection(Connection superConnection) {
        TracePoint commonPoint = commonPoint(superConnection);
        if (commonPoint != null) {
            if (directionFrom(commonPoint).equals(superConnection.directionFrom(commonPoint))) {
                if (commonPoint.distanceTo(otherEnd(commonPoint)) <
                        commonPoint.distanceTo(superConnection.otherEnd(commonPoint))) {
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

    Set<Connection> getSubConnections() {
        return new HashSet<>(subConnections);
    }

    int getSubConnectionCount() {
        return subConnections.size();
    }

    Set<Connection> getSuperConnections() {
        return new HashSet<>(superConnections);
    }

    int getSuperConnectionCount() {
        return superConnections.size();
    }

    Set<TracePoint> getPoints() {
        return new HashSet<>(points);
    }

    boolean isBaseConnection() {
        return subConnections.isEmpty();
    }

    Connection getBaseConnection() {
        if (isBaseConnection()) {
            return this;
        }
        return subConnections.iterator().next().getBaseConnection();
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

    TracePoint connectsTo(TracePoint point) {
        for (TracePoint tracePoint : points) {
            if (point.equals(tracePoint)) {
                return tracePoint;
            }
        }
        return null;
    }

    Connection existingConnection() {
        TracePoint point = getPoint();
        TracePoint otherPoint = otherEnd(point);
        return point.connectedTo(otherPoint);
    }

    void setFree() {
        if (!isFree()) {
            Log.v(TAG, "Setting " + this + " free");
            setState(State.FREE);
            for (Connection connection : subConnections) {
                connection.setFree();
            }
        }
    }

    /**
     * Sets current connection to occupied, subconnections to occupied and superconnections to
     * blocked.
     */
    void setOccupied() {
        State prevState = state;
        if (prevState == State.BLOCKED || prevState == State.FREE) {
            for (Connection connection : subConnections) {
                connection.setOccupied();
            }
            Log.v(TAG, "Setting " + this + " occupied");
            this.setState(State.OCCUPIED);
        }
        if (prevState == State.FREE) {
            for (Connection connection : superConnections) {
                connection.setBlocked();
            }
        }
    }

    private void setBlocked() {
        if (isFree()) {
            Log.v(TAG, "Setting " + this + " free");
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
            if (this.directionFrom(commonPoint).isOppositeOrigin(connection.directionFrom(commonPoint))) {
                return new Connection(this.otherEnd(commonPoint), connection.otherEnd(commonPoint));
            }
        }
        return null;
    }

    void addSelfToPoints() {
        for (TracePoint tracePoint : points) {
            tracePoint.addConnection(this);
        }
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

    TracePoint getPoint() {
        return points.iterator().next();
    }

    TracePoint otherEnd(TracePoint tracePoint) {
        if (!points.contains(tracePoint)) Log.wtf(TAG, "OtherEnd passed point that is not in connection");
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

    double slope() {
        TracePoint p1 = getPoint();
        TracePoint p2 = otherEnd(p1);
        if (p1.getX() == p2.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        return (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
    }

    double yIntercept() {
        double m = slope();
        if (m == Double.POSITIVE_INFINITY) return Double.POSITIVE_INFINITY;
        TracePoint p = getPoint();
        return p.getY() - p.getX() * m;
    }

    /**
     * Inclusive of connection's points
     * @param p
     * @return
     */
    boolean squareContains(Point p) {
        TracePoint p1 = getPoint();
        TracePoint p2 = otherEnd(p1);
        return !(p.getX() < p1.getX() && p.getX() < p2.getX()) &&
                !(p.getX() > p1.getX() && p.getX() > p2.getX()) &&
                !(p.getY() < p1.getY() && p.getY() < p2.getY()) &&
                !(p.getY() > p1.getY() && p.getY() > p2.getY());
    }

    /**
     * Returns point where connections intersect else null. If intersection occurs at a connection
     * point, return null.
     * @param connection
     * @return
     */
    Point intersects(Connection connection) {
        if (connection.equals(this)) return null;
        if (null != connection.commonPoint(this)) return null;
        double m1 = slope();
        double m2 = connection.slope();
        if (m1 == m2) return null;
        double b1 = yIntercept();
        double b2 = connection.yIntercept();

        double x;
        double y;

        if(m1 == Float.POSITIVE_INFINITY) {
            x = getPoint().getX();
            y = m2 * x + b2;
        } else if(m2 == Float.POSITIVE_INFINITY) {
            x = connection.getPoint().getX();
            y = m1 * x + b1;
        } else {
            x = (b2 - b1)/(m1 - m2);
            y = m1 * x + b1;
        }
        Point intersectionPoint = new Point(x, y);
        if(!squareContains(intersectionPoint)) return null;
        if(!connection.squareContains(intersectionPoint)) return null;
        Log.v(TAG, connection + " intersects " + this + " at " + intersectionPoint);
        return intersectionPoint;
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

    @Override
    public String toString() {
        TracePoint p1 = getPoint();
        TracePoint p2 = otherEnd(p1);
        return "<" + p1 + "," + p2 + ">";
    }

    private enum State {
        OCCUPIED,
        BLOCKED,
        FREE
    }

}
