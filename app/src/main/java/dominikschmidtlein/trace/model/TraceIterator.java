package dominikschmidtlein.trace.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by domin_2o9sb4z on 2017-01-07.
 */
public class TraceIterator implements Iterator<TracePoint> {

    private Trace trace;
    private Set<TracePoint> visitedPoints;
    private Queue<TracePoint> nextPoints;

    TraceIterator(Trace trace) {
        this.trace = trace;
        visitedPoints = new HashSet<>();
        nextPoints = new LinkedList<>();
        nextPoints.add(trace.getPoint());
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
