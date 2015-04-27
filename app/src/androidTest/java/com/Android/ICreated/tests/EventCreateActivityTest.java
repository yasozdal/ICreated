package com.Android.ICreated.tests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.TextView;
import com.Android.ICreated.Activity.LoginActivity;
import com.Android.ICreated.Activity.eventCreate.EventCreateActivity;
import com.Android.ICreated.R;


/**
 * Created by Филипп on 27.04.2015.
 */
@LargeTest
public class EventCreateActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

//    private EventCreateActivity mEventCreateActivity;
//    private TextView mDescriptionEvent;

    public EventCreateActivityTest() {super(LoginActivity.class);}

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
//        mEventCreateActivity = getActivity();
//        mDescriptionEvent = (TextView) mEventCreateActivity.findViewById(R.id.etDescription);
    }

//    @SmallTest
//    public void testPreconditions() {
//        assertNotNull("mEventCreateActivity is null", mEventCreateActivity);
//        assertNotNull("mDescription is null", mDescriptionEvent);
//    }

    @SmallTest
    public void testTextViewDescription() {
        Espresso.onView(ViewMatchers.withId(R.id.userName)).perform(ViewActions.typeText("Hello"));
//        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Espresso"));
//        Espresso.onView(ViewMatchers.withId(R.id.btnSaveEvent)).perform(ViewActions.click());
    }

}
