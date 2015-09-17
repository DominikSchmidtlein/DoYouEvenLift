package com.domkick1.doyouevenlift;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.view.View;

        import java.util.ArrayList;

public class CustomView extends View {

    private float[] shape = new float[]{};
    private float[] trace = new float[]{};

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

        canvas.drawLines(shape, shapeLines);
        canvas.drawLines(trace, traceLines);
    }

    public void setTrace(ArrayList<Float> trace) {
        this.trace = toArray(trace);
    }
    public ArrayList<Float> getTrace() {
        return toList(this.trace);
    }
    public void setShape(ArrayList<Float> shape) {
        this.shape = toArray(shape);
    }
    public ArrayList<Float> getShape() {
        return toList(this.shape);
    }
    private ArrayList<Float> toList(float[] a){
        ArrayList<Float> b = new ArrayList<Float>();
        for(float f:a){
            b.add(f);
        }
        return b;
    }
    private float[] toArray(ArrayList<Float> a){
        float[] b = new float[a.size()];
        for(int i = 0;i < a.size();i++){
            b[i] = a.get(i);
        }
        return b;
    }
    private void setUpPaint() {
        shapeLines.setColor(Color.LTGRAY);
        shapeLines.setStrokeCap(Paint.Cap.ROUND);
        shapeLines.setStrokeWidth(30);
        traceLines.set(shapeLines);
        traceLines.setColor(0xFF00AEFF);
    }
}