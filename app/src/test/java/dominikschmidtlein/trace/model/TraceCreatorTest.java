package dominikschmidtlein.trace.model;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2017-01-09.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class TraceCreatorTest {
    TraceCreator traceCreator;
    TracePoint p1;
    TracePoint p2;
    TracePoint p3;
    TracePoint p4;
    TracePoint p5;
    Connection c12;
    Connection c13;
    Connection c14;
    Connection c15;
    Connection c23;
    Connection c24;
    Connection c25;
    Connection c34;
    Connection c35;
    Connection c45;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Log.class);
//        traceCreator = new TraceCreator();
        p1 = new TracePoint(100, 100);
        p2 = new TracePoint(100, 200);
        p3 = new TracePoint(200, 100);
        p4 = new TracePoint(200, 200);
        p5 = new TracePoint(150, 150);

        c12 = new Connection(p1, p2);
        c13 = new Connection(p1, p3);
        c14 = new Connection(p1, p4);
        c15 = new Connection(p1, p5);
        c23 = new Connection(p2, p3);
        c24 = new Connection(p2, p4);
        c25 = new Connection(p2, p5);
        c34 = new Connection(p3, p4);
        c35 = new Connection(p3, p5);
        c45 = new Connection(p4, p5);

        traceCreator.addConnection(c12);
        traceCreator.addConnection(c13);
        traceCreator.addConnection(c24);
        traceCreator.addConnection(c34);
    }

    public void checkTrace() {
        assertEquals(p1.connectedTo(p2), c12);
        assertEquals(p1.connectedTo(p3), c13);
        assertEquals(p1.connectedTo(p4), c14);
        assertEquals(p1.connectedTo(p5), c15);
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertEquals(p4.connectedTo(p5), c45);
    }

    @Test
    public void testAddConnectionSuper() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointPThenC() {
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectPointCThenP() {
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);

        checkTrace();
    }

    @Test
    public void testAddConnectionIntersectConnection() {
        traceCreator.addConnection(c14);
        traceCreator.addConnection(c23);

        checkTrace();
    }

    @Test
    public void testRemoveConnection() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        traceCreator.removeConnection(c12);

        assertNull(p1.connectedTo(p2));
        assertEquals(p1.connectedTo(p3), c13);
        assertEquals(p1.connectedTo(p4), c14);
        assertEquals(p1.connectedTo(p5), c15);
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertEquals(p4.connectedTo(p5), c45);
    }

    @Test
    public void testRemoveConnectionSuper() {
        traceCreator.addConnection(c15);
        traceCreator.addConnection(c25);
        traceCreator.addConnection(c35);
        traceCreator.addConnection(c45);

        traceCreator.removeConnection(c14);

        assertEquals(p1.connectedTo(p2), c12);
        assertEquals(p1.connectedTo(p3), c13);
        assertNull(p1.connectedTo(p4));
        assertNull(p1.connectedTo(p5));
        assertEquals(p2.connectedTo(p3), c23);
        assertEquals(p2.connectedTo(p4), c24);
        assertEquals(p2.connectedTo(p5), c25);
        assertEquals(p3.connectedTo(p4), c34);
        assertEquals(p3.connectedTo(p5), c35);
        assertNull(p4.connectedTo(p5));
    }

}