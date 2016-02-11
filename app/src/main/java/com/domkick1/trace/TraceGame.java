package com.domkick1.trace;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by dominikschmidtlein on 10/21/2015.
 */
public class TraceGame extends Trace {
    public static final int RADIUS = 80;
    public static final String NAME = "TraceGame";


    private HashMap<Line, ArrayList<Line>> hashMap;
    private ArrayList<Line> shape;
    private ArrayList<Line> trace;

    private List<WinEventListener> winEventListeners;

    private int currentLevel;

    public TraceGame(android.graphics.Point screenSize, int actionBarHeight, int level) {
        super(screenSize, actionBarHeight);
        winEventListeners = new ArrayList<>(2);
        this.currentLevel = level;
        setupLevel();

    }

    @Override
    protected boolean handleTouch(View v, MotionEvent event) {
        if (event.getPointerCount() > 1)
            return false;

        Point touchPoint = new Point(event.getX(), event.getY());
        Point nearPoint = isNearVertexInLines(touchPoint, shape, RADIUS);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (nearPoint != null)
                    trace.add(new Line(nearPoint, touchPoint));
                return nearPoint != null;
            case MotionEvent.ACTION_MOVE:
                if(nearPoint != null) {
                    Point startPoint = new Point(trace.get(trace.size() - 1).getP1());
                    ArrayList<Line> componentLines = isLineInShape(new Line(startPoint, nearPoint));
                    if (componentLines != null && !isOccupied(componentLines)) {
                        addLinesToTrace(startPoint, componentLines);
                        trace.add(new Line(nearPoint, touchPoint));
                        return true;
                    }
                }
                replaceLastPoint(touchPoint, trace);
                return true;
            default:
                trace.remove(trace.size()-1);
                if (win())
                    notifyWinEventListeners();
                trace.clear();
                return false;
        }
    }

    private void addLinesToTrace(Point startPoint, List<Line> lines) {
        trace.set(trace.size() - 1, lines.get(0));
        for (Line line : lines)
            if (!line.contains(startPoint))
                trace.add(line);
    }

    public void setWinEventListener(WinEventListener eventListener){
        winEventListeners.add(eventListener);
    }

    private void notifyWinEventListeners(){
        for(WinEventListener listener : winEventListeners)
            listener.onWin(new WinEvent(this));
    }

    public ArrayList<Line> getShape() {
        return shape;
    }

    public ArrayList<Line> getTrace() {
        return trace;
    }

    public boolean incrementCurrentLevel() {
        if (currentLevel >= Levels.LEVELS.length - 1)
            return false;
        currentLevel ++;
        setupLevel();
        return true;
    }

    private void setupLevel() {
        shape = Levels.getLevelAsLines(currentLevel, size.x, size.y, actionBarHeight);
        trace = new ArrayList<>(shape.size());
        hashMap = generateMap();
        setChanged();
        notifyObservers();
    }

    public void setTrace(ArrayList<Line> trace) {
        this.trace = trace;
    }

    private ArrayList<Line> isLineInShape(Line line) {
        return (line.getP1() == null || line.getP2() == null) ? null : hashMap.get(line);
    }

    /**
     * Returns true if trace contains a line segment that overlaps with the prospective lines in
     * componentLines.
     *
     * @param componentLines all the lines that make up the potential new line
     * @return true if there is overlap, false otherwise
     */
    private boolean isOccupied(ArrayList<Line> componentLines) {
        for (Line l : componentLines)
            if (isLineInSet(trace, l))
                return true;
        return false;
    }

    /**
     * Return true if the level is complete, which occurs when all lines have been drawn.
     *
     * @return true for win, false for loss
     */
    private boolean win() {
        return shape.size() == trace.size();
    }

    /**
     * Generates a hashmap where all lines, simple and component, are keys and their value is an
     * array of component lines. The order of the components(value) is consistent with the line(key),
     * i.e. if the line starts at pa and extends to pb, then p1 of components[0] will be pa and p2
     * of components[n-1] will be pb.
     * In practice, if a given line exists, the map will return its components as simple lines. If
     * the line does not exist, the map will return null.
     *
     * @return a map of all simple and compound lines with their components as value pairs
     */
    private HashMap<Line, ArrayList<Line>> generateMap() {
        HashMap<Line, ArrayList<Line>> map = addShapeToMap();
        Log.d("Trace", "Done add shape");
        addCompoundLinesToMap(map);
        Log.d("Trace", "Done add compound");
        return map;
    }

    /**
     * Adds all the lines from shape to the map, and sets the line itself as its only component.
     *
     * @return a map containing all simple lines and their 1 component
     */
    private HashMap<Line, ArrayList<Line>> addShapeToMap() {
        HashMap<Line, ArrayList<Line>> map = new HashMap<Line, ArrayList<Line>>();
        ArrayList<Line> components;
        for (Line line : shape) {

            components = new ArrayList<Line>();
            components.add(line);
            map.put(line, components);

            components = new ArrayList<Line>();
            components.add(line.getOpposite());
            map.put(line.getOpposite(), components);
        }
        return map;
    }

    /**
     * Checks each line in shape to see if it is part of a compound line. If so, the new line is
     * added to the map and added to the queue to be searched for compound lines.
     *
     * @param map the partially completed map which will contain all lines and their components at
     *            the end of the method
     */
    private void addCompoundLinesToMap(HashMap<Line, ArrayList<Line>> map) {
        Queue<Line> queue = new LinkedList<Line>(shape);
        Line testLine;
        Point commonPoint;
        ArrayList<Line> simpleAndCompoundLines;

        while (!queue.isEmpty()) {
            testLine = queue.poll();
            simpleAndCompoundLines = new ArrayList<Line>(map.keySet());
            for (Line line : simpleAndCompoundLines) {
                if ((commonPoint = testLine.isTouching(line)) == null)
                    continue; //not touching
                if (!testLine.getP1().equals(commonPoint))
                    testLine = testLine.getOpposite();
                if (!line.getP1().equals(commonPoint))
                    line = line.getOpposite();
                if (!new Vector(testLine).getUnitVector().equals(new Vector(line).getUnitVector().getInverse()))
                    continue; //not same direction or are overlapping

                queue.add(insertCompoundLineInMap(testLine, line, map));
            }
        }
    }


    /**
     * Takes 2 lines which are known to be part of a component line. It is also known that they are
     * connect at their respective p1's. Method creates the compound line at its components, and
     * then adds it to the map.
     *
     * @param l1  one of two lines that make the compound line
     * @param l2  two of two lines that make the compound line
     * @param map a map of all lines and compound lines where the key is the line and the value is
     *            the components (1..*)
     * @return the new component line with l1.p2 as its p1 and l2.p2 as p2
     */
    private Line insertCompoundLineInMap(Line l1, Line l2, HashMap<Line, ArrayList<Line>> map) {
        Line compoundLine1 = new Line(l1.getP2(), l2.getP2());
        Line compoundLine2 = new Line(l2.getP2(), l1.getP2());

        ArrayList<Line> components1 = new ArrayList<Line>();
        ArrayList<Line> components2 = new ArrayList<Line>();

        components1.addAll(map.get(l1.getOpposite()));
        components1.addAll(map.get(l2));
        components2.addAll(map.get(l2.getOpposite()));
        components2.addAll(map.get(l1));

        map.put(compoundLine1, components1);
        map.put(compoundLine2, components2);

        return compoundLine1;
    }
}
