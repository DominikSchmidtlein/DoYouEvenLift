package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class ConnectionTest {

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
        c5 = p1.connectedTo(p3);
        c6 = p3.connectedTo(p5);
        c7 = p2.connectedTo(p4);
        c8 = p1.connectedTo(p4);
        c9 = p2.connectedTo(p5);
        c10 = p1.connectedTo(p5);
    }

    @Test
    public void testConstructorHorizontal() {
        assertEquals(p1.connectedTo(p2), c1);
        assertEquals(p1.connectedTo(p3), c5);
        assertEquals(p1.connectedTo(p4), c8);
        assertEquals(p1.connectedTo(p5), c10);
        assertEquals(p2.connectedTo(p3), c2);
        assertEquals(p2.connectedTo(p4), c7);
        assertEquals(p2.connectedTo(p5), c9);
        assertEquals(p3.connectedTo(p4), c3);
        assertEquals(p3.connectedTo(p5), c6);
        assertEquals(p4.connectedTo(p5), c4);
    }

    @Test
    public void testConstructorVertical() {
        assertTrue(c1.getSubConnections().isEmpty());
        assertTrue(c1.getSuperConnections().contains(c5));
        assertTrue(c1.getSuperConnections().contains(c8));
        assertTrue(c1.getSuperConnections().contains(c10));
        assertEquals(c1.getSuperConnections().size(), 3);

        assertTrue(c2.getSubConnections().isEmpty());
        assertTrue(c2.getSuperConnections().contains(c5));
        assertTrue(c2.getSuperConnections().contains(c7));
        assertTrue(c2.getSuperConnections().contains(c9));
        assertEquals(c2.getSuperConnections().size(), 3);

        assertTrue(c3.getSubConnections().isEmpty());
        assertTrue(c3.getSuperConnections().contains(c6));
        assertTrue(c3.getSuperConnections().contains(c7));
        assertTrue(c3.getSuperConnections().contains(c8));
        assertEquals(c3.getSuperConnections().size(), 3);

        assertTrue(c4.getSubConnections().isEmpty());
        assertTrue(c4.getSuperConnections().contains(c6));
        assertTrue(c4.getSuperConnections().contains(c9));
        assertTrue(c4.getSuperConnections().contains(c10));
        assertEquals(c4.getSuperConnections().size(), 3);

        assertTrue(c5.getSubConnections().contains(c1));
        assertTrue(c5.getSubConnections().contains(c2));
        assertEquals(c5.getSubConnections().size(), 2);
        assertTrue(c5.getSuperConnections().contains(c8));
        assertTrue(c5.getSuperConnections().contains(c10));
        assertEquals(c5.getSuperConnections().size(), 2);

        assertTrue(c6.getSubConnections().contains(c3));
        assertTrue(c6.getSubConnections().contains(c4));
        assertEquals(c6.getSubConnections().size(), 2);
        assertTrue(c6.getSuperConnections().contains(c9));
        assertTrue(c6.getSuperConnections().contains(c10));
        assertEquals(c6.getSuperConnections().size(), 2);

        assertTrue(c7.getSubConnections().contains(c2));
        assertTrue(c7.getSubConnections().contains(c3));
        assertEquals(c7.getSubConnections().size(), 2);
        assertTrue(c7.getSuperConnections().contains(c8));
        assertTrue(c7.getSuperConnections().contains(c9));
        assertEquals(c7.getSuperConnections().size(), 2);

        assertTrue(c8.getSubConnections().contains(c1));
        assertTrue(c8.getSubConnections().contains(c3));
        assertTrue(c8.getSubConnections().contains(c5));
        assertTrue(c8.getSubConnections().contains(c7));
        assertEquals(c8.getSubConnections().size(), 4);
        assertTrue(c8.getSuperConnections().contains(c10));
        assertEquals(c8.getSuperConnections().size(), 1);

        assertTrue(c9.getSubConnections().contains(c2));
        assertTrue(c9.getSubConnections().contains(c4));
        assertTrue(c9.getSubConnections().contains(c6));
        assertTrue(c9.getSubConnections().contains(c7));
        assertEquals(c9.getSubConnections().size(), 4);
        assertTrue(c9.getSuperConnections().contains(c10));
        assertEquals(c9.getSuperConnections().size(), 1);

        assertTrue(c10.getSubConnections().contains(c1));
        assertTrue(c10.getSubConnections().contains(c4));
        assertTrue(c10.getSubConnections().contains(c5));
        assertTrue(c10.getSubConnections().contains(c6));
        assertTrue(c10.getSubConnections().contains(c8));
        assertTrue(c10.getSubConnections().contains(c9));
        assertEquals(c10.getSubConnections().size(), 6);
        assertTrue(c10.getSuperConnections().isEmpty());
    }

    @Test
    public void testSetOccupied1() {
        c1.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isFree());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isFree());
        assertTrue(c7.isFree());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isFree());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied2() {
        c2.setOccupied();

        assertTrue(c1.isFree());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isFree());
        assertTrue(c7.isBlocked());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied5() {
        c5.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isFree());
        assertTrue(c4.isFree());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isFree());
        assertTrue(c7.isBlocked());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied7() {
        c7.setOccupied();

        assertTrue(c1.isFree());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isFree());
        assertTrue(c5.isBlocked());
        assertTrue(c6.isBlocked());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isBlocked());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied8() {
        c8.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isFree());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isBlocked());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isOccupied());
        assertTrue(c9.isBlocked());
        assertTrue(c10.isBlocked());
    }

    @Test
    public void testSetOccupied10() {
        c10.setOccupied();

        assertTrue(c1.isOccupied());
        assertTrue(c2.isOccupied());
        assertTrue(c3.isOccupied());
        assertTrue(c4.isOccupied());
        assertTrue(c5.isOccupied());
        assertTrue(c6.isOccupied());
        assertTrue(c7.isOccupied());
        assertTrue(c8.isOccupied());
        assertTrue(c9.isOccupied());
        assertTrue(c10.isOccupied());
    }

    @Test
    public void testEquals() {
        assertNotEquals(c1, c2);
        assertNotEquals(c1, c5);
        assertNotEquals(c1, c8);
        assertNotEquals(c1, c10);
        assertEquals(c1, c1);
        assertEquals(c1, new Connection(p1, p2));
        assertEquals(c1, new Connection(new TracePoint(100, 100), new TracePoint(200, 200)));
    }

    @Test
    public void testConnects() {
        assertFalse(c1.connects(p1, p1));
        assertTrue(c1.connects(p1, p2));
        assertTrue(c1.connects(p2, p1));
        assertTrue(c1.connects(p2, new TracePoint(100, 100)));
        assertTrue(c1.connects(new TracePoint(200, 200), p1));
        assertTrue(c1.connects(new TracePoint(200, 200), new TracePoint(100, 100)));
    }

    @Test
    public void testAddSuperConnection() {
        assertFalse(c1.addSuperConnection(c3));
        assertFalse(c1.addSuperConnection(c2));
        assertFalse(c8.addSuperConnection(c9));
        assertFalse(c5.addSuperConnection(c1));
        assertFalse(c10.addSuperConnection(c2));
        assertFalse(c1.addSuperConnection(c1));
        assertFalse(c2.addSuperConnection(c10));

        assertTrue(c1.addSuperConnection(c5));
        assertTrue(c1.addSuperConnection(c8));
        assertTrue(c1.addSuperConnection(c10));
    }

    @Test
    public void testOtherEnd() {
        assertEquals(c1.otherEnd(p1), p2);
        assertEquals(c1.otherEnd(p2), p1);
        assertEquals(c2.otherEnd(p2), p3);
        assertEquals(c2.otherEnd(p3), p2);
        assertEquals(c3.otherEnd(p3), p4);
        assertEquals(c3.otherEnd(p4), p3);
        assertEquals(c4.otherEnd(p4), p5);
        assertEquals(c4.otherEnd(p5), p4);
    }

    @Test
    public void testDirectionFrom() {
        assertEquals(c1.directionFrom(p1), new Point(0.70710678118, 0.70710678118));
        assertEquals(c1.directionFrom(p2), new Point(-0.70710678118, -0.70710678118));
    }

    @Test
    public void testConcat() {
        assertNull(c1.concat(c3));
        assertNull(c1.concat(c4));
        assertNull(c2.concat(c4));
        assertNull(c1.concat(c1));
        assertNull(c2.concat(c2));
        assertNull(c3.concat(c3));
        assertNull(c4.concat(c4));
        assertNull(c2.concat(c8));
        assertNull(c1.concat(c5));
        assertNull(c2.concat(c5));

        assertEquals(c1.concat(c2), c5);
        assertEquals(c2.concat(c3), c7);
        assertEquals(c4.concat(c3), c6);
        assertEquals(c1.concat(c2).concat(c3), c8);
        assertEquals(c2.concat(c3).concat(c1).concat(c4), c10);

    }

}
