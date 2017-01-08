package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-07.
 */
public class TracePointIterator implements Iterator<TracePoint> {

    private Set<TracePoint> visitedPoints;
    private Queue<TracePoint> nextPoints;

    TracePointIterator(TracePoint tracePoint) {
        visitedPoints = new HashSet<>();
        nextPoints = new LinkedList<>();
        nextPoints.add(tracePoint);
    }

    @Override
    public boolean hasNext() {
        return nextPoints.peek() != null;
    }

    @Override
    public TracePoint next() {
        TracePoint nextPoint = nextPoints.remove();
        visitedPoints.add(nextPoint);
        for (Connection connection : nextPoint.getConnections()) {
            if (!visitedPoints.contains(connection.otherEnd(nextPoint))) {
                nextPoints.add(connection.otherEnd(nextPoint));
            }
        }
        return nextPoint;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
