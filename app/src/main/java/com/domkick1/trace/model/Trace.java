package com.domkick1.trace.model;

import java.util.List;
import java.util.Map;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Trace {

    Map<TracePoint, TracePoint> points;
    List<Connection> baseConnections;

    TracePoint nearPoint(TracePoint tracePoint) {
        return null;
    }

    boolean isComplete() {
        return false;
    }

    /**
     * Sets all connections to unoccupied. Sets
     */
    void reset() {

    }

}
