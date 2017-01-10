package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2017-01-07.
 */
class TracePointIterator extends GraphIterator<TracePoint> {

    TracePointIterator(TracePoint tracePoint) {
        super(tracePoint);
    }

    @Override
    public TracePoint next() {
        TracePoint nextPoint = super.next();
        for (Connection connection : nextPoint.getConnections()) {
            if (!visitedElements.contains(connection.otherEnd(nextPoint)) &&
                    !nextElements.contains(connection.otherEnd(nextPoint))) {
                nextElements.add(connection.otherEnd(nextPoint));
            }
        }
        return nextPoint;
    }
}
