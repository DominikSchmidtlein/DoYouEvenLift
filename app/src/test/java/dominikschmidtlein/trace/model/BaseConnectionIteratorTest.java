package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-08.
 */
public class BaseConnectionIteratorTest {

    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;

    Trace trace;
    BaseConnectionIterator baseConnectionIterator;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        trace = new Trace(new TracePointMap(500, 500, 50));
    }

    @Test
    public void testConnectionIterator() {
        trace.addConnection(p1, p2);
        trace.addConnection(p1, p3);
        trace.addConnection(p1, p5);
        trace.addConnection(p2, p4);
        trace.addConnection(p2, p5);
        trace.addConnection(p3, p4);
        trace.addConnection(p3, p5);
        trace.addConnection(p4, p5);

        baseConnectionIterator = trace.baseConnectionIterator();

        Set<Connection> expectedConnections = new HashSet<>();
        expectedConnections.add(p1.connectedTo(p2));
        expectedConnections.add(p1.connectedTo(p3));
        expectedConnections.add(p1.connectedTo(p5));
        expectedConnections.add(p2.connectedTo(p4));
        expectedConnections.add(p2.connectedTo(p5));
        expectedConnections.add(p3.connectedTo(p4));
        expectedConnections.add(p3.connectedTo(p5));
        expectedConnections.add(p4.connectedTo(p5));

        Set<Connection> actualConnections = new HashSet<>();

        int count = 0;
        for (Connection connection; baseConnectionIterator.hasNext(); ) {
            connection = baseConnectionIterator.next();
            actualConnections.add(connection);
            count++;
        }
        assertEquals(expectedConnections, actualConnections);
        assertEquals(8, count);
    }
}