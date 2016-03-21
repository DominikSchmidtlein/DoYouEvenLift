package com.domkick1.trace;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

/**
 * Created by domin_2o9sb4z on 2016-02-17.
 */
public class LineList extends ArrayList<Line> {

    public LineList() {
        super();
    }

    public LineList(int size) {
        super(size);
    }

    public LineList(Collection<Line> collection) {
        super(collection);
    }

    public LineList(float[] floats) {
        super(floats.length / 4);
        for (int i = 0; i < floats.length; i += 4)
            add(new Line(floats[i], floats[i + 1], floats[i + 2], floats[i + 3]));
    }

    public LineList(JSONArray jsonArray, ScreenDimensions dim) {
        this(new PointList(jsonArray).getCenteredPoints(dim), false);
    }

    /**
     * Creates a list of lines from a list of points. If usePointsTwice is true, then the end of one
     * line will be used as the start of the next. Otherwise, the lines will be created from pairs
     * of separate points. Therefore if usePointsTwice is true the new lines will be (a,b), (b,c),
     * (c,d)... However if usePointsTwice is false, the new lines will be (a,b), (c,d), (e,f)...
     *
     * @param points         a list of points
     * @param usePointsTwice if true, successive lines include same points, else not
     */
    public LineList(PointList points, boolean usePointsTwice) {
        super(usePointsTwice ? points.size() - 1 : points.size() / 2);
        int increment = usePointsTwice ? 1 : 2;

        for (int i = 0; i < points.size() - 1; i += increment)
            add(new Line(points.get(i), points.get(i + 1)));
    }

    public JSONArray toJsonArray() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Line line : this) {
                jsonArray.put(line.getP1().getX());
                jsonArray.put(line.getP1().getY());
                jsonArray.put(line.getP2().getX());
                jsonArray.put(line.getP2().getY());
            }
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    public float[] toFloatArray() {
        float[] floats = new float[size() * 4];
        int i = 0;
        for (Line line : this) {
            floats[i] = line.getP1().getX();
            floats[i + 1] = line.getP1().getY();
            floats[i + 2] = line.getP2().getX();
            floats[i + 3] = line.getP2().getY();
            i += 4;
        }
        return floats;
    }

    /**
     * Replaces point 2 of the last line in this array with the given point.
     *
     * @param point the new point
     */
    public void replaceLastPoint(Point point) {
        set(size() - 1, new Line(get(size() - 1).getP1(), point));
    }

    /**
     * Returns the point from this, if touchPoint is within radius of any vertices in this. Otherwise
     * returns null.
     *
     * @param touchPoint a point which may or may not be near vertices in this
     * @param radius     the maximum distance for point to be considered near a vertex in this
     * @return returns a near point from this, or null if none exist
     */
    public Point isNearVertex(Point touchPoint, int radius) {
        Point nearPoint;
        for (Line line : this)
            if ((nearPoint = new PointList(line).isNearVertex(touchPoint, radius)) != null)
                return nearPoint;
        return null;
    }

    /**
     * Checks if a line is in this, regardless of order of points.
     *
     * @param line line to be searched for
     * @return true of set contains line as forwards or backwards
     */
    public boolean aDirectionalContains(Line line) {
        return contains(line) || contains(line.getOpposite());
    }

    /**
     * Returns a list of lines where all instances of duplicates are removed. Therefore if there
     * were 2 instances of lineA, there will be none in the return list.
     *
     * @return a list free of all instances that were once duplicates
     */
    public LineList getWithoutDuplicates() {
        LineList noDuplicateLines = new LineList(size());
        for (ListIterator<Line> i = listIterator(); i.hasNext(); ) {
            Line line = i.next();
            i.remove();
            if (!aDirectionalContains(line))
                noDuplicateLines.add(line);
            i.add(line);
        }
        return noDuplicateLines;
    }

    /**
     * Returns all points that have an odd number of occurrences in this.
     *
     * @return a list of points that occur an odd number of times.
     */
    public PointList getOddlyOccurringPoints() {
        PointList problemPoints = new PointList(size());
        for (Line line : this)
            for (Point point : line)
                if (!problemPoints.remove(point))
                    problemPoints.add(point);
        return problemPoints;
    }

    /**
     * Updates the last line of trace, and appends the remaining lines  to trace.
     *
     * @param lines a sorted list of lines where the first point of lines[0] matches startpoint
     */
    public void addLines(LineList lines) {
        remove(size() - 1);
        addAll(lines);
    }

    /**
     * Returns true if this contains a line segment that overlaps with the prospective lines in
     * componentLines.
     *
     * @param componentLines all the lines that make up the potential new line
     * @return true if there is overlap, false otherwise
     */
    public boolean isOccupied(LineList componentLines) {
        if (componentLines == null)
            return false;
        for (Line l : componentLines)
            if (aDirectionalContains(l))
                return true;
        return false;
    }

    public int getPointCount(Point point) {
        int count = 0;
        for (Line line : this)
            for (Point p : line)
                if (p.equals(point))
                    count++;
        return count;
    }

    /**
     * Returns a list of lines where simple lines are combined into single larger lines where
     * possible.
     *
     * @return a list of lines where multiple simple lines in the same direction are simplified into
     * one line
     */
    public LineList getConnectedLines() {
        Queue<Line> queue = new LinkedList<>(this);
        LineList connectedLines = new LineList(this);

        while (!queue.isEmpty()) {
            Line testLine = queue.poll();
            for (Line line : connectedLines) {
                Point commonPoint = testLine.isTouching(line);
                if (connectedLines.getPointCount(commonPoint) != 2)
                    continue;
                if (!testLine.getP1().equals(commonPoint))
                    testLine = testLine.getOpposite();
                if (!line.getP1().equals(commonPoint))
                    line = line.getOpposite();
                if (!new Vector(testLine).getUnitVector().equals(new Vector(line).getUnitVector().getInverse()))
                    continue;

                Line newLine = new Line(testLine.getP2(), line.getP2());
                connectedLines.add(newLine);
                connectedLines.remove(testLine);
                connectedLines.remove(testLine.getOpposite());
                connectedLines.remove(line);
                connectedLines.remove(line.getOpposite());
                queue.remove(testLine);
                queue.remove(testLine.getOpposite());
                queue.remove(line);
                queue.remove(line.getOpposite());
                queue.add(newLine);
                break;
            }
        }
        return connectedLines;
    }
}
