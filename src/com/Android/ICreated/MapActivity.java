package com.Android.ICreated;

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

public class MapActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener
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
        getActionBar().hide();

        storage = (Storage) getApplication();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null)
        {
            finish();
            return;
        }
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
        uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        int curSize = storage.getSize();
        storage.setCurLatLng(latLng);
        Intent intent = new Intent(this, EventCreateActivity.class);
        startActivity(intent);
        Marker marker = map.addMarker(new MarkerOptions()
                        .position(latLng)
        );
        Log.d("Logs", "" + curSize + " " + storage.getSize());
        if (curSize == storage.getSize())
        {
            marker.remove();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        storage.setCurLatLng(marker.getPosition());
        Intent intent = new Intent(this, EventShowActivity.class);
        startActivity(intent);
        return true;
    }
}
