package com.domkick1.trace;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NextLevelDialogFragment.NextLevelDialogListener {

    private GameController gameController;

    private final String currentLevelKey = "currentlevel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolabr setup
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        // get screen dimensions and action bar height
        android.graphics.Point size = new android.graphics.Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int actionBarHeight = (int) getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize}).getDimension(0, 0);
        ScreenDimensions dim = new ScreenDimensions(size.x, size.y, actionBarHeight + 50);

        // read state from files
        LevelState state = new StateLoader(this, dim).loadState();

        // set state saver to listen for state changes
        state.setLevelChangedListener(new StateSaver(this));
        state.resetLevels();

        TraceGame traceGame = new TraceGame(this, state);
        DrawViewGame drawViewGame = (DrawViewGame) findViewById(R.id.draw_view);
        gameController = new GameController(this, traceGame, drawViewGame);

        drawViewGame.addModel(traceGame);
        drawViewGame.setOnTouchListener(gameController);
        traceGame.setWinEventListener(gameController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.action_instructions:
                intent = new Intent(MainActivity.this, InstructionsActivity.class);
                break;
            case R.id.action_level_builder:
                intent = new Intent(MainActivity.this, LevelBuilderActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void onDialogNextLevelClick(DialogFragment dialogFragment) {
        gameController.nextLevelSelected();
    }

    public void onDialogRetryClick(DialogFragment dialogFragment) {

    }

}