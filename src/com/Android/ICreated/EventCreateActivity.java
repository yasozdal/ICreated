package com.Android.ICreated;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Ник on 05.10.2014.
 */
public class EventCreateActivity extends Activity implements TextWatcher
{
    Storage storage;
    EditText etTitle;
    EditText etDescription;
    Button btnSave;
    String title;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Новое событие");
        actionBar.setDisplayHomeAsUpEnabled(true);
        storage = (Storage)getApplication();
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btnSave = (Button) findViewById(R.id.btnSave);
        etTitle.addTextChangedListener(this);
        etDescription.addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s)
    {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        if (title.matches("") || description.matches(("")))
        {
            btnSave.setEnabled(false);
        }
        else
        {
            btnSave.setEnabled(true);
        }
    }


    public void finish(View view)
    {
        finish();
    }


    public void saving(View view)
    {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        Event event = new Event(Calendar.getInstance(), storage.getCurLatLng(), title, description);
        storage.addEvent(event);
        finish();
    }

}
