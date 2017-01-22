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

    Set<Connection> expectedConnections = new HashSet<>();
    Set<Connection> actualConnections = new HashSet<>();
    ConnectionIterator connectionIterator;

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        c1 = new Connection(p1, p2);
        p1.addConnection(c1);
        p2.addConnection(c1);
        c2 = new Connection(p1, p3);
        p1.addConnection(c2);
        p3.addConnection(c2);
        c3 = new Connection(p1, p5);
        p1.addConnection(c3);
        p5.addConnection(c3);
        c4 = new Connection(p2, p4);
        p2.addConnection(c4);
        p4.addConnection(c4);
        c5 = new Connection(p2, p5);
        p2.addConnection(c5);
        p5.addConnection(c5);
        c6 = new Connection(p3, p4);
        p3.addConnection(c6);
        p4.addConnection(c6);
        c7 = new Connection(p3, p5);
        p3.addConnection(c7);
        p5.addConnection(c7);
        c8 = new Connection(p4, p5);
        p4.addConnection(c8);
        p5.addConnection(c8);
        c9 = new Connection(p1, p4);
        p1.addConnection(c9);
        p4.addConnection(c9);
        c10 = new Connection(p2, p3);
        p2.addConnection(c10);
        p3.addConnection(c10);

        c3.addSuperConnection(c9);
        c8.addSuperConnection(c9);
        c5.addSuperConnection(c10);
        c7.addSuperConnection(c10);

        expectedConnections.add(c1);
        expectedConnections.add(c2);
        expectedConnections.add(c3);
        expectedConnections.add(c4);
        expectedConnections.add(c5);
        expectedConnections.add(c6);
        expectedConnections.add(c7);
        expectedConnections.add(c8);
    }

    @Test
    public void testConnectionIteratorTrue() {

        connectionIterator = new ConnectionIterator(p1.connectedTo(p2), true);

        int count = traverseIterator();
        assertEquals(expectedConnections, actualConnections);
        assertEquals(8, count);
    }

    @Test
    public void testConnectionIteratorFalse() {

        connectionIterator = new ConnectionIterator(p1.connectedTo(p2), false);

        expectedConnections.add(c9);
        expectedConnections.add(c10);

        int count = traverseIterator();
        assertEquals(expectedConnections, actualConnections);
        assertEquals(10, count);
    }

    private int traverseIterator() {
        int count = 0;
        for (Connection connection; connectionIterator.hasNext(); ) {
            connection = connectionIterator.next();
            actualConnections.add(connection);
            count++;
        }
        return count;
    }
}