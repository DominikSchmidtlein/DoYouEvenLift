package com.domkick1.doyouevenlift;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.View;

        import java.util.ArrayList;

public class CustomView extends View {
    private DoYouEvenLift doYouEvenLift;
    private float x;
    private float y;

    Paint shapeLines = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint traceLines = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setUpPaint();

        float[] shape = doYouEvenLift.getShapeAsFloats();
        float[] trace = doYouEvenLift.getTraceAsFloats();

        for(int i = 0; i < (shape.length > trace.length? shape.length : trace.length); i += 2){
            if(i < shape.length) {
                shape[i] = shape[i] + x;
                shape[i + 1] = shape[i + 1] + y;
            }
            if(i < trace.length){
                trace[i] = trace[i] + x;
                trace[i + 1] = trace[i + 1] + y;
            }
        }

        canvas.drawLines(shape, shapeLines);
        canvas.drawLines(trace, traceLines);
    }

    private void setUpPaint() {
        shapeLines.setColor(Color.LTGRAY);
        shapeLines.setStrokeCap(Paint.Cap.ROUND);
        shapeLines.setStrokeWidth(30);
        traceLines.set(shapeLines);
        traceLines.setColor(0xFF00AEFF);
    }

    public void updateCanvas(){
        this.invalidate();
    }



    public void addModel(DoYouEvenLift doYouEvenLift){
        this.doYouEvenLift =  doYouEvenLift;
    }


}