package dominikschmidtlein.trace.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2016-12-09.
 */
public class TracePointMap {

    private Map<Integer, Set<TracePoint>> points;
    private int width;
    private int height;
    private int xbins;
    private int ybins;

    public TracePointMap(int width, int height, double margin, int xbins, int ybins) {
        int bins = xbins * ybins;
        this.width = width;
        this.height = height;
        this.xbins = xbins;
        this.ybins = ybins;
        points = new HashMap<>(bins);
        for (int i = 0; i < bins; i ++) {
            points.put(i, new HashSet<TracePoint>());
        }
    }

    public TracePoint nearPoint(TracePoint tracePoint) {
        return null;
    }

    public void addTracePoint(TracePoint tracePoint) {

    }

    public int calculateBin(TracePoint tracePoint) {
        return -1;
    }

    public int calculateBin(double x, double y) {
        if (x < 0 || x > width || y < 0 || y > height) {
            throw new IllegalArgumentException();
        }
        if (x == width) {
            x -= 1;
        }
        if (y == height) {
            y -= 1;
        }
        return (int)(y/height*ybins)*xbins + (int)(x/width*xbins);
    }

}
