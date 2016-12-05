package dominikschmidtlein.trace.model;

/**
 * Created by domin_2o9sb4z on 2016-11-24.
 */
public interface Engine {

    void down(TracePoint touchPoint);
    void move(TracePoint touchPoint);
    void up();

}
