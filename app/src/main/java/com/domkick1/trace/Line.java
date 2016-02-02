package com.domkick1.trace;

import java.util.Iterator;

/**
 * Created by dominik on 05/08/15.
 */
public class Line implements Iterable<Point> {
    public static final double INFINITY = Double.POSITIVE_INFINITY;

    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(float p1x, float p1y, float p2x, float p2y) {
        this(new Point(p1x, p1y), new Point(p2x, p2y));
    }

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

    public double getSlope() {
        double rise = p2.getY() - p1.getY();
        double run = p2.getX() - p1.getX();
        if (run == 0)
            return Line.INFINITY;
        return rise / run;
    }

    public double getIsometricSlope(double xOffset, double yOffset, double columnWidth, double rowHeight) {
        double rise = p2.getY() - p1.getY();
        double p1IsometricX = p1.getX() - xOffset - (p1.getY() - yOffset) / rowHeight * (columnWidth / 2);
        double p2IsometricX = p2.getX() - xOffset - (p2.getY() - yOffset) / rowHeight * (columnWidth / 2);
        double isometricRun = p2IsometricX - p1IsometricX;
        if (isometricRun == 0)
            return Line.INFINITY;
        return rise / isometricRun;
    }

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
        return Math.round(p1.getX() * p1.getY() * p2.getX() * p2.getY() / 1000000 - 2147483647);
    }

    public Line getOpposite() {
        return new Line(p2, p1);
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

}
