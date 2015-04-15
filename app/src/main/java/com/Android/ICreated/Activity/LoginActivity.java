package com.Android.ICreated.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.Android.ICreated.R;
import com.Android.ICreated.ServerAPI;
import com.Android.ICreated.Storage;
import android.support.v7.app.ActionBarActivity;
import com.Android.ICreated.network.MyOkHttpSpiceService;
import com.Android.ICreated.network.requests.RegisterRequest;
import com.Android.ICreated.network.requests.SignInRequest;
import com.Android.ICreated.network.requests.UserInfoRequest;
import com.Android.ICreated.network.responses.RegisterResponse;
import com.Android.ICreated.network.responses.SignInResponse;
import com.Android.ICreated.network.responses.UserInfoResponse;
import com.Android.ICreated.network.responses.models.UserInfoModel;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Филипп on 07.10.2014.
 */

public class LoginActivity extends ActionBarActivity
{

    EditText userName;
    EditText password;
    EditText passwordConfirm;
    Storage storage;

    /////////////////////////////////for test//////////////////////////////////////
    private SpiceManager spiceManager = new SpiceManager(MyOkHttpSpiceService.class);
    //////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        showStartPage();
        toolbarInit();
    }
///////////////////////////////for test/////////////////////////////////////////
    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
        storage = (Storage) getApplication();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }
///////////////////////////////////////////////////////////////////////////////////
    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.bar_icon);
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

        ServerAPI.setUser(userName.getText().toString(), password.getText().toString(), passwordConfirm.getText().toString());
        ServerAPI.Response response = ServerAPI.RegistrationNewUser();
        RegisterRequest request = new RegisterRequest(userName.getText().toString(), password.getText().toString(), passwordConfirm.getText().toString());
        /////////////////////////////////////////////////////////////////////
        spiceManager.execute(request, new RegisterRequestListener());
        ////////////////////////////////////////////////////////////////////
        if (response.type.equals(ServerAPI.ResponseType.SUCCES)) {
            signIn();
        }

    }

    private final class RegisterRequestListener implements RequestListener<RegisterResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("REQUEST FAIL", spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(RegisterResponse result) {
            RegisterResponse test = result;
        }
    }

    private final class SignInRequestListener implements RequestListener<SignInResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("REQUEST FAIL", spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(SignInResponse result) {
            SignInResponse test = result;
        }
    }


    private final class UserInfoListener implements RequestListener<UserInfoResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(LoginActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("REQUEST FAIL", spiceException.getMessage());
        }

        @Override
        public void onRequestSuccess(UserInfoResponse result) {
            UserInfoResponse test = result;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        String result = resultTextView.getText().toString();
//        if (!TextUtils.isEmpty(result)) {
//            outState.putString(KEY_RESULT, result);
//        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        spiceManager.addListenerIfPending(RegisterResponse.class, null, new RegisterRequestListener());
//
//        if (savedInstanceState.containsKey(KEY_RESULT)) {
//            String result = savedInstanceState.getString(KEY_RESULT);
//            resultTextView.setText(result);
//        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void signIn() {
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        ServerAPI.setUser(userName.getText().toString(), password.getText().toString());
        //////////////////////////////////////
        SignInRequest request = new SignInRequest(userName.getText().toString(), password.getText().toString());
        spiceManager.execute(request, new SignInRequestListener());
        /////////////////////////////////////

        //
        // for test
        spiceManager.execute(new UserInfoRequest(), new UserInfoListener());
        //
        if (ServerAPI.signIn()) {
            ToMap();
        }
        else {
            Log.d("StartPageActivity", "YOU SHALL NOT PASS");
        }

    }
}
