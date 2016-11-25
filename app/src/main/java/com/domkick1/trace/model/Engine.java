package com.domkick1.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-24.
 */
public interface Engine {

    public boolean down(TracePoint touchPoint);
    public boolean move(TracePoint touchPoint);
    public boolean up(TracePoint touchPoint);

}
