package com.Android.ICreated;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Ник on 05.10.2014.
 */
public class EventCreateActivity extends Activity implements TextWatcher
{
    Storage storage;
    EditText title_text;
    EditText description_text;
    Button save_button;
    String title;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        getActionBar().hide();
        storage = (Storage)getApplication();
        title_text = (EditText) findViewById(R.id.title_text);
        description_text = (EditText) findViewById(R.id.description_text);
        save_button = (Button) findViewById(R.id.save_button);
        title_text.addTextChangedListener(this);
        description_text.addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s)
    {
        String title = title_text.getText().toString();
        String description = description_text.getText().toString();
        if (title.matches("") || description.matches(("")))
        {
            save_button.setEnabled(false);
        }
        else
        {
            save_button.setEnabled(true);
        }
    }


    public void finish(View view)
    {
        finish();
    }


    public void saving(View view)
    {
        String title = title_text.getText().toString();
        String description = description_text.getText().toString();
        Event event = new Event(Calendar.getInstance(), storage.getCurLatLng(), title, description);
        storage.addEvent(event);
        finish();
    }

}
