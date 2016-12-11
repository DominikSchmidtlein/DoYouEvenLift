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

    public TracePointMap(int width, int height, double margin, int xbins, int ybins) {
        int bins = xbins * ybins;
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
        return -1;
    }

}
