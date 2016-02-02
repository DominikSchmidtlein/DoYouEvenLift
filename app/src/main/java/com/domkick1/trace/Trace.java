package com.domkick1.trace;

import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by domin_2o9sb4z on 2016-01-31.
 */
public abstract class Trace extends Observable {

    public static final int RADIUS = 80;

    protected android.graphics.Point size;
    protected int actionBarHeight;

    public Trace(android.graphics.Point screenSize, int actionBarHeight){
        this.size = screenSize;
        this.actionBarHeight = actionBarHeight;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = handleTouch(v, event);
        setChanged();
        notifyObservers();
        return ret;
    }

    protected abstract boolean handleTouch(View v, MotionEvent event);

    /**
     * Returns the point from shape, if point is within radius of any vertices in shape. Otherwise
     * returns null.
     * @param point a point which may or may not be near vertices in shape
     * @param shape a set of lines, from which vertices can be extracted
     * @param radius the maximum distance for point to be considered near a vertex in shape
     * @return returns a near point from shape, or null if none exist
     */
    protected Point isNearVertexInLines(Point point, Iterable<Line> shape, int radius) {
        Point p;
        for(Line line: shape)
            if((p = isNearVertexInPoints(point, line, radius)) != null)
                return p;
        return null;
    }

    /**
     * Checks if point is within "radius" of a point in points. Returns the point from points if
     * true, otherwise returns null.
     * @param point the point which may or may not be near a point from points
     * @param points a set of points
     * @param radius the maximum distance between point and a point in points, for them to be "near"
     * @return a point from points, if it is close, otherwise null
     */
    protected Point isNearVertexInPoints(Point point, Iterable<Point> points, int radius){
        for(Point p: points)
            if(point.getDistance(p) < radius)
                return new Point(p);
        return null;
    }

    protected void replaceLastPoint(Point point, ArrayList<Line> lines){
        lines.set(lines.size()-1, new Line(lines.get(lines.size()-1).getP1(), point));
    }

    public ArrayList<Line> floatsAsLines(float[] floats){
        ArrayList<Line> lines = new ArrayList<>(floats.length/4);
        for(int i = 0; i < floats.length; i += 4)
            lines.add(new Line(floats[i], floats[i + 1], floats[i + 2], floats[i + 3]));
        return lines;
    }

    private boolean isLineInSet(ArrayList<Line> set, Line line){
        for(Line l: set)
            if(l.equals(line) || l.getOpposite().equals(line))
                return true;
        return false;
    }

}
