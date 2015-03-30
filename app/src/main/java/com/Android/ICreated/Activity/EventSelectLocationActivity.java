package com.Android.ICreated.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.Android.ICreated.DrawerAdapter;
import com.Android.ICreated.R;
import com.Android.ICreated.Storage;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ник on 22.10.2014.
 */
public class EventSelectLocationActivity extends ActionBarActivity implements GoogleMap.OnMapClickListener
{
    SupportMapFragment mapFragment;
    Storage storage;
    GoogleMap map;
    UiSettings uiSettings;
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;
    Menu barMenu;
    double latitude, longitude;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        storage = (Storage)getApplication();

        Toolbar toolbar = toolbarInit();
        if (toolbar != null)
        {
            drawerInit();
        }
        mapInit();
    }

    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle(R.string.choose_event_location);
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.bar_icon);
        }

        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        barMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_action_bar, menu);
        if (storage.getCurLatLng() != null)
        {
            map.addMarker(new MarkerOptions().position(storage.getCurLatLng()));
            barMenu.findItem(R.id.btnSaveEvent).setEnabled(true);
            barMenu.findItem(R.id.btnSaveEvent).setIcon(R.drawable.complete);
        }
        else
        {
            barMenu.findItem(R.id.btnSaveEvent).setEnabled(false);
            barMenu.findItem(R.id.btnSaveEvent).setIcon(R.drawable.complete_grey);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void drawerInit()
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.events)
        {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.choose_event_location);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
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
        map.setOnMapClickListener(this);
        if (storage.getCurLatLng() != null)
        {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(storage.getCurLatLng());
            map.animateCamera(cameraUpdate);
        }
        uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.btnSaveEvent)
        {
            returnLatLng();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
        if (latLng != null)
        {
            map.clear();
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            map.addMarker(new MarkerOptions().position(latLng));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            map.animateCamera(cameraUpdate);
            barMenu.findItem(R.id.btnSaveEvent).setEnabled(true);
            barMenu.findItem(R.id.btnSaveEvent).setIcon(R.drawable.complete);
        }
    }

    private void returnLatLng()
    {
        Intent intent = new Intent();
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

}
