package com.domkick1.doyouevenlift;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.view.View;

        import java.util.ArrayList;

public class CustomView extends View {
    private DoYouEvenLift doYouEvenLift;

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

        canvas.drawLines(doYouEvenLift.getShapeAsFloats(), shapeLines);
        canvas.drawLines(doYouEvenLift.getTraceAsFloats(), traceLines);
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