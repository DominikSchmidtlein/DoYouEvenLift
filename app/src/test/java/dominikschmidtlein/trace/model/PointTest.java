package dominikschmidtlein.trace.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by domin_2o9sb4z on 2016-12-18.
 */
public class PointTest {

    Point point1, point2;

    @Before
    public void setUp() {
        point1 = new Point(100, 100);
        point2 = new Point(200, 100);
    }

    @Test
    public void testIsOppositeOrigin() {
        assertFalse(point1.isOppositeOrigin(point2));
        assertTrue(point1.isOppositeOrigin(new Point(-100, -100)));
        assertTrue(point2.isOppositeOrigin(new Point(-200, -100)));
        assertTrue(new Point(-100, -100).isOppositeOrigin(point1));
        assertTrue(new Point(-200, -100).isOppositeOrigin(point2));
    }

    @Test
    public void testDistanceTo() {
        assertEquals(point1.distanceTo(point1), 0, 0.0000001);
        assertEquals(point1.distanceTo(point2), 100, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(100, 150)), 50, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(100, 50)), 50, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(50, 100)), 50, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(150, 100)), 50, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(50, 50)), 70.7106781187, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(50, 150)), 70.7106781187, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(150, 50)), 70.7106781187, 0.0000001);
        assertEquals(point1.distanceTo(new TracePoint(150, 150)), 70.7106781187, 0.0000001);
    }

    @Test
    public void testUnitMagnitude() {
        assertEquals(point1.unitMagnitude(), new Point(0.70710678118, 0.70710678118));
        assertEquals(new Point(-100, -100).unitMagnitude(), new Point(-0.70710678118, -0.70710678118));
        assertEquals(new Point(100, -100).unitMagnitude(), new Point(0.70710678118, -0.70710678118));
        assertEquals(new Point(-100, 100).unitMagnitude(), new Point(-0.70710678118, 0.70710678118));
    }

    @Test
    public void testEquals() {
        assertNotEquals(point1, null);
        assertNotEquals(point1, point2);
        assertEquals(point1, new TracePoint(100, 100));
        assertEquals(point1, point1);
    }
}