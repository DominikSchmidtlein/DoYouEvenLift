package com.domkick1.trace;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominikschmidtlein on 10/21/2015.
 */
public class TraceGame extends Trace {

    private static final int RADIUS = 80;

    private TraceGameJsonHelper gameJsonHelper;
    private LevelSaver levelSaver;

    private LineDictionary hashMap;
    private LineList shape;
    private LineList trace;

    private List<WinEventListener> winEventListeners;

    private LevelState levelState;

    public TraceGame(Context context, android.graphics.Point screenSize, int actionBarHeight, int level) {
        super(context, screenSize, actionBarHeight);
        gameJsonHelper = new TraceGameJsonHelper(context);
        levelSaver = new LevelSaver(context);
        levelState = levelSaver.loadState();
        winEventListeners = new ArrayList<>(2);
        toNextLevel();
    }

    @Override
    protected boolean handleTouch(View v, MotionEvent event) {
        if (event.getPointerCount() > 1)
            return false;

        Point touchPoint = new Point(event.getX(), event.getY());
        Point nearPoint = shape.isNearVertex(touchPoint, RADIUS);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (nearPoint != null)
                    trace.add(new Line(nearPoint, touchPoint));
                return nearPoint != null;
            case MotionEvent.ACTION_MOVE:
                if (nearPoint != null) {
                    Point startPoint = trace.get(trace.size() - 1).getP1();
                    LineList componentLines = hashMap.get(new Line(startPoint, nearPoint));
                    if (componentLines != null && !trace.isOccupied(componentLines)) {
                        trace.addLines(componentLines);
                        trace.add(new Line(nearPoint, touchPoint));
                        return true;
                    }
                }
                trace.replaceLastPoint(touchPoint);
                return true;
            default:
                trace.remove(trace.size() - 1);
                if (win())
                    notifyWinEventListeners();
                trace.clear();
                return false;
        }
    }

    public void setWinEventListener(WinEventListener eventListener) {
        winEventListeners.add(eventListener);
    }

    private void notifyWinEventListeners() {
        for (WinEventListener listener : winEventListeners)
            listener.onWin(new WinEvent(this));
    }

    public boolean toNextLevel() {
        levelState.levelPlayed();
        levelState.nextLevel();
        setupLevel();
        return true;
    }

    private void setupLevel() {
        shape = gameJsonHelper.getLevelAsLines(levelState.getCurrentLevel(), size.x, size.y, actionBarHeight);
        trace = new LineList(shape.size());
        hashMap = new LineDictionary(shape);
        setChanged();
        notifyObservers();
    }

    /**
     * Return true if the level is complete, which occurs when all lines have been drawn.
     *
     * @return true for win, false for loss
     */
    private boolean win() {
        return shape.size() == trace.size();
    }

    public ArrayList<Line> getShape() {
        return shape;
    }

    public ArrayList<Line> getTrace() {
        return trace;
    }

    private void loadLevels(){

    }
}
