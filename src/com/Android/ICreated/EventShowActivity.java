package com.Android.ICreated;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Филипп on 05.10.2014.
 */
public class EventShowActivity extends Activity {
    Storage storage;
    ActionBar actionBar;

    private void eventShow()
    {
        Event event = storage.getEvent(storage.getCurLatLng());
        TextView EventTittle = (TextView) findViewById(R.id.EventTittle);
        TextView EventDescription = (TextView) findViewById(R.id.EventDescription);

        if (event == null)
        {
            actionBar.setTitle("Ошибка");
            EventTittle.setText("Ой, событие не найдено :(");
        }
        else
        {
            actionBar.setTitle(event.getTitle());
            EventTittle.setText(event.getTitle());
            EventDescription.setText(event.getDescription());
        }


        Button CloseButton = (Button) findViewById(R.id.CloseButton);
        View.OnClickListener oclCloseButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        CloseButton.setOnClickListener(oclCloseButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        storage = (Storage) getApplication();
        eventShow();

    }
}