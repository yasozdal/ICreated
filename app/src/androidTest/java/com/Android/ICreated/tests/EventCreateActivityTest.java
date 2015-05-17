package com.Android.ICreated.tests;

import android.app.Activity;
import android.content.res.TypedArray;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import android.util.Log;
import com.Android.ICreated.Activity.eventCreate.EventCreateActivity;
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

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withClassName;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withContentDescription;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by Филипп on 27.04.2015.
 */
@LargeTest
public class EventCreateActivityTest extends ActivityInstrumentationTestCase2<EventCreateActivity> {

    private Activity eventCreateActivity;

    public EventCreateActivityTest() {super(EventCreateActivity.class);}


    @Override
    public void setUp() throws Exception {
        super.setUp();
        eventCreateActivity = getActivity();
    }

    @LargeTest
    public void testButtonsHaveRightCondition() {
        Espresso.onView(withId(R.id.btnSaveEvent)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())));
        Espresso.onView(withId(R.id.btnCategory)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(withId(R.id.btnLocation)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(withId(R.id.btnLock)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(withId(R.id.btnDate)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(withId(R.id.btnPhoto)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));

        Espresso.onView(withId(R.id.btnSaveEvent)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(withId(R.id.btnCategory)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(withId(R.id.btnLocation)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(withId(R.id.btnLock)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(withId(R.id.btnDate)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
        Espresso.onView(withId(R.id.btnPhoto)).check(ViewAssertions.matches(ViewMatchers.isClickable()));
    }

    @LargeTest
    public void testSetDateTime() {
        Espresso.onView(ViewMatchers.withId(R.id.btnDate)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withClassName(ClassNameMatcher.DatePicker())).perform(MyActions.setDate(10, 10, 2016));
        Espresso.onView(ViewMatchers.withText(R.string.set_date)).perform(ViewActions.click());
    }

    @LargeTest
    public void testSetConcreteCategory() {
        TypedArray cat = eventCreateActivity.getResources().obtainTypedArray(R.array.categories);
        Espresso.onView(ViewMatchers.withId(R.id.btnCategory)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(cat.getString(0))).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.ok)).perform(ViewActions.click());
    }
    @LargeTest
    public void testSetCategoryWithoutChoosingConcrete() {
        Espresso.onView(ViewMatchers.withId(R.id.btnCategory)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.ok)).perform(ViewActions.click());
    }


    @LargeTest
    public void testFullCreatingEvent() {
        Espresso.onView(withId(R.id.btnSaveEvent)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())));
        Espresso.onView(withId(R.id.etDescription)).perform(ViewActions.typeText("My Cool Event"));

        Espresso.onView(withId(R.id.btnSaveEvent)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())));
        Espresso.onView(withId(R.id.btnDate)).perform(ViewActions.click());
        Espresso.onView(withClassName(ClassNameMatcher.DatePicker())).perform(MyActions.setDate(10, 10, 2016));
        Espresso.onView(withText(R.string.set_date)).perform(ViewActions.click());
        Espresso.onView(withText(R.string.set_time)).perform(ViewActions.click());

        Espresso.onView(withId(R.id.btnSaveEvent)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())));

        Espresso.onView(withId(R.id.btnLocation)).perform(ViewActions.click());
        Espresso.onView(withContentDescription("Карта Google")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.btnSaveEvent)).perform(ViewActions.click());

        Espresso.onView(withId(R.id.btnSaveEvent)).perform(ViewActions.click());

    }

//    @LargeTest
//    public void testFullCreatingEventWithNetwork() {
//        AddEventRequest request = mock(AddEventRequest.class);
//        try {
//            when(request.loadDataFromNetwork()).thenReturn(new AddEventResponse(200, "OK"));
//        }
//        catch (Exception e) {
//            Log.d("MUST NOT EXCEPTION", "Mock AddEventRequests");
//        }
//
//        RequestsFactory factory = mock(MockRequests.class);
//        when(factory.addEventRequest(anyString(), anyString(), anyString(), Matchers.any(Calendar.class))).thenReturn(request);
//
//        Network.setRequestsFactory(factory);
//
//        testFullCreatingEvent();
//
//
//        try {
//            Thread.currentThread().sleep(5000);
//        }
//        catch (InterruptedException e) {};
//
//        verify(factory).addEventRequest(anyString(), anyString(), anyString(), Matchers.any(Calendar.class));
//    }

}
