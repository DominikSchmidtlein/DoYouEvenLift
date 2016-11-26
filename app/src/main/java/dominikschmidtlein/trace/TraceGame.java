package dominikschmidtlein.trace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominikschmidtlein on 10/21/2015.
 */
public class TraceGame extends Trace {

    private static final int RADIUS = 80;

    private LineDictionary hashMap;
    private LineList shape;
    private LineList trace;

    private final List<WinListener> winListeners = new ArrayList<>(2);

    private final LevelState levelState;
    private final LevelLoader levelLoader;

    public TraceGame(Context context, @NonNull LevelState state, LevelLoader levelLoader) {
        super(context);
        levelState = state;
        this.levelLoader = levelLoader;
        setupLevel();
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
                        trace.addWithReplacement(componentLines);
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

    public void setWinEventListener(WinListener eventListener) {
        winListeners.add(eventListener);
    }

    private void notifyWinEventListeners() {
        for (WinListener listener : winListeners)
            listener.onWin(new WinEvent(this));
    }

    public void toNextLevel() {
        levelState.nextLevel();
        setupLevel();
    }

    private void setupLevel() {
        try {
            shape = levelLoader.getLevel(levelState.getCurrentLevel());
        } catch (NoMoreLevelsException e) {
            Toast.makeText(context, context.getString(R.string.no_more_levels), Toast.LENGTH_LONG).show();
        }
        trace = new LineList(shape.size());
        hashMap = new LineDictionary(shape, false);

        new Thread() {
            @Override
            public void run() {
                LineDictionary dict = new LineDictionary(shape, true);
                synchronized (hashMap) {
                    hashMap = dict;
                }
            }
        }.start();

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
}
