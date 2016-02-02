package com.domkick1.trace;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-01-30.
 */
public class TraceBuilder extends Trace {

    private ArrayList<Point> points;
    private ArrayList<Line> shape;

    public TraceBuilder(android.graphics.Point screenSize, int actionBarHeight) {
        super(screenSize, actionBarHeight);
    }

    public enum Mode {SQUARE, ISOMETRIC}





    /**
     * Checks if there's a point in the vicinity. Update most recent line if not. Otherwise add new
     * line to shape array and start new line.
     * If user lifted finger, remove unfinished line.
     *
     * @param event
     * @return
     */
    @Override
    protected boolean handleTouch(View v, MotionEvent event) {
        if(event.getPointerCount() > 1)
            return false;

        Point touchPoint = new Point(event.getX(), event.getY());
        Point nearPoint = isNearVertexInPoints(touchPoint, points, RADIUS);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(nearPoint != null)
                    shape.add(new Line(nearPoint, touchPoint));
                return nearPoint != null;

            case MotionEvent.ACTION_MOVE:
                if(nearPoint != null){
                    replaceLastPoint(nearPoint, shape);
                    shape.add(new Line(nearPoint, touchPoint));
                    return true;
                }
                replaceLastPoint(touchPoint, shape);
                return true;
            default:
                shape.remove(shape.size()-1);
                return false;

        }
    }

}
