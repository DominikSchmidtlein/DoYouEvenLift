package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-07.
 */
public class TracePointIteratorTest {

    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;

    Trace trace;
    TracePointIterator tracePointIterator;

    @Before
    public void setUp() throws Exception {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        trace = new Trace(new TracePointMap(500, 500, 50));
    }

    @Test
    public void testForeachLoop() {
        trace.addConnection(p1, p2);
        trace.addConnection(p1, p3);
        trace.addConnection(p1, p5);
        trace.addConnection(p2, p4);
        trace.addConnection(p2, p5);
        trace.addConnection(p3, p4);
        trace.addConnection(p3, p5);
        trace.addConnection(p4, p5);

        tracePointIterator = trace.tracePointIterator();

        Set<TracePoint> expectedSet = new HashSet<>();
        expectedSet.add(p1);
        expectedSet.add(p2);
        expectedSet.add(p3);
        expectedSet.add(p4);
        expectedSet.add(p5);

        Set<TracePoint> actualSet = new HashSet<>();


        for (TracePoint tracePoint; tracePointIterator.hasNext(); ) {
            tracePoint = tracePointIterator.next();
            actualSet.add(tracePoint);
        }

        assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testForeachLoopSplit() {
        trace.addConnection(p1, p2);
        trace.addConnection(p3, p4);
        tracePointIterator = trace.tracePointIterator();

        int count = 0;
        for (TracePoint tracePoint; tracePointIterator.hasNext(); ) {
            tracePoint = tracePointIterator.next();
            count ++;
        }
        assertEquals(count, 2);
    }

}

