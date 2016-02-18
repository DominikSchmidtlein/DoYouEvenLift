package com.domkick1.trace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class LevelBuilderActivity extends AppCompatActivity implements View.OnTouchListener {

    TraceBuilder traceBuilder;
    FloatingActionButton fab;
    FabListener fabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_builder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.level_builder_toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!traceBuilder.generateNewJsonFile())
                    Snackbar.make(view, "Cannot add illegal shape", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
        fabListener = new FabListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.graphics.Point size = new android.graphics.Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        int actionBarHeight = (int) getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize}).getDimension(0, 0);

        traceBuilder = new TraceBuilder(this, size, actionBarHeight, TraceBuilder.Mode.ISOMETRIC);

        DrawViewLevelBuilder drawViewLevelBuilder = (DrawViewLevelBuilder) findViewById(R.id.level_builder_draw_view);
        drawViewLevelBuilder.addModel(traceBuilder);

        drawViewLevelBuilder.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_level_builder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_reset:
                traceBuilder.resetLevelsToJsonFile();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        boolean ret = traceBuilder.onTouch(v, event);
        if ((event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) && ret) {
            fabListener.setShown(false);
            fab.hide(fabListener);
        }
        if (!ret || event.getAction() == MotionEvent.ACTION_UP) {
            fabListener.setShown(true);
            fab.show(fabListener);
        }
        return ret;
    }

    private class FabListener extends FloatingActionButton.OnVisibilityChangedListener {

        private boolean shown;

        @Override
        public void onHidden(FloatingActionButton fab) {
            super.onHidden(fab);
            if (shown)
                fab.show(this);
        }

        @Override
        public void onShown(FloatingActionButton fab) {
            super.onShown(fab);
            if (!shown)
                fab.hide(this);
        }

        public void setShown(boolean shown) {
            this.shown = shown;
        }
    }

}
