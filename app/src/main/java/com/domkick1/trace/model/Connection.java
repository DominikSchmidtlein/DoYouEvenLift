package com.domkick1.trace.model;

import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Connection {

    private TracePoint point1;
    private TracePoint point2;

    private List<Connection> subConnections;
    private List<Connection> superConnections;

    private State state;

    private int length;
    private int freeCount;

    public Connection(TracePoint p1, TracePoint p2) {
        point1 = p1;
        point2 = p2;
    }

    boolean isFree() {
        return state == State.FREE;
    }

    /**
     * Sets current connection to occupied, subconnections to occupied and superconnections to
     * blocked.
     */
    void setOccupied() {

    }

    private enum State {
        OCCUPIED,
        BLOCKED,
        FREE
    }

}
