package com.domkick1.doyouevenlift;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements NextLevelDialogFragment.NextLevelDialogListener {

    private CustomView drawView;
    private DoYouEvenLift doYouEvenLift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get local reference for canvas
        doYouEvenLift = new DoYouEvenLift();
        drawView = (CustomView) findViewById(R.id.draw_view);
        drawView.addModel(doYouEvenLift);
        doYouEvenLift.addListener(this);
        updateCanvas();

        drawView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //first touch
                return doYouEvenLift.click(event);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //clicks next level
    public void onDialogNextLevelClick(DialogFragment dialogFragment){
        if(!doYouEvenLift.incrementCurrentLevel()){
            //no more level
        }
        shape = toList(levels.get(currentLevel));
        hashMap = generateMap(shape);
        for(int i = 0; i < shape.size(); i+= 4){
            generateMapForCompoundLines(hashMap,shape,new Line(shape.get(i),shape.get(i+1),shape.get(i+2),shape.get(i+3)));
        }
        updateCanvas();
    }
    //clicks retry level
    public void onDialogRetryClick(DialogFragment dialogFragment){
        updateCanvas();
    }

    private void updateCanvas(){
        drawView.updateCanvas();
    }


    public CustomView getDrawView() {
        return drawView;
    }

    public void launchNextLevelDialog() {
        DialogFragment newFragment = new NextLevelDialogFragment();
        newFragment.show(getFragmentManager(), "next_level");
    }

}