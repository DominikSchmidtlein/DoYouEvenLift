package com.domkick1.trace;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements NextLevelDialogFragment.NextLevelDialogListener {

//    private DrawerLayout drawerLayout;
//    private ListView drawerList;
//    private ActionBarDrawerToggle drawerToggle;

    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

//        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
//        drawerList = (ListView) findViewById(R.id.left_drawer);
//        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, new String[]{"Play", "Level Builder"}));
//        drawerList.setOnItemClickListener(this);

//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.open_drawer, R.string.close_drawer) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                invalidateOptionsMenu();
//            }

//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu();
//            }
//        };
//        drawerLayout.setDrawerListener(drawerToggle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        android.graphics.Point size = new android.graphics.Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int actionBarHeight = (int) getApplicationContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize}).getDimension(0, 0);

        TraceGame traceGame = new TraceGame(this, size, actionBarHeight, 0);
        DrawViewGame drawViewGame = (DrawViewGame) findViewById(R.id.draw_view);
        gameController = new GameController(this, traceGame, drawViewGame);

        drawViewGame.addModel(traceGame);
        drawViewGame.setOnTouchListener(gameController);
        traceGame.setWinEventListener(gameController);
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
//        if (drawerToggle.onOptionsItemSelected(item))
//            return true;

        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.action_instructions:
                intent = new Intent(MainActivity.this, InstructionsActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        switch (position) {
//            case 0:
//                break;
//            case 1:
//                startActivity(new Intent(MainActivity.this, LevelBuilderActivity.class));
//                break;
//            default:
//                break;
//        }
//
//        if (drawerLayout.isDrawerOpen(drawerList))
//            drawerLayout.closeDrawer(drawerList);
//        else
//            drawerLayout.openDrawer(drawerList);
//    }

    public void onDialogNextLevelClick(DialogFragment dialogFragment) {
        gameController.nextLevelSelected();
    }

    public void onDialogRetryClick(DialogFragment dialogFragment) {

    }

}