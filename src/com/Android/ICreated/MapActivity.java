package com.Android.ICreated;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("События");

        storage = (Storage) getApplication();
        storage.addListener(this);

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
    public void onEventAdded(Event event)
    {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(event.getLatLng())
                .title(event.getTitle())
        );
        marker.showInfoWindow();
    }
}
