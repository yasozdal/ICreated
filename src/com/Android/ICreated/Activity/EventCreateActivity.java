package com.Android.ICreated.Activity;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.Android.ICreated.DrawerAdapter;
import com.Android.ICreated.Event;
import com.Android.ICreated.R;
import com.Android.ICreated.Storage;
import com.google.android.gms.maps.model.LatLng;
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
    TextView btnCategory, btnLocation, btnDate, btnPhoto, btnLock;
    TextView tvCategory, tvLocation, tvDate, tvPhoto, tvLock;
    TextView tvCategoryIcon, tvLocationIcon, tvDateIcon, tvPhotoIcon, tvLockIcon, equalizer;
    int minute, hour, day, month, year;
    final int DIALOG_CATEGORY = 1;
    final int DIALOG_TIME = 2;
    final int DIALOG_PHOTO = 3;
    String[] categories, place, photo;
    int other_category, selected_category;
    Calendar date = null;
    boolean isBtnSaveEnabledByDescription;
    boolean isBtnSaveEnabledByLocation;
    LatLng latLng;
    Typeface tf;
    Menu barMenu;


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
        etDescription.addTextChangedListener(this);
        btnCategory = (TextView) findViewById(R.id.btnCategory);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategoryIcon = (TextView) findViewById(R.id.tvCategoryIcon);
        btnCategory.setTypeface(tf);
        tvCategory.setTypeface(tf);
        tvCategoryIcon.setTypeface(tf);
        btnLocation = (TextView) findViewById(R.id.btnLocation);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocationIcon = (TextView) findViewById(R.id.tvLocationIcon);
        btnLocation.setTypeface(tf);
        tvLocation.setTypeface(tf);
        tvLocationIcon.setTypeface(tf);
        btnDate = (TextView) findViewById(R.id.btnDate);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDateIcon = (TextView) findViewById(R.id.tvDateIcon);
        btnDate.setTypeface(tf);
        tvDate.setTypeface(tf);
        tvDateIcon.setTypeface(tf);
        btnPhoto = (TextView) findViewById(R.id.btnPhoto);
        tvPhoto = (TextView) findViewById(R.id.tvPhoto);
        tvPhotoIcon = (TextView) findViewById(R.id.tvPhotoIcon);
        btnPhoto.setTypeface(tf);
        tvPhoto.setTypeface(tf);
        tvPhotoIcon.setTypeface(tf);
        btnLock = (TextView) findViewById(R.id.btnLock);
        tvLock = (TextView) findViewById(R.id.tvLock);
        tvLockIcon = (TextView) findViewById(R.id.tvLockIcon);
        btnLock.setTypeface(tf);
        tvLock.setTypeface(tf);
        tvLockIcon.setTypeface(tf);
        equalizer = (TextView) findViewById(R.id.equalizer);
        equalizer.setTypeface(tf);
        categories = getResources().getStringArray(R.array.categories);
        place = getResources().getStringArray(R.array.place);
        photo = getResources().getStringArray(R.array.photo);
        other_category = categories.length - 1;
        isBtnSaveEnabledByDescription = false;
        isBtnSaveEnabledByLocation = false;
        selected_category = other_category;
        latLng = storage.getCurLatLng();
        drawerInit();
    }

////////////////////////////////dialogs of category, time and photos

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnCategory:
                showMyDialog(DIALOG_CATEGORY);
                break;
            case R.id.btnDate:
                showMyDialog(DIALOG_TIME);
                break;
            case R.id.btnPhoto:
                showMyDialog(DIALOG_PHOTO);
                break;
            default:
                break;
        }
    }

    protected void showMyDialog(int id)
    {
        if (id == DIALOG_TIME)
        {
            DatePickerDialog dpd = new DatePickerDialog(this, dateCallBack, Calendar.YEAR,
                                                                            Calendar.MONTH,
                                                                            Calendar.DAY_OF_MONTH);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.setTitle(R.string.dialog_date);
            dpd.show();
        }
        else
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            switch (id) {
                case DIALOG_CATEGORY:
                    adb.setTitle(R.string.dialog_category);
                    adb.setSingleChoiceItems(categories, selected_category, dialogClickListener);
                    adb.setPositiveButton(R.string.ok, btnOkClickListener);
                    break;
                case DIALOG_PHOTO:
                    adb.setTitle(R.string.dialog_photo);
                    adb.setItems(photo, dialogClickListener);
                    break;
                default:
                    break;
            }
            AlertDialog current_dialog = adb.create();
            current_dialog.show();
        }
    }

    DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int pickedYear, int pickedMonth, int pickedDay)
        {
            year = pickedYear;
            month = pickedMonth;
            day = pickedDay;

            callTimePicker();
        }
    };

    private void callTimePicker()
    {
        Calendar currTime = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(EventCreateActivity.this, timeCallBack,
                                                    currTime.get(Calendar.HOUR_OF_DAY),
                                                    currTime.get(Calendar.MINUTE), true);
        tpd.setTitle(R.string.dialog_time);
        tpd.show();
    }

    TimePickerDialog.OnTimeSetListener timeCallBack = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int pickedHour, int pickedMinute)
        {
            String hourView = "";
            String minuteView = "";
            Calendar currTime = Calendar.getInstance();
            if (year == currTime.get(Calendar.YEAR) &&
                month == currTime.get(Calendar.MONTH) &&
                day == currTime.get(Calendar.DAY_OF_MONTH) &&
               (pickedHour < currTime.get(Calendar.HOUR_OF_DAY) ||
               (pickedHour == currTime.get(Calendar.HOUR_OF_DAY) &&
                pickedMinute < currTime.get(Calendar.MINUTE))))
            {
                Toast toast = Toast.makeText(getApplicationContext(),R.string.past_event_toast, Toast.LENGTH_SHORT);
                toast.show();
                callTimePicker();
            }
            else
            {
                hour = pickedHour;
                minute = pickedMinute;
                btnDate.setTextColor(getResources().getColor(R.color.main));
                if (hour < 10) { hourView = "0"; }
                if (minute < 10) { minuteView = "0"; }
                tvDate.setText(day + "." + month + "." + year + "   "
                        + hourView + hour + ":" + minuteView + minute);
                tvDateIcon.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
            }
        }
    };

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
            TypedArray cat = getResources().obtainTypedArray(R.array.categories);
            ListView lv = ((AlertDialog) dialog).getListView();
            selected_category = lv.getCheckedItemPosition();
            btnCategory.setTextColor(getResources().getColor(R.color.main));
            if (selected_category == other_category)
            {
                btnCategory.setText(getResources().getString(R.string.tag));
                tvCategoryIcon.setText(getResources().getString(R.string.tag));
                tvCategory.setText(cat.getString(selected_category));
            }
            else
            {
                btnCategory.setText(getResources().getString(R.string.category));
                tvCategoryIcon.setText(getResources().getString(R.string.category));
                tvCategory.setText(cat.getString(selected_category));
            }
            tvCategoryIcon.setVisibility(View.VISIBLE);
            tvCategory.setVisibility(View.VISIBLE);
        }
    };

////////////////////////////////location

    public void chooseLocation(View view)
    {
        Intent intent = new Intent(this, EventSelectLocationActivity.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) {return;}
        double latitude = data.getDoubleExtra("latitude", 0);
        double longitude = data.getDoubleExtra("longitude", 0);
        latLng = new LatLng(latitude, longitude);
        isBtnSaveEnabledByLocation = true;
        storage.setCurLatLng(latLng);
        onPrepareOptionsMenu(barMenu);
        btnLocation.setTextColor(getResources().getColor(R.color.main));
        tvLocation.setText("Курлык");
        tvLocationIcon.setVisibility(View.VISIBLE);
        tvLocation.setVisibility(View.VISIBLE);
    }

////////////////////////////////action bars

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
        barMenu = menu;
        if (drawerLayout.isDrawerOpen(drawerList))
        {
            menu.findItem(R.id.btnSaveEvent).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.btnSaveEvent).setVisible(true);
        }
        if (isBtnSaveEnabledByDescription && isBtnSaveEnabledByLocation)
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

        drawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));

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
        if (description.matches(""))
        {
            isBtnSaveEnabledByDescription = false;
            invalidateOptionsMenu();
        }
        else
        {
            isBtnSaveEnabledByDescription = true;
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

    public void switchToTE(View view)
    {
        etDescription.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etDescription, InputMethodManager.SHOW_IMPLICIT);
    }

////////////////////////////////lock changing

    public void locking (View view)
    {
        btnLock.setTextColor(getResources().getColor(R.color.main));
        String currLock = btnLock.getText().toString();
        if (currLock.matches(getResources().getString(R.string.lock)))
        {
            btnLock.setText(getResources().getString(R.string.unlock));
            tvLockIcon.setText(getResources().getString(R.string.unlock));
            tvLock.setText(getResources().getString(R.string.open_event));
        }
        else
        {
            btnLock.setText(getResources().getString(R.string.lock));
            tvLockIcon.setText(getResources().getString(R.string.lock));
            tvLock.setText(getResources().getString(R.string.friends_event));
        }
        tvLockIcon.setVisibility(View.VISIBLE);
        tvLock.setVisibility(View.VISIBLE);
    }

////////////////////////////////event saving

    public void saving()
    {
        String description = etDescription.getText().toString();
        if (date == null)
        {
            date = Calendar.getInstance();
        }
        if (latLng == null)
        {
            latLng = storage.getCurLatLng();
        }
        Event event = new Event(date, latLng, description, selected_category);
        storage.addEvent(event);
        storage.getEventsFromServer();
        finish();
    }

}
