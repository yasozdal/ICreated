package com.Android.ICreated;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Филипп on 07.10.2014.
 */
public class StartPageActivity extends Activity{
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
    }

    private void ToMap()
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
