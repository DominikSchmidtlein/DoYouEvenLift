package com.domkick1.trace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

public class LevelBuilderActivity extends AppCompatActivity implements View.OnTouchListener {

    TraceBuilder traceBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_builder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.level_builder_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.graphics.Point size = new android.graphics.Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        int actionBarHeight = (int) getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize}).getDimension(0, 0);

        traceBuilder = new TraceBuilder(size, actionBarHeight, TraceBuilder.Mode.ISOMETRIC);

        DrawViewLevelBuilder drawViewLevelBuilder = (DrawViewLevelBuilder) findViewById(R.id.level_builder_draw_view);
        drawViewLevelBuilder.addModel(traceBuilder);

        drawViewLevelBuilder.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return traceBuilder.onTouch(v, event);
    }
}
