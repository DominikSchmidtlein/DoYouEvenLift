package com.domkick1.trace.model;

import java.util.List;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Connection {

    TracePoint point1;
    TracePoint point2;

    List<Connection> subConnections;
    List<Connection> superConnections;

    boolean occupied;
}
