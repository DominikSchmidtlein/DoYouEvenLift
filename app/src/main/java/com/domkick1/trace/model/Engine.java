package com.domkick1.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class Engine {

    Connection current;
    Trace trace;

    /**
     * Check if the touchPoint is in the trace. If so, initialize connection.
     * @param touchPoint
     */
    void down(TracePoint touchPoint) {

    }

    /**
     * Check if touchPoint is in the trace and the prospective line exists and is not occupied. If
     * so, update the trace. Update current connection in either case.
     * @param touchPoint
     */
    void move(TracePoint touchPoint) {

    }

    /**
     * Check if trace is fully occupied, notify win event listeners if necessary. Reset trace.
     * @param touchPoint
     */
    void up(TracePoint touchPoint) {

    }

}
