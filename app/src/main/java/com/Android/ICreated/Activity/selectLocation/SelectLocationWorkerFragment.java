package com.Android.ICreated.Activity.selectLocation;

import android.app.Fragment;
import android.os.Bundle;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by Mikhail on 11.04.2015.
 */
public class SelectLocationWorkerFragment extends Fragment
{
    private CameraPosition cameraPosition;

    public SelectLocationWorkerFragment()
    {
        this.cameraPosition = null;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setCameraPosition(CameraPosition cameraPosition)
    {
        this.cameraPosition = cameraPosition;
    }

    public CameraPosition getCameraPosition()
    {
        return cameraPosition;
    }
}
