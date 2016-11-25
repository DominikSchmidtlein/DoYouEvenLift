package com.domkick1.trace.model;

import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class TracePoint {

    double x;
    double y;

    private List<Connection> connections;

    Connection connectedTo(TracePoint tracePoint) {
        return null;
    }

}
