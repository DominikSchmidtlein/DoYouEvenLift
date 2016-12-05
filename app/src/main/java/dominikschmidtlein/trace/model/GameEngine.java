package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-23.
 */
public class GameEngine implements Engine{

    private TracePoint startPoint = null;
    private TracePoint endPoint = null;
    private Trace trace;
    private TouchState state = TouchState.NOT_TOUCHING;


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

    public boolean isTouching() {
        return state == TouchState.TOUCHING;
    }

    /**
     * Check if the touchPoint is in the trace. If so, initialize start/end points.
     * @param touchPoint
     */
    @Override
    public void down(TracePoint touchPoint) {
        TracePoint nearPoint = trace.nearPoint(touchPoint);

        if (nearPoint != null) {
            startPoint = nearPoint;
            endPoint = touchPoint;
        }
    }

    /**
     * Check if touchPoint is in the trace and the prospective line exists and is not occupied. If
     * so, update the trace. Update current connection in either case.
     * @param touchPoint
     */
    @Override
    public void move(TracePoint touchPoint) {
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
    }

    /**
     * Check if trace is fully occupied, notify win event listeners if necessary. Reset trace. Returns
     * whether there was a win (for testing)
     */
    @Override
    public void up() {
        if (trace.isComplete()) {
            // notify win event
        }
        trace.reset();
    }

    private enum TouchState {
        TOUCHING,
        NOT_TOUCHING
    }

}
