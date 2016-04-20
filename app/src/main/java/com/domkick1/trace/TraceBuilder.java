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

    private static final int RADIUS_DOWN = 80;
    private static final int RADIUS_MOVE = 40;
    private final android.graphics.Point size;

    private Mode mode;
    private PointList points, problemPoints;
    private LineList shape;

    private final int pointGap = 200;


    public TraceBuilder(Context context, android.graphics.Point screenSize, int actionBarHeight, Mode mode) {
        super(context);
        this.size = screenSize;
        this.mode = mode;
        points = generatePointsByMode(mode);
        shape = new LineList(50);
        problemPoints = new PointList();
    }

    public enum Mode {
        SQUARE("square"), ISOMETRIC("isometric");
        private final String value;

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
                        shape.addWithReplacement(getSimpleLines(newLine));
                        shape = shape.getWithoutDuplicates();
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

    private void updateShapeLegality() {
        problemPoints = shape.getOddlyOccurringPoints();
        if (problemPoints.size() == 2)
            problemPoints.clear();
    }

    public boolean logLevel() {
        if (getProblemPoints().isEmpty())
            Log.d("DOM", shape.getConnectedLines().addIntersections().toString());
        return getProblemPoints().isEmpty();
    }

    private PointList generatePointsByMode(Mode mode) {
        if (mode == Mode.ISOMETRIC)
            return generateIsometricPoints(pointGap, size.x, size.y, 0);
        else if (mode == Mode.SQUARE)
            return generateSquarePoints(pointGap, size.x, size.y, 0);
        else
            return null;
    }

    public PointList generateSquarePoints(int pointGap, int width, int height, int topOffset) {
        PointList points = new PointList();
        for (int y = topOffset + pointGap / 2; y < height - pointGap / 4; y += pointGap)
            for (int x = pointGap / 2; x < width - pointGap / 4; x += pointGap)
                points.add(new Point(x, y));
        return points;
    }

    public PointList generateIsometricPoints(int pointGap, int width, int height, int yOffset) {
        float xStart = pointGap / 2;
        float yStart = pointGap / 2 + yOffset;
        float xEnd = width - pointGap / 4;
        float yEnd = height - pointGap / 4;
        float yJump = (float) Math.sqrt(3) * pointGap / 2;

        PointList points = new PointList();
        for (float y = yStart; y < yEnd; y += yJump)
            for (float x = xStart + getXOffset(y, yJump, pointGap, yOffset); x < xEnd; x += pointGap)
                points.add(new Point(x, y));
        return points;
    }

    public float getXOffset(float y, float yJump, float pointGap, float yOffset) {
        return (((y - yOffset - pointGap / 2) / yJump) % 2) - 0 < 0.001 ? 0 : 100;
    }

    public void clear() {
        shape.clear();
        problemPoints.clear();
        setChanged();
        notifyObservers();
    }

    public void toggleGrid() {
        setMode(mode == Mode.ISOMETRIC ? Mode.SQUARE : Mode.ISOMETRIC);
        clear();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        points = generatePointsByMode(mode);
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
