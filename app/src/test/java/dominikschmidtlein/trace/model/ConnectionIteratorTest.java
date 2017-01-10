package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-08.
 */
public class ConnectionIteratorTest {

    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;

    TraceApi traceApi;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        traceApi = new TraceApi();
    }

    @Test
    public void testConnectionIteratorTrue() {
        traceApi.addConnection(new Connection(p1, p2));
        traceApi.addConnection(new Connection(p1, p3));
        traceApi.addConnection(new Connection(p1, p5));
        traceApi.addConnection(new Connection(p2, p4));
        traceApi.addConnection(new Connection(p2, p5));
        traceApi.addConnection(new Connection(p3, p4));
        traceApi.addConnection(new Connection(p3, p5));
        traceApi.addConnection(new Connection(p4, p5));

        ConnectionIterator baseConnectionIterator = new ConnectionIterator(p1.connectedTo(p2), true);

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

    @Test
    public void testConnectionIteratorFalse() {
        traceApi.addConnection(new Connection(p1, p2));
        traceApi.addConnection(new Connection(p1, p3));
        traceApi.addConnection(new Connection(p1, p5));
        traceApi.addConnection(new Connection(p2, p4));
        traceApi.addConnection(new Connection(p2, p5));
        traceApi.addConnection(new Connection(p3, p4));
        traceApi.addConnection(new Connection(p3, p5));
        traceApi.addConnection(new Connection(p4, p5));

        ConnectionIterator connectionIterator = new ConnectionIterator(p1.connectedTo(p2), false);

        Set<Connection> expectedConnections = new HashSet<>();
        expectedConnections.add(p1.connectedTo(p2));
        expectedConnections.add(p1.connectedTo(p3));
        expectedConnections.add(p1.connectedTo(p5));
        expectedConnections.add(p2.connectedTo(p4));
        expectedConnections.add(p2.connectedTo(p5));
        expectedConnections.add(p3.connectedTo(p4));
        expectedConnections.add(p3.connectedTo(p5));
        expectedConnections.add(p4.connectedTo(p5));
        expectedConnections.add(p1.connectedTo(p4));
        expectedConnections.add(p2.connectedTo(p3));

        Set<Connection> actualConnections = new HashSet<>();

        int count = 0;
        for (Connection connection; connectionIterator.hasNext(); ) {
            connection = connectionIterator.next();
            actualConnections.add(connection);
            count++;
        }
        assertEquals(expectedConnections, actualConnections);
        assertEquals(10, count);
    }
}