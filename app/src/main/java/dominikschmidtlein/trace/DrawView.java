package dominikschmidtlein.trace;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by domin_2o9sb4z on 2016-02-03.
 */
public abstract class DrawView extends View implements Observer {

    protected Trace trace;

    public DrawView(Context context) {
        super(context);
        setupPaint();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setupPaint();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        setupPaint();
    }

    protected abstract void setupPaint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected float[] linesAsFloats(ArrayList<Line> lines) {
        float[] floats = new float[lines.size() * 4];
        int i = 0;
        for (Line line : lines) {
            floats[i] = line.getP1().getX();
            floats[i + 1] = line.getP1().getY();
            floats[i + 2] = line.getP2().getX();
            floats[i + 3] = line.getP2().getY();
            i += 4;
        }
        return floats;
    }

    protected float[] pointsAsFloats(Collection<Point> points) {
        float[] floats = new float[points.size() * 2];
        int i = 0;
        for (Point point : points) {
            floats[i] = point.getX();
            floats[i + 1] = point.getY();
            i += 2;
        }
        return floats;
    }


    @Override
    public void update(Observable observable, Object data) {
        this.invalidate();
    }
}
