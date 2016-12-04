package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class GameEngine implements Engine{

    TracePoint startPoint;
    TracePoint endPoint;
    Trace trace;

    public GameEngine(Trace trace) {
        setTrace(trace);
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public TracePoint getStartPoint() {
        return startPoint;
    }

    public TracePoint getEndPoint() {
        return endPoint;
    }

    /**
     * Check if the touchPoint is in the trace. If so, initialize start/end points.
     * @param touchPoint
     */
    @Override
    public boolean down(TracePoint touchPoint) {
        TracePoint nearPoint = trace.nearPoint(touchPoint);

        if (nearPoint != null) {
            startPoint = nearPoint;
            endPoint = touchPoint;
            return true;
        }
        return false;
    }

    /**
     * Check if touchPoint is in the trace and the prospective line exists and is not occupied. If
     * so, update the trace. Update current connection in either case.
     * @param touchPoint
     */
    @Override
    public boolean move(TracePoint touchPoint) {
        TracePoint nearPoint = trace.nearPoint(touchPoint);

        if (nearPoint != null) {
            Connection connection = startPoint.connectedTo(nearPoint);
            if (connection != null) {
                if (connection.isFree()) {
                    connection.setOccupied();
                    startPoint = nearPoint;
                }
            }
        }
        endPoint = touchPoint;
        return true;
    }

    /**
     * Check if trace is fully occupied, notify win event listeners if necessary. Reset trace.
     * @param touchPoint
     */
    @Override
    public boolean up(TracePoint touchPoint) {
        if (trace.isComplete()) {
            // notify win event
        }
        trace.reset();
        return false;
    }

}
