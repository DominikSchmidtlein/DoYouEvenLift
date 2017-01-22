package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-12-04.
 */
public class TracePointTest {

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

    @Before
    public void setUp() {
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(200, 200);
        p3 = new TracePoint(300, 300);
        p4 = new TracePoint(400, 400);
        p5 = new TracePoint(500, 500);

        c1 = new Connection(p1, p2);
        c2 = new Connection(p2, p3);
        c3 = new Connection(p3, p4);
        c4 = new Connection(p4, p5);
        c5 = new Connection(p1, p3);
        c6 = new Connection(p3, p5);
        c7 = new Connection(p2, p4);
        c8 = new Connection(p1, p4);
        c9 = new Connection(p2, p5);
        c10 = new Connection(p1, p5);

        p1.addConnection(c1);
        p1.addConnection(c5);
        p1.addConnection(c8);
        p1.addConnection(c10);

        p2.addConnection(c1);
        p2.addConnection(c2);
        p2.addConnection(c7);
        p2.addConnection(c9);

        p3.addConnection(c2);
        p3.addConnection(c3);
        p3.addConnection(c5);
        p3.addConnection(c6);

        p4.addConnection(c3);
        p4.addConnection(c4);
        p4.addConnection(c7);
        p4.addConnection(c8);

        p5.addConnection(c4);
        p5.addConnection(c6);
        p5.addConnection(c9);
        p5.addConnection(c10);

        c1.addSuperConnection(c5);
        c1.addSuperConnection(c8);
        c1.addSuperConnection(c10);

        c2.addSuperConnection(c5);
        c2.addSuperConnection(c7);
        c2.addSuperConnection(c9);

        c3.addSuperConnection(c6);
        c3.addSuperConnection(c7);
        c3.addSuperConnection(c8);

        c4.addSuperConnection(c6);
        c4.addSuperConnection(c9);
        c4.addSuperConnection(c10);

        c5.addSuperConnection(c8);
        c5.addSuperConnection(c10);

        c6.addSuperConnection(c9);
        c6.addSuperConnection(c10);

        c7.addSuperConnection(c8);
        c7.addSuperConnection(c9);

        c8.addSuperConnection(c10);

        c9.addSuperConnection(c10);
    }

    @Test
    public void testConnectedTo() {
        assertNull(p1.connectedTo(p1));
        assertEquals(p1.connectedTo(p2), c1);
        assertEquals(p2.connectedTo(p1), c1);
        assertEquals(p2.connectedTo(new TracePoint(100, 100)), c1);
    }

    @Test
    public void testOn() {
        assertFalse(p1.on(new Connection(new TracePoint(100, 200), new TracePoint(200, 100))));
        assertFalse(p1.on(new Connection(new TracePoint(0, 0), new TracePoint(200, 100))));
        assertTrue(p1.on(c1));
        assertTrue(p1.on(new Connection(new TracePoint(0, 0), new TracePoint(200, 200))));
        assertTrue(p1.on(new Connection(new TracePoint(0, 200), new TracePoint(200, 0))));
        assertTrue(p1.on(new Connection(new TracePoint(100, 0), new TracePoint(100, 200))));
        assertTrue(p1.on(new Connection(new TracePoint(0, 100), new TracePoint(200, 100))));
    }

    @Test
    public void testBaseConnections() {
        Set<Connection> p1c = new HashSet<>();
        p1c.add(c1);
        assertEquals(p1c, p1.getBaseConnections());

        Set<Connection> p2c = new HashSet<>();
        p2c.add(c1);
        p2c.add(c2);
        assertEquals(p2c, p2.getBaseConnections());

        Set<Connection> p3c = new HashSet<>();
        p3c.add(c2);
        p3c.add(c3);
        assertEquals(p3c, p3.getBaseConnections());
    }

}