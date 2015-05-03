package com.Android.ICreated.tests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.DatePicker;
import com.Android.ICreated.Activity.eventCreate.EventCreateActivity;
import com.Android.ICreated.R;
import com.Android.ICreated.actions.MyActions;
import com.Android.ICreated.matchers.DataPickerMatcher;
import com.Android.ICreated.matchers.MyMatchers;
import com.fasterxml.jackson.databind.util.ViewMatcher;


/**
 * Created by Филипп on 27.04.2015.
 */
@LargeTest
public class EventCreateActivityTest extends ActivityInstrumentationTestCase2<EventCreateActivity> {

    public EventCreateActivityTest() {super(EventCreateActivity.class);}


    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }


    @SmallTest
    public void testTextViewDescription() {
        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Hello"));
    }

    @SmallTest
    public void testTextViewDescription2() {
        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Hello"));
        Espresso.onView(ViewMatchers.withId(R.id.btnDate)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(DataPickerMatcher.isDataPicker())).perform(MyActions.setDate(10, 10, 2016));
        Espresso.onView(ViewMatchers.withText(R.string.set_date)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.set_date)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Hello"));
        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Hello"));
        Espresso.onView(ViewMatchers.withId(R.id.btnLocation)).perform(ViewActions.click());
    }

}
