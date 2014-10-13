package com.Android.ICreated;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, StorageListener, GoogleMap.OnInfoWindowClickListener
{
    SupportMapFragment mapFragment;
    GoogleMap map;
    UiSettings uiSettings;
    Storage storage;
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.events);

        storage = (Storage) getApplication();
        storage.addListener(this);

        mapInit();
        drawerInit();
    }

    private void mapInit()
    {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null)
        {
            finish();
            return;
        }
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(new InfoWindowAdapter(this));

        uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    private void drawerInit()
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new CustomAdapter(this, R.layout.list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.lines, R.string.app_name, R.string.events)
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
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
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
        if (drawerLayout.isDrawerOpen(drawerList))
        {
            menu.findItem(R.id.action_new_event).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.action_new_event).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        storage.setCurLatLng(latLng);
        Intent intent = new Intent(this, EventCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        storage.setCurLatLng(marker.getPosition());
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        storage.setCurLatLng(marker.getPosition());
        Intent intent = new Intent(this, EventShowActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEventAdded(Event event)
    {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(event.getLatLng())
                .title(event.getTitle()));
        marker.showInfoWindow();
    }
}
