package com.Android.ICreated.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import com.Android.ICreated.Activity.LoginActivity;
import com.Android.ICreated.R;
import com.Android.ICreated.network.MyOkHttpSpiceService;
import com.Android.ICreated.network.Network;
import com.Android.ICreated.network.RequestsFactory;
import com.Android.ICreated.network.requests.GetEventsRequest;
import com.Android.ICreated.network.requests.SignInRequest;
import com.Android.ICreated.network.responses.SignInResponse;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import org.mockito.Mockito;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ������ on 11.05.2015.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        loginActivity = getActivity();
    }

//    @SmallTest
//    public void testRightInitialize() {
//
//    }

    private class MyManager extends SpiceManager {

        /**
         * Creates a {@link SpiceManager}. Typically this occurs in the construction
         * of an Activity or Fragment. This method will check if the service to bind
         * to has been properly declared in AndroidManifest.
         *
         * @param spiceServiceClass the service class to bind to.
         */
        public MyManager(Class<? extends SpiceService> spiceServiceClass) {
            super(spiceServiceClass);
        }

        @Override
        public void execute (SpiceRequest request, RequestListener listener) {
            try {
                listener.onRequestSuccess(request.loadDataFromNetwork());
            }
            catch (Exception e) {};
        }

    }

    @LargeTest
    public void testSmd() {
        RequestsFactory factory = mock(RequestsFactory.class);

        SignInRequest signInRequest = mock(SignInRequest.class);
        GetEventsRequest getEventsRequest = new GetEventsRequest();

        SpiceManager spiceManager = new MyManager(MyOkHttpSpiceService.class);
        loginActivity.setSpiceManager(spiceManager);



        try {
            when(signInRequest.loadDataFromNetwork()).thenReturn(new SignInResponse(200, "OK"));
        }
        catch (Exception e) {
            Log.d("MUST NOT HAVE EXCEPTION", "MUST NOT HAVE EXCEPTION");
        }

        when(factory.signInRequest(anyString(), anyString())).thenReturn(signInRequest);
        when(factory.getEventsRequest()).thenReturn(getEventsRequest);

        Network.setRequestsFactory(factory);

        Espresso.onView(ViewMatchers.withId(R.id.userName)).perform(ViewActions.typeText("username"));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("password"));
        Espresso.onView(ViewMatchers.withId(R.id.btnSignIn)).perform(ViewActions.click());




        Mockito.verify(factory).signInRequest("username","password");

    }
}
