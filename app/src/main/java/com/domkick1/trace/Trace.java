package com.domkick1.trace;

import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
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

    /**
     * Replaces point 2 of the last line in the given array with the given point.
     * @param point the new point
     * @param lines the set of lines where the last line's second point is replaced
     */
    protected void replaceLastPoint(Point point, ArrayList<Line> lines){
        lines.set(lines.size()-1, new Line(lines.get(lines.size()-1).getP1(), point));
    }

    /**
     * Checks if a line is in an array of lines, regardless of order of points.
     * @param set list of lines
     * @param line line to be searched for
     * @return true of set contains line as forwards or backwards
     */
    protected boolean isLineInSet(ArrayList<Line> set, Line line){
        for(Line l: set)
            if(l.equals(line) || l.getOpposite().equals(line))
                return true;
        return false;
    }

    /**
     * Returns a list of lines where all instances of duplicates are removed. Therefore if there
     * were 2 instances of lineA, there will be none in the return list.
     * @param lines a list of lines that may contain duplicates
     * @return a list free of all instances that were once duplicates
     */
    protected ArrayList<Line> removeDuplicates(ArrayList<Line> lines){
        ArrayList<Line> noDuplicateLines = new ArrayList<>(lines.size());
        for(ListIterator<Line> i = lines.listIterator(); i.hasNext();){
            Line line = i.next();
            i.remove();
            if(!lines.contains(line.getOpposite()) && !lines.contains(line))
                noDuplicateLines.add(line);
            i.add(line);
        }
        return noDuplicateLines;
    }

}
