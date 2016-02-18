package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */

public class PointTest {

    private Point point;
    private Point point1;
    private Point point2;
    private Point point3;

    @Before
    public void setUp() {
        point = new Point(2, 2);
        point1 = new Point(2, 2);
        point2 = new Point(2, 3);
        point3 = new Point(3, 3);
    }

    @Test
    public void testEquals() {
        assertTrue(point.equals(point));
        assertTrue(!point.equals(null));
        assertTrue(point.equals(point1));
        assertTrue(!point.equals(point2));
    }

    @Test
    public void testGetDistance() {
        assertTrue(point.getDistance(point2) - 2 < 0.000001);
        assertTrue(point.getDistance(point3) - Math.sqrt(2) < 0.000001);
    }

}
