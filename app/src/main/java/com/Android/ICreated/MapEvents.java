package com.Android.ICreated;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.Android.ICreated.Activity.eventCreate.EventCreateActivity;
import com.Android.ICreated.Activity.EventShowActivity;
import com.Android.ICreated.Activity.eventCreate.EventCreateWorkerFragment;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.Android.ICreated.Activity.eventsShow.EventsShowModel;
import com.Android.ICreated.Activity.eventsShow.EventsShowWorkerFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class MapEvents extends Fragment implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, EventsShowModel.Observer, GoogleMap.OnInfoWindowClickListener
{
    private MapView mapView;
    private GoogleMap map;
    private Context context;
    private UiSettings uiSettings;
    private EventsShowModel eventsShowModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.events_map, container, false);
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        context = getActivity();
        eventsShowModel = ((EventsShowActivity)getActivity()).getModel();
        if (mapInit())
        {
            loadEvents();
        }

        CameraPosition cameraPosition = eventsShowModel.getCameraPosition();
        if (eventsShowModel.getCameraPosition() != null)
        {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        return v;
    }

    private boolean mapInit()
    {
        map = mapView.getMap();

        if (map != null)
        {
            map.getUiSettings().setMyLocationButtonEnabled(false);

            MapsInitializer.initialize(this.getActivity()); // Для CameraUpdate

            map.setOnMapLongClickListener(this);
            map.setOnMarkerClickListener(this);
            map.setOnInfoWindowClickListener(this);
            map.setInfoWindowAdapter(new InfoWindowAdapter(eventsShowModel, context));

            uiSettings = map.getUiSettings();
            uiSettings.setCompassEnabled(false);
            return true;
        }
        else
        {
            int checkGooglePlayServices = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());
            if (checkGooglePlayServices != ConnectionResult.SUCCESS)
            {
                GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices, this.getActivity(), 0).show();
            }
            return false;
        }
    }

    private void loadEvents()
    {
        Event[] events = eventsShowModel.getEvents();
        for (int i = 0; i < eventsShowModel.getSize(); ++i)
        {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(events[i].getLatLng()));
            eventsShowModel.addMarkerId(marker);
        }
    }

    @Override
    public void onResume()
    {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        eventsShowModel.setCameraPosition(map.getCameraPosition());
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mapView != null)
        {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        if (mapView != null)
        {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        Intent intent = new Intent(context, EventCreateActivity.class);
        intent.putExtra("LatLng", latLng);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        Intent intent = new Intent(context, EventShowActivity.class);
        intent.putExtra("Event", eventsShowModel.findEventByMarker(marker));
        startActivity(intent);
    }

    @Override
    public void onEventAdded(Event event)
    {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(event.getLatLng()));
        eventsShowModel.addMarkerId(marker);
        marker.showInfoWindow();
    }
}
