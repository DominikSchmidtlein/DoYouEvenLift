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

    Connection c1;
    Connection c2;
    Connection c3;
    Connection c4;
    Connection c5;
    Connection c6;
    Connection c7;
    Connection c8;
    Connection c9;
    Connection c10;

    TracePointIterator tracePointIterator;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        c1 = new Connection(p1, p2);
        c2 = new Connection(p1, p3);
        c3 = new Connection(p1, p5);
        c4 = new Connection(p2, p4);
        c5 = new Connection(p2, p5);
        c6 = new Connection(p3, p4);
        c7 = new Connection(p3, p5);
        c8 = new Connection(p4, p5);
        c9 = new Connection(p1, p4);
        c10 = new Connection(p2, p3);

        tracePointIterator = new TracePointIterator(p1);
    }

    @Test
    public void testForeachLoop() {
        p1.addConnection(c1);
        p2.addConnection(c1);
        p1.addConnection(c2);
        p3.addConnection(c2);
        p1.addConnection(c3);
        p5.addConnection(c3);
        p2.addConnection(c4);
        p4.addConnection(c4);
        p2.addConnection(c5);
        p5.addConnection(c5);
        p3.addConnection(c6);
        p4.addConnection(c6);
        p3.addConnection(c7);
        p5.addConnection(c7);
        p4.addConnection(c8);
        p5.addConnection(c8);
        p1.addConnection(c9);
        p4.addConnection(c9);
        p2.addConnection(c10);
        p3.addConnection(c10);

        c3.addSuperConnection(c9);
        c8.addSuperConnection(c9);
        c5.addSuperConnection(c10);
        c7.addSuperConnection(c10);

        Set<TracePoint> expectedSet = new HashSet<>();
        expectedSet.add(p1);
        expectedSet.add(p2);
        expectedSet.add(p3);
        expectedSet.add(p4);
        expectedSet.add(p5);

        Set<TracePoint> actualSet = new HashSet<>();

        int count = 0;
        for (TracePoint tracePoint; tracePointIterator.hasNext(); ) {
            tracePoint = tracePointIterator.next();
            actualSet.add(tracePoint);
            count++;
        }

        assertEquals(expectedSet, actualSet);
        assertEquals(5, count);
    }

    @Test
    public void testForeachLoopSplit() {
        p1.addConnection(c1);
        p2.addConnection(c1);
        p3.addConnection(c6);
        p4.addConnection(c6);

        int count = 0;
        for ( ; tracePointIterator.hasNext(); ) {
            tracePointIterator.next();
            count ++;
        }
        assertEquals(count, 2);
    }

}

