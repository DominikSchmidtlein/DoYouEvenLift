package com.domkick1.trace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DrawView extends View implements Observer {

    private TraceGame trace;

    Paint shapeLines;
    Paint traceLines;

    public DrawView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        setUpPaint();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setUpPaint();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        setUpPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(trace.getShape() == null || trace.getTrace() == null)
            return;

        canvas.drawLines(linesAsFloats(trace.getShape()), shapeLines);
        canvas.drawLines(linesAsFloats(trace.getTrace()), traceLines);
    }

    private void setUpPaint() {
        shapeLines = new Paint(Paint.ANTI_ALIAS_FLAG);
        traceLines = new Paint(Paint.ANTI_ALIAS_FLAG);
        shapeLines.setColor(Color.LTGRAY);
        shapeLines.setStrokeCap(Paint.Cap.ROUND);
        shapeLines.setStrokeWidth(30);
        traceLines.set(shapeLines);
        traceLines.setColor(0xFF00AEFF);
    }

    public void addModel(TraceGame trace) {
        this.trace = trace;
        trace.addObserver(this);
    }

    public float[] linesAsFloats(ArrayList<Line> lines) {
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

    @Override
    public void update(Observable observable, Object data) {
        this.invalidate();
    }
}