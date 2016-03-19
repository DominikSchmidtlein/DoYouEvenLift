package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class PointListTest {

    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    private PointList points;
    private PointList sortedPoints;

    @Before
    public void setUp() {
        points = new PointList(4);
        points.add(point3 = new Point(300, 300));
        points.add(point2 = new Point(200, 200));
        points.add(point4 = new Point(400, 400));
        points.add(point1 = new Point(100, 100));

        sortedPoints = new PointList(points.size());
        sortedPoints.add(point1);
        sortedPoints.add(point2);
        sortedPoints.add(point3);
        sortedPoints.add(point4);
    }

    @Test
    public void testToFloatArray() throws Exception {
        assertTrue(Arrays.equals(sortedPoints.toFloatArray(),
                new float[]{100, 100, 200, 200, 300, 300, 400, 400}
        ));
    }

    @Test
    public void testIsNearVertex() throws Exception {
        assertTrue(points.isNearVertex(new Point(280, 280), 80).equals(point3));
    }

    @Test
    public void testSortDistanceToPoint() throws Exception {
        assertTrue(!points.equals(sortedPoints));
        points.sortDistanceToPoint(point1);
        assertTrue(points.equals(sortedPoints));
    }

    @Test
    public void testGetPointsOnLine() throws Exception {
        PointList pointsOnLine = points.getPointsOnLine(new Line(point2, point4));

        PointList correctPoints = new PointList();
        correctPoints.add(point2);
        correctPoints.add(point3);
        correctPoints.add(point4);

        correctPoints.sortDistanceToPoint(point1);
        pointsOnLine.sortDistanceToPoint(point1);

        assertTrue(pointsOnLine.equals(correctPoints));
    }

    @Test
    public void testGetOffsets() throws Exception {
        assertTrue(sortedPoints.getOffsets(new ScreenDimensions(500, 600, 100)).equals(new Point(0, 0)));
        assertTrue(sortedPoints.getOffsets(new ScreenDimensions(700, 800, 100)).equals(new Point(100, 100)));
    }
}
