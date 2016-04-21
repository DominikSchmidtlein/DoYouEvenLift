package com.domkick1.trace;

import android.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by domin_2o9sb4z on 2016-02-11.
 */
public class GameController implements View.OnTouchListener, WinListener {

    private final MainActivity mainActivity;
    private final TraceGame gameModel;
    private final DrawViewGame gameView;

    public GameController(MainActivity mainActivity, TraceGame gameModel, DrawViewGame gameView) {
        this.mainActivity = mainActivity;
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gameModel.onTouch(v, event);
    }

    public void onWin(WinEvent winEvent) {
        launchNextLevelDialog();
    }

    public void launchNextLevelDialog() {
        DialogFragment newFragment = new NextLevelDialogFragment();
        newFragment.show(mainActivity.getFragmentManager(), "next_level");
    }

    public void nextLevelSelected() {
        gameModel.toNextLevel();
    }

}
