package com.domkick1.trace;

import android.support.annotation.NonNull;

import java.util.Iterator;

/**
 * Created by dominik on 05/08/15.
 */
public class Line implements Iterable<Point> {

    private final Point p1;
    private final Point p2;

    public Line(@NonNull Point p1, @NonNull Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(float p1x, float p1y, float p2x, float p2y) {
        this(new Point(p1x, p1y), new Point(p2x, p2y));
    }

    @NonNull
    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {

            private int current = 1;

            @Override
            public boolean hasNext() {
                return current == 1 || current == 2;
            }

            @Override
            public Point next() {
                current++;
                if (current == 2)
                    return p1;
                return (current == 3) ? p2 : null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean contains(Object obj) {
        return p1.equals(obj) || p2.equals(obj);
    }

    public double getIsometricSlope(double xOffset, double yOffset, double columnWidth, double rowHeight) {
        double rise = p2.getY() - p1.getY();
        double p1IsometricX = p1.getX() - xOffset - (p1.getY() - yOffset) / rowHeight * (columnWidth / 2);
        double p2IsometricX = p2.getX() - xOffset - (p2.getY() - yOffset) / rowHeight * (columnWidth / 2);
        double isometricRun = p2IsometricX - p1IsometricX;
        if (isometricRun == 0)
            return Float.POSITIVE_INFINITY;
        return rise / isometricRun;
    }

    /**
     * If lines have any points in common, the common point is returned.
     *
     * @param line a line that may have points in common with this
     * @return a common point or null if none exist
     */
    public Point isTouching(Line line) {
        if (line == null)
            return null;
        if (p1.equals(line.getP1()) || p1.equals(line.getP2()))
            return p1;
        if (p2.equals(line.getP1()) || p2.equals(line.getP2()))
            return p2;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Line))
            return false;
        Line l = (Line) o;
        return this.p1.equals(l.getP1()) && this.p2.equals(l.getP2());
    }

    @Override
    public int hashCode() {
        return Math.round(p1.getX() * p1.getY() * p2.getX() * p2.getY());
    }

    public Line getOpposite() {
        return new Line(p2, p1);
    }

    public float getYIntercept() {
        return p2.getY() - getSlope() * p2.getX();
    }

    public float getSlope() {
        float rise = p2.getY() - p1.getY();
        float run = p2.getX() - p1.getX();
        if (run == 0)
            return Float.POSITIVE_INFINITY;
        return rise / run;
    }

    /**
     * Returns true if a point lies on the line, else false. Endpoints return true.
     *
     * @param point a point that may lie on the line
     * @return true if the point is on the line
     */
    public boolean intersects(Point point) {
        if (!squareContains(point))
            return false;
        if (getSlope() == Float.POSITIVE_INFINITY)
            return p1.getX() == point.getX();
        return Math.abs((point.getY() - getSlope() * point.getX() - getYIntercept())) < 0.0000000000001;
    }

    public Point intersects(Line line) {
        if (line == null) return null;
        if (getSlope() == line.getSlope()) return null;

        float x = (line.getYIntercept() - getYIntercept())/(getSlope() - line.getSlope());
        float y = getSlope() * x + getYIntercept();
        Point intersectionPoint = new Point(x, y);

        if(!squareContains(intersectionPoint)) return null;
        if(!line.squareContains(intersectionPoint)) return null;
        return intersectionPoint;
    }

    public boolean squareContains(Point point) {
        return !(point.getX() < p1.getX() && point.getX() < p2.getX()) &&
                !(point.getX() > p1.getX() && point.getX() > p2.getX()) &&
                !(point.getY() < p1.getY() && point.getY() < p2.getY()) &&
                !(point.getY() > p1.getY() && point.getY() > p2.getY());
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

}
