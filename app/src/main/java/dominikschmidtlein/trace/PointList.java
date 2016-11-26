package dominikschmidtlein.trace;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class PointList extends ArrayList<Point> {

    public PointList() {
        super();
    }

    public PointList(int size) {
        super(size);
    }

    public PointList(Collection<Point> collection) {
        super(collection);
    }

    public PointList(@NonNull Line line) {
        super();
        add(line.getP1());
        add(line.getP2());
    }

    public PointList(float[] points) {
        super(points.length / 2);
        for (int i = 0; i < points.length; i += 2)
            add(new Point(points[i], points[i + 1]));
    }

    public PointList(JSONArray jsonArray) {
        super(jsonArray.length() / 2);
        try {
            for (int i = 0; i < jsonArray.length(); i += 2)
                add(new Point(jsonArray.getInt(i), jsonArray.getInt(i + 1)));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public float[] toFloatArray() {
        float[] floats = new float[size() * 2];
        int i = 0;
        for (Point point : this) {
            floats[i] = point.getX();
            floats[i + 1] = point.getY();
            i += 2;
        }
        return floats;
    }

    /**
     * Checks if point is within "radius" of a point in points. Returns the point from points if
     * true, otherwise returns null.
     *
     * @param touchPoint the point which may or may not be near a point from points
     * @param radius     the maximum distance between point and a point in points, for them to be "near"
     * @return a point from points, if it is close, otherwise null
     */
    public Point isNearVertex(Point touchPoint, int radius) {
        for (Point nearPoint : this)
            if (touchPoint.getDistance(nearPoint) < radius)
                return nearPoint;
        return null;
    }

    /**
     * Sort these points according to their distance to point. Sorting from smallest to biggest
     * distance.
     *
     * @param point
     */
    public void sortDistanceToPoint(Point point) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < size(); i++) {
                if (point.getDistance(get(i - 1)) > point.getDistance(get(i))) {
                    swap(i - 1, i);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    /**
     * Swap the points are the 2 given indices.
     *
     * @param index1 first index
     * @param index2 second index
     */
    private void swap(int index1, int index2) {
        Point temp = get(index1);
        set(index1, get(index2));
        set(index2, temp);
    }

    /**
     * Returns a list of points that sit on the given line.
     *
     * @param line a line
     * @return a list of points that sit on the line
     */
    public PointList getPointsOnLine(Line line) {
        PointList intersectingPoints = new PointList();
        for (Point point : this)
            if (line.intersectsPoint(point))
                intersectingPoints.add(point);
        return intersectingPoints;
    }

    /**
     * Returns a list of points that are centered in a screen of a given height, width, and vertical
     * offset.
     *
     * @param dim the screen dimensions
     * @return a list of points that are centered
     */
    public PointList getCenteredPoints(ScreenDimensions dim) {
        Point offsets = getOffsets(dim);

        PointList centeredPoints = new PointList(size());
        for (Point point : this)
            centeredPoints.add(new Point(point.getX() + offsets.getX(), point.getY() + offsets.getY()));

        return centeredPoints;
    }

    /**
     * Returns a line that runs from top left to bottom right of the smallest rectangle that
     * includes all points.
     *
     * @return
     */
    public Line getPointSpan() {
        float left = Float.POSITIVE_INFINITY;
        float top = Float.POSITIVE_INFINITY;
        float right = 0;
        float bot = 0;

        for (Point point : this) {
            left = (point.getX() < left) ? point.getX() : left;
            right = (point.getX() > right) ? point.getX() : right;
            top = (point.getY() < top) ? point.getY() : top;
            bot = (point.getY() > bot) ? point.getY() : bot;
        }
        return new Line(left, top, right, bot);
    }

    public PointList getScaled(ScreenDimensions dim, float useFactor) {
        Line span = getPointSpan();

        float left = span.getP1().getX();
        float top = span.getP1().getY();
        float right = span.getP2().getX();
        float bot = span.getP2().getY();

        float width = right - left;
        float height = bot - top;
        float xScale = useFactor * dim.getWidth() / width;
        float yScale = useFactor * dim.getHeight() / height;

        float scale = (xScale < yScale) ? xScale : yScale;

        PointList scalePoints = new PointList(size());
        for (Point point : this) {
            float x = scale * (point.getX() - left) + left;
            float y = scale * (point.getY() - top) + top;
            scalePoints.add(new Point(x, y));
        }
        return scalePoints.getCenteredPoints(dim);
    }

    /**
     * Calculates the necessary x and y offsets to center these points in a screen with the given
     * dimensions
     *
     * @param dim the screen dimensions
     * @return a point with x and y offsets
     */
    public Point getOffsets(ScreenDimensions dim) {
        Line span = getPointSpan();

        return new Point((dim.getWidth() - span.getP2().getX() - span.getP1().getX()) / 2,
                (dim.getHeight() - span.getP1().getY() - span.getP2().getY()) / 2);
    }

}
