package com.domkick1.trace;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by domin_2o9sb4z on 2016-01-30.
 */
public class TraceBuilder extends Trace {

    private TraceBuilderJsonHelper builderJsonHelper;

    private static final int RADIUS_DOWN = 80;
    private static final int RADIUS_MOVE = 40;

    private Mode mode;
    private PointList points;
    private LineList shape;
    private PointList problemPoints;


    public TraceBuilder(Context context, android.graphics.Point screenSize, int actionBarHeight, Mode mode) {
        super(context, screenSize, actionBarHeight);
        this.mode = mode;
        builderJsonHelper = new TraceBuilderJsonHelper(context);

        points = builderJsonHelper.getGridAsPoints(mode, screenSize.x, screenSize.y, actionBarHeight);
        shape = new LineList(50);
        problemPoints = new PointList();
    }

    public enum Mode {
        SQUARE("square"),
        ISOMETRIC("isometric");

        private String value;

        Mode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }


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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nearPoint = shape.isEmpty() ? points.isNearVertex(touchPoint, RADIUS_DOWN)
                        : shape.isNearVertex(touchPoint, RADIUS_DOWN);
                if (nearPoint != null)
                    shape.add(new Line(nearPoint, touchPoint));
                return nearPoint != null;

            case MotionEvent.ACTION_MOVE:
                nearPoint = points.isNearVertex(touchPoint, RADIUS_MOVE);
                problemPoints.clear();
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
                shape.replaceLastPoint(touchPoint);
                return true;
            default:
                shape.remove(shape.size() - 1);
                updateShapeLegality();
                return false;
        }
    }

    private LineList getSimpleLines(Line line) {
        PointList intersectingPoints = points.getPointsOnLine(line);
        intersectingPoints.sortDistanceToPoint(line.getP1());
        return new LineList(intersectingPoints, true);
    }

    private void cleanShape() {
        shape = shape.getWithoutDuplicates();
    }

    private void updateShapeLegality() {
        problemPoints = shape.getOddlyOccurringPoints();
        if (problemPoints.size() == 2)
            problemPoints.clear();
    }

    public boolean generateNewJsonFile() {
        if (!getProblemPoints().isEmpty())
            return false;
        logLevel(shape);
        return true;
    }

    public void resetLevelsToJsonFile() {
        builderJsonHelper.loadLevelsFromAssets();
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

    private void logLevel(LineList level) {
        Log.d("DOM", level.toJsonArray().toString());
    }
}
