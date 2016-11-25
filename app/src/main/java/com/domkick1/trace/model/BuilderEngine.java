package com.domkick1.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-24.
 */
public class BuilderEngine implements Engine {

    @Override
    public boolean down(TracePoint touchPoint) {
        return false;
    }

    @Override
    public boolean move(TracePoint touchPoint) {
        return false;
    }

    @Override
    public boolean up(TracePoint touchPoint) {
        return false;
    }

}
