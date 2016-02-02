package com.domkick1.trace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        float[] shape = this.trace.getShapeAsFloats();
//        float[] trace = this.trace.getTraceAsFloats();

//        for (int i = 0; i < (shape.length > trace.length ? shape.length : trace.length); i += 2) {
//            if (i < shape.length) {
//                shape[i] = shape[i];
//                shape[i + 1] = shape[i + 1];
//            }
//            if (i < trace.length) {
//                trace[i] = trace[i];
//                trace[i + 1] = trace[i + 1];
//            }
//        }

        canvas.drawLines(trace.getShapeAsFloats(), shapeLines);
        canvas.drawLines(trace.getTraceAsFloats(), traceLines);
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

    @Override
    public void update(Observable observable, Object data) {
        this.invalidate();
    }
}