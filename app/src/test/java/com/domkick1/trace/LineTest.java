package com.domkick1.trace;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by domin_2o9sb4z on 2016-02-18.
 */
public class LineTest {

    private Point point1a;
    private Point point1b;
    private Point point2a;
    private Point point2b;
    private Point point3a;
    private Point point3b;
    private Point point4a;
    private Point point4b;

    private Line horizontal;
    private Line horCopy;
    private Line diagonal;
    private Line vertical;
    private Line line1;
    private Line line2;

    @Before
    public void setUp() {
        point1a = new Point(0, 3);
        point1b = new Point(6, 3);
        point2a = new Point(0, 3);
        point2b = new Point(6, 3);
        point3a = new Point(2, 2);
        point3b = new Point(5, 5);
        point4a = new Point(3, 7);
        point4b = new Point(3, 2);


        horizontal = new Line(point1a, point1b);
        horCopy = new Line(point2a, point2b);
        diagonal = new Line(point3a, point3b);
        vertical = new Line(point4a, point4b);
        line1 = new Line(point1a, point3b);
        line2 = new Line(point1b, point3b);
    }

    @Test
    public void testEquals() {
        assertTrue(horizontal.equals(horizontal));
        assertTrue(horizontal.equals(horCopy));
        assertTrue(!horizontal.equals(null));
        assertTrue(!horizontal.equals(diagonal));
    }

    @Test
    public void testIntersects() {
        // Horizontal
        assertTrue(horizontal.intersects(new Point(2, 3)));
        assertTrue(horizontal.intersects(point1a));
        assertTrue(horizontal.intersects(point1b));
        assertTrue(!horizontal.intersects(new Point(2, (float) 3.1)));

        //Vertical
        assertTrue(vertical.intersects(new Point(3, 5)));
        assertTrue(vertical.intersects(point4a));
        assertTrue(vertical.intersects(point4b));

        //Diagonal
        assertTrue(diagonal.intersects(point3a));
        assertTrue(diagonal.intersects(point3b));
        assertTrue(!diagonal.intersects(new Point(3, (float) 3.01)));
    }

    @Test
    public void testSquareContains() {
        // Horizontal line
        assertTrue(horizontal.squareContains(point1a));
        assertTrue(horizontal.squareContains(point1b));
        assertTrue(!horizontal.squareContains(new Point(3, (float) 3.1)));

        // Vertical Line
        assertTrue(vertical.squareContains(point4a));
        assertTrue(vertical.squareContains(point4b));
        assertTrue(!vertical.squareContains(new Point(3, (float) 7.1)));

        // Diagonal line
        assertTrue(diagonal.squareContains(new Point(3, 3)));
        assertTrue(!diagonal.squareContains(new Point(8, 8)));
    }

    @Test
    public void testGetSlope() {
        assertTrue(horizontal.getSlope() - 0 < 0.0000000001);
        assertTrue(vertical.getSlope() == Float.POSITIVE_INFINITY);
        assertTrue(diagonal.getSlope() - 1 < 0.0000000001);

    }

    @Test
    public void testIsTouching() {
        assertTrue(horizontal.isTouching(line1).equals(point1a));
        assertTrue(horizontal.isTouching(line2).equals(point1b));
        assertTrue(horizontal.isTouching(vertical) == null);
    }

    @Test
    public void testGetYIntercept() {
        assertTrue(horizontal.getYIntercept() == 3);
        assertTrue(diagonal.getYIntercept() == 0);
        assertTrue(line1.getYIntercept() == 3);
        assertTrue(line2.getYIntercept() == 15);
    }

    @Test
    public void testGetSimpleLines() {
        PointList points = new PointList(5);
        points.add(new Point(10, 10));
        points.add(new Point(20, 20));
        points.add(new Point(30, 30));
        points.add(new Point(30, 10));
        points.add(new Point(10, 30));

        // diagonal
        Line line = new Line(10, 10, 30, 30);
        LineList simpleLines = new LineList(2);
        simpleLines.add(new Line(10, 10, 20, 20));
        simpleLines.add(new Line(20, 20, 30, 30));
        assertTrue(simpleLines.equals(line.getSimpleLines(points)));

        // 2nd diagonal
        line = new Line(30, 10, 10, 30);
        simpleLines.clear();
        simpleLines.add(new Line(30, 10, 20, 20));
        simpleLines.add(new Line(20, 20, 10, 30));
        assertTrue(line.getSimpleLines(points).equals(simpleLines));

        // vertical
        line = new Line(10, 10, 10, 30);
        simpleLines.clear();
        simpleLines.add(new Line(10, 10, 10, 30));
        assertTrue(line.getSimpleLines(points).equals(simpleLines));
    }

}