package com.Android.ICreated.Activity;

import android.app.Activity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.okhttp.OkHttpSpiceService;

/**
 * Created by Филипп on 22.03.2015.
 */
public class BaseSpiceActivity extends Activity {
    private SpiceManager spiceManager = new SpiceManager( OkHttpSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
