package com.domkick1.trace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by domin_2o9sb4z on 2016-02-03.
 */
public class DrawViewLevelBuilder extends DrawView {

    private TraceBuilder traceBuilder;

    private Paint pointPaint;
    private Paint linePaint;
    private Paint problemPaint;

    public DrawViewLevelBuilder(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public DrawViewLevelBuilder(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public DrawViewLevelBuilder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoints(pointsAsFloats(traceBuilder.getPoints()), pointPaint);
        if(traceBuilder.getShape() != null)
            canvas.drawLines(linesAsFloats(traceBuilder.getShape()), linePaint);
        if(traceBuilder.getProblemPoints() != null)
            canvas.drawPoints(pointsAsFloats(traceBuilder.getProblemPoints()), problemPaint);
    }

    @Override
    protected void setupPaint() {
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.LTGRAY);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
        pointPaint.setStrokeWidth(30);
        linePaint = new Paint(pointPaint);
        linePaint.setColor(0xFFFF8000);
        problemPaint = new Paint(pointPaint);
        problemPaint.setColor(Color.RED);
        problemPaint.setStrokeWidth(50);
    }

    public void addModel(TraceBuilder traceBuilder){
        this.traceBuilder = traceBuilder;
        this.traceBuilder.addObserver(this);
    }
}
