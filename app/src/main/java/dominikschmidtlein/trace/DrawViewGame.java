package dominikschmidtlein.trace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class DrawViewGame extends DrawView {

    private TraceGame trace;

    private Paint shapeLines;
    private Paint traceLines;

    public DrawViewGame(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public DrawViewGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public DrawViewGame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(trace.getShape() == null || trace.getTrace() == null)
            return;

        canvas.drawLines(linesAsFloats(trace.getShape()), shapeLines);
        canvas.drawLines(linesAsFloats(trace.getTrace()), traceLines);
    }

    @Override
    protected void setupPaint() {
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
}