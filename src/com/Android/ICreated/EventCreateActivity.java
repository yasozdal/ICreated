package com.Android.ICreated;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.util.Calendar;

/**
 * Created by Ник on 05.10.2014.
 */
public class EventCreateActivity extends Activity implements TextWatcher
{
    Storage storage;
    EditText etDescription;
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;
    TimePicker timePicker;
    DatePicker datePicker;
    TextView btnCategory;
    TextView btnPlace;
    TextView btnDate;
    TextView btnPhoto;
    TextView btnLock;
    RelativeLayout rlDate;
    Button dateSave;
    Button dateCancel;
    int minute = Time.MINUTE;
    int hour = Time.HOUR;
    int day = Time.MONTH_DAY;
    int month = Time.MONTH;
    int year = Time.YEAR;
    final int DIALOG_CATEGORY = 1;
    final int DIALOG_PLACE = 2;
    final int DIALOG_PHOTO = 3;
    String[] categories, place, photo;
    int other_category;
    int selected_category;
    Calendar date = null;
    boolean btnSaveEnabled;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        tf = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.icon_font));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Новое событие");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        storage = (Storage)getApplication();
        etDescription = (EditText) findViewById(R.id.etDescription);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker.setIs24HourView(true);
        etDescription.addTextChangedListener(this);
        btnCategory = (TextView) findViewById(R.id.btnCategory);
        btnCategory.setTypeface(tf);
        btnPlace = (TextView) findViewById(R.id.btnPlace);
        btnPlace.setTypeface(tf);
        btnDate = (TextView) findViewById(R.id.btnDate);
        btnDate.setTypeface(tf);
        btnPhoto = (TextView) findViewById(R.id.btnPhoto);
        btnPhoto.setTypeface(tf);
        btnLock = (TextView) findViewById(R.id.btnLock);
        btnLock.setTypeface(tf);
        rlDate = (RelativeLayout) findViewById(R.id.rlDate);
        dateSave = (Button) findViewById(R.id.dateSave);
        dateCancel = (Button) findViewById(R.id.dateCancel);
        categories = getResources().getStringArray(R.array.categories);
        place = getResources().getStringArray(R.array.place);
        photo = getResources().getStringArray(R.array.photo);
        other_category = categories.length - 1;
        btnSaveEnabled = false;
        drawerInit();
    }

////////////////////////////////dialogs of category, place and photos

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnCategory:
                rlDate.setVisibility(View.INVISIBLE);
                showDialog(DIALOG_CATEGORY);
                break;
            case R.id.btnPlace:
                rlDate.setVisibility(View.INVISIBLE);
                showDialog(DIALOG_PLACE);
                break;
            case R.id.btnPhoto:
                rlDate.setVisibility(View.INVISIBLE);
                showDialog(DIALOG_PHOTO);
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_CATEGORY:
                adb.setTitle("Select category");
                adb.setSingleChoiceItems(categories, other_category, dialogClickListener);
                adb.setPositiveButton("OK", btnOkClickListener);
                break;
            case DIALOG_PLACE:
                adb.setTitle("Select place");
                adb.setItems(place, dialogClickListener);
                break;
            case DIALOG_PHOTO:
                adb.setTitle("Select photo");
                adb.setItems(photo, dialogClickListener);
                break;
            default:
                break;
        }
        return adb.create();
    }

    OnClickListener dialogClickListener = new OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            Log.d("olologs", "which = " + which);
        }
    };

    OnClickListener btnOkClickListener = new OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            ListView lv = ((AlertDialog) dialog).getListView();
            selected_category = lv.getCheckedItemPosition();
            btnCategory.setTextColor(getResources().getColor(R.color.main));
            if (selected_category == other_category)
            {
                btnCategory.setText(getResources().getString(R.string.tag));
            }
            else
            {
                btnCategory.setText(getResources().getString(R.string.category));
            }
        }
    };

////////////////////////////////action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (drawerLayout.isDrawerOpen(drawerList))
        {
            menu.findItem(R.id.btnSaveEvent).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.btnSaveEvent).setVisible(true);
        }
        if (btnSaveEnabled)
        {
            menu.findItem(R.id.btnSaveEvent).setEnabled(true);
            menu.findItem(R.id.btnSaveEvent).setIcon(R.drawable.complete);
        }
        else
        {
            menu.findItem(R.id.btnSaveEvent).setEnabled(false);
            menu.findItem(R.id.btnSaveEvent).setIcon(R.drawable.complete_grey);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void drawerInit()
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new CustomAdapter(this, R.layout.list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.bar_icon, R.string.app_name, R.string.events)
        {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getActionBar().setTitle(R.string.events);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s)
    {
        String description = etDescription.getText().toString();
        if (description.matches(("")))
        {
            btnSaveEnabled = false;
            invalidateOptionsMenu();
        }
        else
        {
            btnSaveEnabled = true;
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.btnSaveEvent:
                saving();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

////////////////////////////////date_changing

    public void add_date (View view)
    {
        if (rlDate.getVisibility() == View.INVISIBLE)
        {
            rlDate.setVisibility(View.VISIBLE);
        }
        else
        {
            rlDate.setVisibility(View.INVISIBLE);
        }
    }

    public void save_date (View view)
    {
        rlDate.setVisibility(View.INVISIBLE);
        minute = timePicker.getCurrentMinute();
        hour = timePicker.getCurrentHour();
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();
        date = Calendar.getInstance();
        date.set(year, month, day, hour, minute); //the same value actually...
        btnDate.setTextColor(getResources().getColor(R.color.main));
    }


    public void cancel_data (View view)
    {
        rlDate.setVisibility(View.INVISIBLE);
    }

////////////////////////////////lock changing

    public void locking (View view)
    {
        btnLock.setTextColor(getResources().getColor(R.color.main));
        String currLock = btnLock.getText().toString();
        if (currLock.matches(getResources().getString(R.string.lock)))
        {
            btnLock.setText(getResources().getString(R.string.unlock));
        }
        else
        {
            btnLock.setText(getResources().getString(R.string.lock));
        }
    }

////////////////////////////////event saving

    public void saving()
    {
        String description = etDescription.getText().toString();
        Event event = new Event(date, storage.getCurLatLng(), "nope", description);
        storage.addEvent(event);
        finish();
    }


}
