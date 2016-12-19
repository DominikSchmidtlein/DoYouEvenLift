package dominikschmidtlein.trace.model;

import android.support.annotation.NonNull;

/**
 * Created by domin_2o9sb4z on 2016-12-18.
 */
class Point {

    static final double EPSILON = 0.0000001;

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    double distanceTo(@NonNull Point point) {
        return Math.hypot(getX() - point.getX(), getY() - point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return Math.abs(getX() - p.getX()) < EPSILON && Math.abs(getY() - p.getY()) < EPSILON;
    }

    boolean isOppositeOrigin(Point point) {
        return equals(new Point(-point.getX(), -point.getY()));
    }

    Point unitMagnitude() {
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point(x / magnitude, y / magnitude);
    }

    @Override
    public int hashCode() {
        return (int)(x * 997 + y * 83);
    }

}
