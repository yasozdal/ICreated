package com.Android.ICreated.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.Android.ICreated.R;
import com.Android.ICreated.ServerAPI;
import android.support.v7.app.ActionBarActivity;
import com.Android.ICreated.network.MyOkHttpSpiceService;
import com.Android.ICreated.network.requests.AddEventRequest;
import com.Android.ICreated.network.requests.GetEventsRequest;
import com.Android.ICreated.network.requests.RegisterRequest;
import com.Android.ICreated.network.requests.SignInRequest;
import com.Android.ICreated.network.responses.AddEventResponse;
import com.Android.ICreated.network.responses.GetEventsResponse;
import com.Android.ICreated.network.responses.RegisterResponse;
import com.Android.ICreated.network.responses.SignInResponse;
import com.Android.ICreated.network.responses.models.EventModel;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Calendar;

/**
 * Created by Филипп on 07.10.2014.
 */

public class LoginActivity extends ActionBarActivity
{

    EditText userName;
    EditText password;
    EditText passwordConfirm;
    private SpiceManager spiceManager = new SpiceManager(MyOkHttpSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        showStartPage();
        toolbarInit();
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
        }

        return toolbar;
    }

    private void showStartPage()
    {
        Button btnToMap = (Button) findViewById(R.id.btnToMap);
        View.OnClickListener oclBtnToMapButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToMap();
            }
        };
        btnToMap.setOnClickListener(oclBtnToMapButton);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        View.OnClickListener oclBtnRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        };
        btnRegister.setOnClickListener(oclBtnRegister);

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        View.OnClickListener oclBtnSignIn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        };
        btnSignIn.setOnClickListener(oclBtnSignIn);


    }

    private void ToMap()
    {
        Intent intent = new Intent(this, EventsShowActivity.class);
        startActivity(intent);
        finish();
    }

    private void Register() {

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);

        RegisterRequest request = new RegisterRequest(userName.getText().toString(), password.getText().toString(), passwordConfirm.getText().toString());
        spiceManager.execute(request, new RegisterRequestListener());

    }

    private final class RegisterRequestListener implements RequestListener<RegisterResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("REQUEST FAIL", spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(RegisterResponse result) {
            if (result.getStatusCode() == 200) {
                signIn();
            }
            else {
                // не смогли зарегестрироваться
            }

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        spiceManager.addListenerIfPending(RegisterResponse.class, null, new RegisterRequestListener());
        spiceManager.addListenerIfPending(SignInResponse.class, null, new SignInRequestListener());
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void signIn() {
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        spiceManager.execute(new SignInRequest(userName.getText().toString(), password.getText().toString()),new SignInRequestListener());
//        spiceManager.execute(new AddEventRequest("32", "32", "Hello, Misha, from Doneck", Calendar.getInstance()), new AddEventRequestListener());
//        spiceManager.execute(new GetEventsRequest(), new GetEventsRequestListener());

    }
    private final class SignInRequestListener implements RequestListener<SignInResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("REQUEST FAIL", spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(SignInResponse result) {
            if (result.getStatusCode() == 200) {
                ToMap();
            }
            else {
                //не смогли залогиниться
                Log.d("StartPageActivity", "YOU SHALL NOT PASS");
            }
        }
    }
//    private final class GetEventsRequestListener implements RequestListener<GetEventsResponse> {
//        @Override
//        public void onRequestFailure(SpiceException spiceException) {
//            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.d("REQUEST FAIL", spiceException.getMessage());
//        }
//
//        @Override
//        public void onRequestSuccess(GetEventsResponse result) {
//            EventModel[] a = result.getResponse();
//            EventModel b = a[0];
//        }
//    }


}
