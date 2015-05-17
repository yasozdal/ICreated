package com.Android.ICreated.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.Android.ICreated.R;
import com.Android.ICreated.actions.MyActions;
import com.Android.ICreated.matchers.ClassNameMatcher;
import com.Android.ICreated.network.Network;
import com.Android.ICreated.network.RequestsFactory;
import com.Android.ICreated.network.requests.AddEventRequest;
import com.Android.ICreated.network.responses.AddEventResponse;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;
import org.mockito.Matchers;

import java.util.Calendar;

import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Created by Филипп on 11.05.2015.
 */
public class EventsShowActivityTest extends ActivityInstrumentationTestCase2<EventsShowActivity> {

    private Activity eventsShowActivity;

    public EventsShowActivityTest() {super(EventsShowActivity.class);}


    @Override
    public void setUp() throws Exception {
        super.setUp();
        eventsShowActivity = getActivity();
    }

    @LargeTest
    public void testWorkSwitchBetweenMapAndList() {
        Espresso.onView(withText(R.string.list)).perform(click());
        Espresso.onView(withText(R.string.map)).perform(click());
    }


}
