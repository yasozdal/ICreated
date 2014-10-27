package com.Android.ICreated.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.Android.ICreated.CustomAdapter;
import com.Android.ICreated.Event;
import com.Android.ICreated.R;
import com.Android.ICreated.Storage;

/**
 * Created by Филипп on 05.10.2014.
 */
public class EventShowActivity extends Activity {
    Storage storage;
    ActionBar actionBar;
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.show);
        storage = (Storage) getApplication();
        drawerInit();
        eventShow();
    }

    private void eventShow()
    {
        Event event = storage.getEvent(storage.getCurLatLng());
        TextView EventDescription = (TextView) findViewById(R.id.EventDescription);

        if (event == null)
        {
            actionBar.setTitle("Ошибка");
        }
        else
        {
            EventDescription.setText(event.getDescription());
        }


        Button CloseButton = (Button) findViewById(R.id.CloseButton);
        View.OnClickListener oclCloseButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        CloseButton.setOnClickListener(oclCloseButton);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void drawerInit()
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new CustomAdapter(this, R.layout.drawer_list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.bar_icon, R.string.app_name, R.string.events)
        {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getActionBar().setTitle(R.string.events);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }
}