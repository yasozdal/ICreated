package com.Android.ICreated.matchers;

import android.widget.DatePicker;
import com.google.android.gms.maps.GoogleMap;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Филипп on 04.05.2015.
 */
public class ClassNameMatcher extends TypeSafeMatcher<String> {
    private String expectedClassName;

    public ClassNameMatcher(String expectedClassName) {
        this.expectedClassName = expectedClassName;
    }

    @Override
    public boolean matchesSafely(String name) {
        return expectedClassName.equals(name);
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("ClassName - ").appendValue(expectedClassName);
    }

    @Factory
    public static ClassNameMatcher DatePicker() {
        return new ClassNameMatcher(DatePicker.class.getName());
    }

    @Factory
    public static ClassNameMatcher GoogleMap() {
        return new ClassNameMatcher(GoogleMap.class.getName());
    }
}
