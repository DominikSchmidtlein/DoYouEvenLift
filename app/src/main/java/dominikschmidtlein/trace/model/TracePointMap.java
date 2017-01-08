package dominikschmidtlein.trace.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-12-09.
 */
class TracePointMap {

    static final int DEFAULT_XBINS = 4;
    static final int DEFAULT_YBINS = 4;

    private Map<Integer, Set<TracePoint>> points;
    private int width;
    private int height;
    private int xbins;
    private int ybins;
    private double margin;

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

    /**
     * If a point at the same location already exists it is returned otherwise null.
     * @param tracePoint
     * @return
     */
    TracePoint equalPoint(TracePoint tracePoint) {
        int binNumber = calculateBin(tracePoint);
        Set<TracePoint> bin = points.get(binNumber);

        for (TracePoint point : bin) {
            if (point.equals(tracePoint)) {
                return point;
            }
        }
        return null;
    }

    TracePoint nearPoint(TracePoint tracePoint) {
        int binNumber = calculateBin(tracePoint);
        Set<TracePoint> bin = points.get(binNumber);

        TracePoint nearPoint = null;
        double distance = margin;
        double calcDistance;
        for (TracePoint point : bin) {
            calcDistance = tracePoint.distanceTo(point);
            if (calcDistance <= distance) {
                nearPoint = point;
                distance = calcDistance;
            }
        }
        return nearPoint;
    }

    TracePoint addTracePoint(TracePoint tracePoint) {
        if (!pointInBounds(tracePoint)) {
            throw new IllegalArgumentException();
        }
        TracePoint existingPoint = equalPoint(tracePoint);
        if (existingPoint == null) {
            points.get(calculateBin(tracePoint)).add(tracePoint);
            points.get(calculateBinUL(tracePoint)).add(tracePoint);
            points.get(calculateBinUR(tracePoint)).add(tracePoint);
            points.get(calculateBinDL(tracePoint)).add(tracePoint);
            points.get(calculateBinDR(tracePoint)).add(tracePoint);
            return tracePoint;
        }
        return existingPoint;
    }

    TracePoint getPoint() {
        for (Set<TracePoint> bin : points.values()) {
            for (TracePoint point : bin) {
                return point;
            }
        }
        return null;
    }

    private int calculateBin(TracePoint tracePoint) {
        return calculateBin(tracePoint.getX(), tracePoint.getY());
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

    private int calculateBinUL(TracePoint tracePoint) {
        return calculateBin(tracePoint.getX() - margin, tracePoint.getY() - margin);
    }

    private int calculateBinUR(TracePoint tracePoint) {
        return calculateBin(tracePoint.getX() + margin, tracePoint.getY() - margin);
    }

    private int calculateBinDL(TracePoint tracePoint) {
        return calculateBin(tracePoint.getX() - margin, tracePoint.getY() + margin);
    }

    private int calculateBinDR(TracePoint tracePoint) {
        return calculateBin(tracePoint.getX() + margin, tracePoint.getY() + margin);
    }

    boolean pointInBounds(TracePoint tracePoint) {
        return tracePoint.getX() >= 0 && tracePoint.getX() <= width &&
                tracePoint.getY() >= 0 && tracePoint.getY() <= height;
    }

}
