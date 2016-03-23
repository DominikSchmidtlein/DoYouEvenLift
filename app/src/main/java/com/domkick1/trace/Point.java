package com.domkick1.trace;

/**
 * Created by dominik on 03/08/15.
 */
public class Point {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point) o;
        return Math.abs(p.getX() - this.getX()) < 0.0001 && Math.abs(p.getY() - this.getY()) < 0.0001;
    }

    public double getDistance(Point p2) {
        return Math.sqrt(Math.pow(this.getX() - p2.getX(), 2) + Math.pow(this.getY() - p2.getY(), 2));
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
