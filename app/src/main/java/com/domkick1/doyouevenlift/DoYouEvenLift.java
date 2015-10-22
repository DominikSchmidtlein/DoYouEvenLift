package com.domkick1.doyouevenlift;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by dominikschmidtlein on 10/21/2015.
 */
public class DoYouEvenLift {
    public static final int RADIUS = 80;
    public static final String tag = "DoYouEvenLift";

    private HashMap<Line,ArrayList<Line>> hashMap;
    private MainActivity mainActivity;
    private CustomView drawView;
    private ArrayList<Line> shape;
    private ArrayList<Line> trace;

    private int currentLevel;
    public static ArrayList<float[]> levels;

    public DoYouEvenLift(){
        levels = Levels.getLevels();
        currentLevel = 0;
        setupLevel(currentLevel);

    }
    public void addListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        drawView = this.mainActivity.getDrawView();
        drawView.updateCanvas();
    }

    public boolean click(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Point touchPoint = isNearVertex(new Point(event.getX(),event.getY()));
            if (touchPoint != null) {
                trace.add(new Line(new Point(touchPoint),new Point(touchPoint)));
                drawView.updateCanvas();
                return true;
            }
            return false;
            //still touching
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Point touchPoint = isNearVertex(new Point(event.getX(),event.getY()));
            if (touchPoint != null) {
                Point startPoint = new Point(trace.get(trace.size() - 1).getP1());
                ArrayList<Line> componentLines = isLineInShapeNew(new Line(startPoint, touchPoint));
                if (componentLines != null) {
                    if (!isOccupied(componentLines)) {
                         for(Line l: componentLines){
                            if(l.getP1().equals(startPoint)){
                                trace.set(trace.size() - 1,new Line(l));
                                continue;
                            }
                             trace.add(new Line(l));
                        }
                        trace.add(new Line(new Point(touchPoint), new Point(touchPoint)));
                        drawView.updateCanvas();
                        return true;
                    }
                }
            }
            trace.get(trace.size() - 1).setP2(new Point(event.getX(), event.getY()));
            drawView.updateCanvas();
            return true;
        } else {
            trace.remove(trace.size() - 1);
            if (allLines(trace, shape))
                //win
                mainActivity.launchNextLevelDialog();
            trace.clear();
            drawView.updateCanvas();
            return false;
        }
    }

    public ArrayList<Line> getTrace() {
        return trace;
    }

    public float[] getTraceAsFloats(){
        return linesAsFloats(trace);
    }

    public float[] getShapeAsFloats(){
        return linesAsFloats(shape);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean incrementCurrentLevel(){
        if(currentLevel >= levels.size() - 1)
            return false;
        currentLevel ++;
        setupLevel(currentLevel);
        drawView.updateCanvas();
        return true;
    }

    private void setupLevel(int currentLevel){
        shape = floatsAsLines(levels.get(currentLevel));
        Log.d(tag, "setuplevel->shape.size");
        trace = new ArrayList<>(shape.size());
        hashMap = generateMap(shape);
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public float[] linesAsFloats(ArrayList<Line> lines){
        float[] floats = new float[lines.size() * 4];
        int i = 0;
        for(Line line: lines){
            floats[i + 0] = line.getP1().getX();
            floats[i + 1] = line.getP1().getY();
            floats[i + 2] = line.getP2().getX();
            floats[i + 3] = line.getP2().getY();
            i += 4;
        }
        return floats;
    }

    public ArrayList<Line> floatsAsLines(float[] floats){
        ArrayList<Line> lines = new ArrayList<>(floats.length/4);
        for(int i = 0; i < floats.length; i += 4)
            lines.add(new Line(floats[i], floats[i + 1], floats[i + 2], floats[i + 3]));
        return lines;
    }

    public ArrayList<Line> getShape() {
        return shape;
    }

    public void setShape(ArrayList<Line> shape) {
        this.shape = shape;
    }

    public void setTrace(ArrayList<Line> trace) {
        this.trace = trace;
    }

    private double getDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    private Point isNearVertex(Point point) {
        for(Line line: shape){
            if(getDistance(line.getP1(),point) < RADIUS)
                return new Point(line.getP1());
            if(getDistance(line.getP2(),point) < RADIUS)
                return new Point(line.getP2());
        }
        return null;
    }

    private boolean isLineInShape(Line line){
        return isLineInSet(shape, line);
    }

    private ArrayList<Line> isLineInShapeNew(Line line){
        return hashMap.get(line);
    }
    //return true is the line has already been coloured
    //return false if this is a legal move
    private boolean isOccupied(ArrayList<Line> componentLines){
        for(Line l: componentLines)
            if(isLineInSet(trace, l))
                return true;
        return false;
    }

    private boolean isLineInSet(ArrayList<Line> set, Line line){
        for(Line l: set)
            if(l.equals(line) || l.getOpposite().equals(line))
                return true;
        return false;
    }
    // Returns true if all lines have been traced.
    private boolean allLines(ArrayList<Line> p1, ArrayList<Line> p2){
        return p1.size() == p2.size();
    }

    /*//returns the line segment that starts at the same point has the same direction
    //returns null if no such line exists
    private ArrayList<Line> isCompoundLine(ArrayList<Line> set, Line line){
        ArrayList<Line> lines = new ArrayList<Line>();
        Vector vector = new Vector(line);
        for(Line l: set){
            if(!l.getP1().equals(line.getP1()) && !l.getP2().equals(line.getP1()))
                continue;
            if(l.equals(line) || l.equals(line.getOpposite())){
                lines.add(new Line(starPoint,touchPoint));
                return lines;
            }
            Vector tempVector = new Vector(l);
            if(!vector.getUnitVector().equals(tempVector.getUnitVector()))
                continue;
            lines.add(l);
            lines.addAll(isCompoundLine(set, new Point(set.get(i), set.get(i + 1)), touchPoint));
            return lines;
        }
        return lines;
    }*/

    private HashMap<Line,ArrayList<Line>> generateMap(ArrayList<Line> set){
        HashMap<Line,ArrayList<Line>> map = new HashMap<Line,ArrayList<Line>>();
        for(Line line: set){
            ArrayList<Line> ls1 = new ArrayList<Line>();
            ls1.add(new Line(line));
            map.put(new Line(line),ls1);
            ArrayList<Line> ls2 = new ArrayList<Line>();
            ls2.add(line.getOpposite());
            map.put(line.getOpposite(), ls2);
        }
        generateMapForCompoundLines(map, new LinkedList<>(set));
        return map;
    }

    private void generateMapForCompoundLines(HashMap<Line, ArrayList<Line>> map, Queue<Line> queue){
        while(!queue.isEmpty()) {
            Line baseLine = queue.poll();
            ArrayList<Line> set = new ArrayList<Line>(map.keySet());
            for (Line line : set) {
                Point commonPoint = baseLine.isTouching(line);
                if (commonPoint == null)
                    continue; //not touching
                if (!baseLine.getP1().equals(commonPoint))
                    baseLine = baseLine.getOpposite();
                if (!line.getP1().equals(commonPoint))
                    line = line.getOpposite();
                if (!new Vector(baseLine).getUnitVector().equals(new Vector(line).getUnitVector().getInverse()))
                    continue; //not same direction/are overlapping
                Line compoundLine1 = new Line(baseLine.getP2(), line.getP2());
                Line compoundLine2 = new Line(line.getP2(), baseLine.getP2());
                ArrayList<Line> subLines1 = new ArrayList<Line>();
                ArrayList<Line> subLines2 = new ArrayList<Line>();
                subLines1.addAll(map.get(baseLine.getOpposite()));
                subLines1.addAll(map.get(line));
                subLines2.addAll(map.get(line.getOpposite()));
                subLines2.addAll(map.get(baseLine));
                map.put(compoundLine1, subLines1);
                map.put(compoundLine2, subLines2);
                queue.add(compoundLine1);
                queue.add(compoundLine2);
                Log.d("DoYouEvenLift", "added to queue, length: " + queue.size());
            }
        }
    }
}
