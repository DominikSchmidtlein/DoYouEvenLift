package dominikschmidtlein.trace.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class    Connection {

    private TracePoint point1;
    private TracePoint point2;

    private List<Connection> subConnections = new ArrayList<>();
    private List<Connection> superConnections = new ArrayList<>();

    private State state = State.FREE;

    public Connection(TracePoint p1, TracePoint p2) {
        point1 = p1;
        point2 = p2;

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

    public List<Connection> getSubConnections() {
        return subConnections;
    }

    public List<Connection> getSuperConnections() {
        return superConnections;
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

    private enum State {
        OCCUPIED,
        BLOCKED,
        FREE
    }

}
