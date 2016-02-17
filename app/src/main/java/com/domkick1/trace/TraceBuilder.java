package com.domkick1.trace;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-01-30.
 */
public class TraceBuilder extends Trace {

    private Mode mode;
    private ArrayList<Point> points;
    private ArrayList<Line> shape;
    private ArrayList<Point> problemPoints;


    public TraceBuilder(LevelHelper levelHelper, android.graphics.Point screenSize, int actionBarHeight, Mode mode) {
        super(levelHelper, screenSize, actionBarHeight);
        this.mode = mode;
        points = levelHelper.getGridAsPoints(mode, screenSize.x, screenSize.y, actionBarHeight);
        shape = new ArrayList<>(50);
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
        if (event.getPointerCount() > 1)
            return false;

        Point touchPoint = new Point(event.getX(), event.getY());
        Point nearPoint;

        problemPoints = null;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                nearPoint = shape.isEmpty() ? isNearVertexInPoints(touchPoint, points, RADIUS)
                        : isNearVertexInLines(touchPoint, shape, RADIUS);
                if (nearPoint != null)
                    shape.add(new Line(nearPoint, touchPoint));
                return nearPoint != null;

            case MotionEvent.ACTION_MOVE:
                nearPoint = isNearVertexInPoints(touchPoint, points, RADIUS);
                if (nearPoint != null) {
                    if (!shape.get(shape.size() - 1).getP1().equals(nearPoint)) {
                        Line newLine = new Line(shape.get(shape.size() - 1).getP1(), nearPoint);
                        shape.remove(shape.size() - 1);
                        shape.addAll(getSimpleLines(newLine));
                        cleanShape();
                        shape.add(new Line(nearPoint, touchPoint));
                        return true;
                    }
                }
                replaceLastPoint(touchPoint, shape);
                return true;
            default:
                shape.remove(shape.size() - 1);
                updateShapeLegality();
                return false;

        }
    }

    protected ArrayList<Line> getSimpleLines(Line line) {
        ArrayList<Point> intersectingPoints = getIntersectingPoints(line);
        sortPoints(intersectingPoints, line);
        ArrayList<Line> simpleLines = new ArrayList<>(intersectingPoints.size() - 1);

        for (int i = 0; i < intersectingPoints.size() - 1; i++)
            simpleLines.add(new Line(intersectingPoints.get(i), intersectingPoints.get(i + 1)));

        return simpleLines;
    }

    protected ArrayList<Point> getIntersectingPoints(Line line) {
        ArrayList<Point> intersectingPoints = new ArrayList<>();
        for (Point point : points)
            if (line.intersects(point))
                intersectingPoints.add(point);
        return intersectingPoints;
    }

    protected void sortPoints(ArrayList<Point> points, Line line) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < points.size(); i++) {
                if (line.getP1().getDistance(points.get(i - 1)) > line.getP1().getDistance(points.get(i))) {
                    swap(i - 1, i, points);
                    swapped = true;
                }
            }
        } while (swapped);
        if (!line.getP2().equals(points.get(points.size() - 1)))
            throw new UnsupportedOperationException();
    }

    protected void swap(int i1, int i2, ArrayList<Point> points) {
        Point temp = points.get(i1);
        points.set(i1, points.get(i2));
        points.set(i2, temp);
    }

    private void cleanShape() {
        shape = removeDuplicates(shape);
    }

    private void updateShapeLegality(){
        problemPoints = isShapeLegal();
    }


    private ArrayList<Point> isShapeLegal() {
        ArrayList<Point> problemPoints = new ArrayList<>(shape.size());
        for (Line line : shape)
            for (Point point : line)
                if (!problemPoints.remove(point))
                    problemPoints.add(point);
        return (problemPoints.size() == 2 || problemPoints.isEmpty()) ? null : problemPoints;
    }

    public boolean addLevel(){
        if(getProblemPoints() != null)
            return false;
        levelHelper.addLevelToShapes(shape);
        return true;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Line> getShape() {
        return shape;
    }

    public ArrayList<Point> getProblemPoints() {
        return problemPoints;
    }
}
