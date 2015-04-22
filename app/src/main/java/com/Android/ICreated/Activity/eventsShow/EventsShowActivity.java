package com.Android.ICreated.Activity.eventsShow;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.Android.ICreated.*;
import com.Android.ICreated.Activity.EventShowActivity;
import com.Android.ICreated.Activity.eventCreate.EventCreateActivity;

/**
 * Created by Mikhail on 27.10.2014.
 */
public class EventsShowActivity extends ActionBarActivity
{
    private final String TAG_WORKER = "TAG_WORKER";
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;
    private FragmentTabHost tabHost;
    private EventsShowModel eventsShowModel;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_show);

        Toolbar toolbar = toolbarInit();
        if (toolbar != null)
        {
            tabHostInit();
            drawerInit(toolbar);
        }
        initWorkerFragment();
    }

    private void initWorkerFragment()
    {
        final EventsShowWorkerFragment retainedWorkerFragment =
                (EventsShowWorkerFragment) getFragmentManager().findFragmentByTag(TAG_WORKER);

        if (retainedWorkerFragment != null)
        {
            eventsShowModel = retainedWorkerFragment.getEventsShowModel();
        } else
        {
            final EventsShowWorkerFragment workerFragment = new EventsShowWorkerFragment();

            getFragmentManager().beginTransaction()
                    .add(workerFragment, TAG_WORKER)
                    .commit();

            eventsShowModel = workerFragment.getEventsShowModel();
            eventsShowModel.setContext(this);
        }
    }

    public EventsShowModel getModel()
    {
        return eventsShowModel;
    }

    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle(R.string.events);
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.bar_icon);
        }

        return toolbar;
    }

    private void tabHostInit()
    {
        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.map)).setIndicator(getResources().getString(R.string.map)),
                MapEvents.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.list)).setIndicator(getResources().getString(R.string.list)),
                ListEvents.class, null);
        for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.white));
        }
    }


    private void drawerInit(Toolbar toolbar)
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.events)
        {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.events);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        for(int i = 0; i < menu.size(); i++)
        {
            menu.getItem(i).setVisible(!drawerLayout.isDrawerOpen(drawerList));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.action_new_event:
                Intent intent = new Intent(this, EventCreateActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            Event event = data.getParcelableExtra("Event");
            eventsShowModel.addEvent(event);
            showEvent(event);
        }
    }

    public void showEvent(Event event)
    {
        Intent intent = new Intent(this, EventShowActivity.class);
        intent.putExtra("Event", event);
        startActivity(intent);
    }
}
