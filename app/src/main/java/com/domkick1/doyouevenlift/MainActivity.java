package com.domkick1.doyouevenlift;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements NextLevelDialogFragment.NextLevelDialogListener {

    private CustomView drawView;

    public int currentLevel = 0;

    public static final int RADIUS = 80;

    public static ArrayList<float[]> levels = Levels.getLevels();

    private HashMap<Line,ArrayList<Line>> hashMap;
    private ArrayList<Float> shape = toList(levels.get(currentLevel));
    private ArrayList<Float> trace = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hashMap = generateMap(shape);
        for(int i = 0; i < shape.size(); i+= 4){
            generateMapForCompoundLines(hashMap,shape,new Line(shape.get(i),shape.get(i+1),shape.get(i+2),shape.get(i+3)));
        }
        //get local reference for canvas
        drawView = (CustomView) findViewById(R.id.draw_view);
        updateCanvas();

        drawView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //first touch
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Point touchPoint = isNearVertex(event);
                    if (touchPoint != null) {
                        trace.add(touchPoint.getX());
                        trace.add(touchPoint.getY());
                        trace.add(touchPoint.getX());
                        trace.add(touchPoint.getY());
                        updateCanvas();
                        return true;
                    }
                    return false;
                    //still touching
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Point touchPoint = isNearVertex(event);
                    if (touchPoint != null) {
                        ArrayList<Line> componentLines = isLineInShapeNew(touchPoint);
                        if (componentLines != null) {
                            if (!isOccupied(componentLines)) {
                                Point startPoint = new Point(trace.get(trace.size() - 4),trace.get(trace.size() - 3));
                                for(Line l: componentLines){
                                    if(l.getP1().equals(startPoint)){
                                        trace.set(trace.size() - 2, l.getP2().getX());
                                        trace.set(trace.size() - 1, l.getP2().getY());
                                        continue;
                                    }
                                    else if(l.getP2().equals(startPoint)){
                                        trace.set(trace.size() - 2, l.getP1().getX());
                                        trace.set(trace.size() - 1, l.getP1().getY());
                                        continue;
                                    }
                                    trace.add(l.getP1().getX());
                                    trace.add(l.getP1().getY());
                                    trace.add(l.getP2().getX());
                                    trace.add(l.getP2().getY());
                                }
                                trace.add(touchPoint.getX());
                                trace.add(touchPoint.getY());
                                trace.add(touchPoint.getX());
                                trace.add(touchPoint.getY());
                                updateCanvas();
                                return true;
                            }
                        }
                    }
                    trace.set(trace.size() - 2, event.getX());
                    trace.set(trace.size() - 1, event.getY());
                    updateCanvas();
                    return true;
                } else {
                    trace.remove(trace.size() - 1);
                    trace.remove(trace.size() - 1);
                    trace.remove(trace.size() - 1);
                    trace.remove(trace.size() - 1);
                    if (allLines(trace, shape))
                        //win
                        launchNextLevelDialog();
                    trace.clear();
                    updateCanvas();
                    return false;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private double getDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    private Point isNearVertex(MotionEvent event) {
        for(int i = 0; i < shape.size(); i+=4){
            if(getDistance(new Point(shape.get(i),shape.get(i + 1)), new Point(event.getX(),event.getY())) < RADIUS)
                return new Point(shape.get(i),shape.get(i+1));
            if(i + 4 == shape.size())
                i -=2;
        }
        return null;
    }

    private boolean isLineInShape(Point touchPoint){
        Point startPoint = new Point(trace.get(trace.size() - 4),trace.get(trace.size() - 3));
        return isLineInSet(shape, new Line(startPoint,touchPoint));
    }

    private ArrayList<Line> isLineInShapeNew(Point touchPoint){
        Point startPoint = new Point(trace.get(trace.size() - 4),trace.get(trace.size() - 3));
        Line line = new Line(startPoint,touchPoint);
        ArrayList<Line> getLine = hashMap.get(line);
        if(getLine == null)
            return null;
        return getLine;
    }
    //return true is the line has already been coloured
    //return false if this is a legal move
    private boolean isOccupied(ArrayList<Line> componentLines){
        for(Line l: componentLines){
            if(isLineInSet(trace, l))
                return true;
        }
        return false;
    }

    private boolean isLineInSet(ArrayList<Float> set, Line line){
        for(int i = 0; i < set.size(); i+=4){
            Point p1 = new Point(set.get(i),set.get(i+1));
            Point p2 = new Point(set.get(i+2),set.get(i+3));
            if(line.getP1().equals(p1) && line.getP2().equals(p2))
                return true;
            if(line.getP1().equals(p2) && line.getP2().equals(p1))
                return true;
        }
        return false;
    }
    // Returns true if all lines have been traced.
    private boolean allLines(ArrayList<Float> p1, ArrayList<Float> p2){
        return p1.size() == p2.size();
    }
    public void launchNextLevelDialog() {
        DialogFragment newFragment = new NextLevelDialogFragment();
        newFragment.show(getFragmentManager(), "next_level");
    }
    //clicks next level
    public void onDialogNextLevelClick(DialogFragment dialogFragment){
        if(levels.size() - 1 > currentLevel)
            currentLevel +=1;
        shape = toList(levels.get(currentLevel));
        hashMap = generateMap(shape);
        for(int i = 0; i < shape.size(); i+= 4){
            generateMapForCompoundLines(hashMap,shape,new Line(shape.get(i),shape.get(i+1),shape.get(i+2),shape.get(i+3)));
        }
        updateCanvas();
    }
    //clicks retry level
    public void onDialogRetryClick(DialogFragment dialogFragment){
        updateCanvas();
    }

    private void updateCanvas(){
        drawView.setShape(shape);
        drawView.setTrace(trace);
        drawView.invalidate();
    }

    //returns the line segment that starts at the same point has the same direction
    //returns null if no such line exists
    private ArrayList<Line> isCompoundLine(ArrayList<Float> set, Point starPoint, Point touchPoint){
        ArrayList<Line> lines = new ArrayList<Line>();
        Vector vector = new Vector(starPoint,touchPoint);
        for(int i = 0; i < set.size(); i+=4){
            Line l = new Line(set.get(i),set.get(i+1),set.get(i+2),set.get(i+3));
            if(!l.getP1().equals(starPoint) && !l.getP2().equals(starPoint))
                continue;
            if(l.getP1().equals(touchPoint) || l.getP2().equals(touchPoint)){
                lines.add(new Line(starPoint,touchPoint));
                return lines;
            }
            Vector tempVector = new Vector(l);
            if(!vector.getUnitVector().equals(tempVector.getUnitVector()))
                continue;
            lines.add(l);
            lines.addAll(isCompoundLine(set,new Point(set.get(i),set.get(i+1)),touchPoint));
            return lines;
        }
        return lines;
    }

    private HashMap<Line,ArrayList<Line>> generateMap(ArrayList<Float> set){
        HashMap<Line,ArrayList<Line>> map = new HashMap<Line,ArrayList<Line>>();
        for(int i = 0; i < set.size(); i+=4){
            Point p1 = new Point(set.get(i),set.get(i+1));
            Point p2 = new Point(set.get(i+2),set.get(i+3));
            Line l1 = new Line(p1,p2);
            Line l2 = new Line(p2,p1);
            ArrayList<Line> ls1 = new ArrayList<Line>();
            ls1.add(l1);
            map.put(l1,ls1);
            ArrayList<Line> ls2 = new ArrayList<Line>();
            ls2.add(l2);
            map.put(l2, ls2);
        }
        return map;
    }

    private void generateMapForCompoundLines(HashMap<Line, ArrayList<Line>> map, ArrayList<Float> set, Line baseLine){
        for(int i = 0; i < set.size(); i += 4){
            Line l = new Line(set.get(i),set.get(i + 1),set.get(i+2),set.get(i+3));
            Point commonPoint = baseLine.isTouching(l);
            if(commonPoint == null)
                continue; //not touching
            if(!baseLine.getP1().equals(commonPoint))
                baseLine = new Line(baseLine.getP2(),baseLine.getP1());
            if(!l.getP1().equals(commonPoint))
                l = new Line(l.getP2(),l.getP1());
            if(!new Vector(baseLine).getUnitVector().equals(new Vector(l).getUnitVector().getInverse()))
                continue; //not same direction/are overlapping
            Line compoundLine1 = new Line(baseLine,l);
            Line compoundLine2 = new Line(l,baseLine);
            ArrayList<Line> compoundLines = new ArrayList<Line>();
            compoundLines.addAll(map.get(l));
            compoundLines.addAll(map.get(baseLine));
            map.put(compoundLine1, compoundLines);
            map.put(compoundLine2,compoundLines);
            generateMapForCompoundLines(map, set, compoundLine1);
        }
        return;
    }

    private ArrayList<Float> toList(float[] a){
        ArrayList<Float> b = new ArrayList<Float>();
        for(float f:a){
            b.add(f);
        }
        return b;
    }
}