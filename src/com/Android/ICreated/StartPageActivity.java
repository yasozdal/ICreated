package com.Android.ICreated;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Филипп on 07.10.2014.
 */

public class StartPageActivity extends Activity{

    EditText userName;
    EditText password;
    EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
        getActionBar().hide();
        showStartPage();

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
    }

    private void ToMap()
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void Register()
    {
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirm);

        ServerAPI handler = new ServerAPI();
        String response = handler.RegistrationNewUser(userName.getText().toString(), password.getText().toString(), passwordConfirm.getText().toString());
        userName.setText(response);
    }
}
