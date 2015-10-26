package com.domkick1.doyouevenlift;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity
        implements NextLevelDialogFragment.NextLevelDialogListener, ListView.OnItemClickListener {

    private CustomView drawView;
    private DoYouEvenLift doYouEvenLift;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] listViewTitles;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LevelDatabaseOperations levelDatabaseOperations = new LevelDatabaseOperations(this);
        listViewTitles = new String[Levels.getLevels().size()];
        for(int i = 0; i < listViewTitles.length; i++)
            listViewTitles[i] = "Level: " + i;

        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,listViewTitles));
        drawerList.setOnItemClickListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);


        doYouEvenLift = new DoYouEvenLift();
        drawView = (CustomView) findViewById(R.id.draw_view);
        drawView.addModel(doYouEvenLift);
        doYouEvenLift.addListener(this);

        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    //clicks next level
    public void onDialogNextLevelClick(DialogFragment dialogFragment){
        if(!doYouEvenLift.incrementCurrentLevel()){}//no more levels
    }
    //clicks retry level
    public void onDialogRetryClick(DialogFragment dialogFragment){
        drawView.updateCanvas();
    }

    public CustomView getDrawView() {
        return drawView;
    }

    public void launchNextLevelDialog() {
        DialogFragment newFragment = new NextLevelDialogFragment();
        newFragment.show(getFragmentManager(), "next_level");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        doYouEvenLift.setCurrentLevel(position);
        if(drawerLayout.isDrawerOpen(drawerList))
            drawerLayout.closeDrawer(drawerList);
        else
            drawerLayout.openDrawer(drawerList);
    }
}