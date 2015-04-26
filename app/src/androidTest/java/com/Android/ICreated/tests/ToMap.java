package com.Android.ICreated.tests;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.TextView;
import com.Android.ICreated.Activity.LoginActivity;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.Android.ICreated.R;
import com.google.android.gms.common.SignInButton;

/**
 * Created by philipp on 27.04.15.
 */
public class ToMap extends ActivityUnitTestCase<LoginActivity> {

    private Activity mLoginActivity;
    private Button mToMap;
    private Intent mIntent;


    public ToMap() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mIntent = new Intent(getInstrumentation()
                .getContext(), EventsShowActivity.class);

        startActivity(mIntent, null, null);
        final Button toMapButton = (Button) getActivity()
                .findViewById(R.id.btnToMap);

    }

    @MediumTest
    public void testMapActivityWasLaunchedWithIntent() {
        startActivity(mIntent, null, null);

        final Button toMapButton =
                (Button) getActivity()
                        .findViewById(R.id.btnToMap);
        toMapButton.performClick();

        final Intent mapIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", mapIntent);
        assertTrue(isFinishCalled());
    }
}
