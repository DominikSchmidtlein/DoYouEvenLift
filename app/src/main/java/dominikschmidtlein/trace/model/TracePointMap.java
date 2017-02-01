package dominikschmidtlein.trace.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dominikschmidtlein.trace.Log;

/**
 * Created by domin_2o9sb4z on 2016-12-09.
 */
class TracePointMap {
    private static final String TAG = "TracePointMap";
    private static final int DEFAULT_XBINS = 4;
    private static final int DEFAULT_YBINS = 4;

    private Map<Integer, Set<TracePoint>> points;
    private int width;
    private int height;
    private int xbins;
    private int ybins;
    private double margin;
    private int count = 0;

    TracePointMap(int width, int height, double margin, int xbins, int ybins) {
        int bins = xbins * ybins;
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.xbins = xbins;
        this.ybins = ybins;

        points = new HashMap<>(bins);
        for (int i = 0; i < bins; i++) {
            points.put(i, new HashSet<TracePoint>());
        }
    }

    TracePointMap(int width, int height, double margin) {
        this(width, height, margin, DEFAULT_XBINS, DEFAULT_YBINS);
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    TracePoint nearPoint(Point point) {
        int binNumber = calculateBin(point);
        Set<TracePoint> bin = points.get(binNumber);
        TracePoint nearPoint = null;
        double distance = margin;
        double calcDistance;
        for (TracePoint tracePoint : bin) {
            calcDistance = tracePoint.distanceTo(point);
            if (calcDistance <= distance) {
                nearPoint = tracePoint;
                distance = calcDistance;
            }
        }
        return nearPoint;
    }

    TracePoint addTracePoint(Point point) {
        if (point instanceof TracePoint) {
            return (TracePoint) point;
        }
        if (!pointInBounds(point)) {
            Log.e(TAG, point + " out of bounds");
            throw new IllegalArgumentException();
        }
        TracePoint existingPoint = existingPoint(point);
        if (null != existingPoint) {
            Log.i(TAG, point + " already exists");
            return existingPoint;
        }
        existingPoint = new TracePoint(point.getX(), point.getY());
        points.get(calculateBin(existingPoint)).add(existingPoint);
        points.get(calculateBinUL(existingPoint)).add(existingPoint);
        points.get(calculateBinUR(existingPoint)).add(existingPoint);
        points.get(calculateBinDL(existingPoint)).add(existingPoint);
        points.get(calculateBinDR(existingPoint)).add(existingPoint);
        count++;
        return existingPoint;
    }

    boolean removeTracePoint(Point point) {
        return false;
    }

    int getCount() {
        return count;
    }

    TracePoint getTracePoint() {
        for (Set<TracePoint> tracePoints : points.values()) {
            for (TracePoint tracePoint : tracePoints) {
                return tracePoint;
            }
        }
        return null;
    }

    private boolean pointInBounds(Point point) {
        return point.getX() >= 0 && point.getX() <= width &&
                point.getY() >= 0 && point.getY() <= height;
    }

    /**
     * If a point at the same location already exists it is returned otherwise null.
     * @param point
     * @return
     */
    private TracePoint existingPoint(Point point) {
        int binNumber = calculateBin(point);
        Set<TracePoint> bin = points.get(binNumber);
        for (TracePoint tracePoint : bin) {
            if (point.equals(tracePoint)) {
                return tracePoint;
            }
        }
        return null;
    }

    private int calculateBin(Point point) {
        return calculateBin(point.getX(), point.getY());
    }

    int calculateBin(double x, double y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            throw new IllegalArgumentException();
        }
        if (x == width) {
            x -= 1;
        }
        if (y == height) {
            y -= 1;
        }
        return (int) (y / height * ybins) * xbins + (int) (x / width * xbins);
    }

    private int calculateBinUL(Point point) {
        return calculateBin(point.getX() - margin, point.getY() - margin);
    }

    private int calculateBinUR(Point point) {
        return calculateBin(point.getX() + margin, point.getY() - margin);
    }

    private int calculateBinDL(Point point) {
        return calculateBin(point.getX() - margin, point.getY() + margin);
    }

    private int calculateBinDR(Point point) {
        return calculateBin(point.getX() + margin, point.getY() + margin);
    }
}
