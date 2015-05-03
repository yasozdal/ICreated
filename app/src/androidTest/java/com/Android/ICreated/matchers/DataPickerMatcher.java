package com.Android.ICreated.matchers;

import android.widget.DatePicker;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Филипп on 04.05.2015.
 */
public class DataPickerMatcher extends TypeSafeMatcher<String> {
    private String expected = DatePicker.class.getName();

    public DataPickerMatcher() {
    }

    @Override
    public boolean matchesSafely(String name) {
        return expected.equals(name);
    }

//    @Override
//    protected void describeMismatchSafely(String name, Description mismatchDescription) {
//        mismatchDescription.appendText("fruit has shape - ").appendValue(name);
//    }

    @Override
    public void describeTo(Description description) {
        description.appendText("shape - ").appendValue(expected);
    }

    @Factory
    public static DataPickerMatcher isDataPicker() {
        return new DataPickerMatcher();
    }
}
