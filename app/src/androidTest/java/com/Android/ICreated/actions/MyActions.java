package com.Android.ICreated.actions;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.DatePicker;
import com.google.android.gms.maps.model.LatLng;
import org.hamcrest.Matcher;

/**
 * Created by Филипп on 28.04.2015.
 */
public final class MyActions {
    public static ViewAction setDate(final int day, final int month, final int year) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                ((DatePicker) view).updateDate(year, month, day);
            }

            @Override
            public String getDescription() {
                return "Set the date into the datepicker(day, month, year)";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(DatePicker.class);
            }
        };
    }

//    map.addMarker(new MarkerOptions().position(latLng));
//    public static ViewAction setMarker(final LatLng latLng) {
//        return new ViewAction() {
//            @Override
//            public Matcher<View> getConstraints() {
//                return ViewMatchers.is
//            }
//
//            @Override
//            public String getDescription() {
//                return null;
//            }
//
//            @Override
//            public void perform(UiController uiController, View view) {
//
//            }
//        }
//    }
}
