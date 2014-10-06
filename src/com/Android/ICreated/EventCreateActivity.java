package com.Android.ICreated;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Ник on 05.10.2014.
 */
public class EventCreateActivity extends Activity
{
    Storage storage;
    EditText title_text;
    EditText description_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        getActionBar().hide();
        storage = (Storage)getApplication();
        title_text = (EditText) findViewById(R.id.title_text);
        description_text = (EditText) findViewById(R.id.description_text);
    }

    public void finish(View view)
    {
        finish();
    }
    public void saving(View view)
    {
        String title = title_text.getText().toString();
        String description = description_text.getText().toString();
        if (title.matches(""))
        {
            Toast.makeText(this, "Please, enter a title", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.matches(""))
        {
            Toast.makeText(this, "Please, enter a description", Toast.LENGTH_SHORT).show();
            return;
        }
        Event event = new Event(Calendar.getInstance(), storage.getCurLatLng(), title, description);
        storage.addEvent(event);
        finish();
    }

}
