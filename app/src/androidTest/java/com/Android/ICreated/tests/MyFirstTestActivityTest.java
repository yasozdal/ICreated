package com.Android.ICreated.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.Android.ICreated.Activity.LoginActivity;
import com.Android.ICreated.R;

/**
 * Created by Филипп on 26.04.2015.
 */
public class MyFirstTestActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity mFirstTestActivity;
    private TextView mFirstTestText;

    public MyFirstTestActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFirstTestActivity = getActivity();
        mFirstTestText =
                (TextView) mFirstTestActivity
                        .findViewById(R.id.userName);
    }

    public void testPreconditions() {
        assertNotNull("mFirstTestActivity is null", mFirstTestActivity);
        assertNotNull("mFirstTestText is null", mFirstTestText);
    }

    public void testMyFirstTestTextView_labelText() {
        final String expected =
                mFirstTestActivity.getString(R.string.user_name);

        final String actual = mFirstTestText.getHint().toString();

        assertEquals(expected, actual);
    }
}


