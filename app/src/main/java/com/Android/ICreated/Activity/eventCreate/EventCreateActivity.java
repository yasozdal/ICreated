package com.Android.ICreated.Activity.eventCreate;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.Android.ICreated.*;
import com.Android.ICreated.Activity.selectLocation.SelectLocationActivity;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.google.android.gms.maps.model.LatLng;
import java.util.Calendar;

/**
 * Created by Ник on 05.10.2014.
 */
public class EventCreateActivity extends AppCompatActivity implements TextWatcher
{
    final String TAG_WORKER = "TAG_WORKER";

    Event event;
    EditText etDescription;
    TextView btnCategory, btnLocation, btnDate, btnPhoto, btnLock;
    TextView tvCategory, tvLocation, tvDate, tvPhoto, tvLock;
    TextView tvCategoryIcon, tvLocationIcon, tvDateIcon, tvPhotoIcon, tvLockIcon, equalizer;
    final int DIALOG_CATEGORY = 1;
    final int DIALOG_TIME = 2;
    final int DIALOG_PHOTO = 3;
    String[] categories, place, photo;
    int other_category;
    boolean isBtnSaveEnabledByDescription;
    boolean isBtnSaveEnabledByLocation;
    Typeface tf;
    Menu barMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet)
        {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int screenWidth = (int) (metrics.widthPixels * 0.90);
            int screenHeight = (int) (metrics.heightPixels * 0.50);
            getWindow().setLayout(screenWidth, screenHeight);
        }

        tf = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.icon_font));
        setContentView(R.layout.create_event);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDescription.addTextChangedListener(this);
        btnInit();
        categories = getResources().getStringArray(R.array.categories);
        place = getResources().getStringArray(R.array.place);
        photo = getResources().getStringArray(R.array.photo);
        isBtnSaveEnabledByDescription = false;
        other_category = categories.length - 1;

        initWorkerFragment();

        if (event.getLatLng() == null)
        {
            isBtnSaveEnabledByLocation = false;
        }
        else
        {
            showLocation();
        }

        Toolbar toolbar = toolbarInit();
    }

    private void initWorkerFragment()
    {
        final EventCreateWorkerFragment retainedWorkerFragment =
                (EventCreateWorkerFragment) getFragmentManager().findFragmentByTag(TAG_WORKER);

        if (retainedWorkerFragment != null)
        {
            event = retainedWorkerFragment.getEvent();
            restoreFromEvent();
        } else
        {
            final EventCreateWorkerFragment workerFragment = new EventCreateWorkerFragment();

            getFragmentManager().beginTransaction()
                    .add(workerFragment, TAG_WORKER)
                    .commit();

            event = workerFragment.getEvent();
            event.setLatLng((LatLng)getIntent().getParcelableExtra("LatLng"));
            if (event.getLatLng() != null)
            {
                showLocation();
            }
        }
    }

    private void restoreFromEvent()
    {
        if (event.getTime() != null)
        {
            showDate();
        }
        if (event.getLatLng() != null)
        {
            showLocation();
        }
        if (event.getCategory() != -1)
        {
            showCategory();
        }
        if (event.getLockType() != null)
        {
            locking(null);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    ///////////////////////////////buttons initialization

    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle(R.string.new_event);
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        return toolbar;
    }

    private void btnInit()
    {
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
            dpd.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.set_date), dpd);

            dpd.show();
        }
        else
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            switch (id) {
                case DIALOG_CATEGORY:
                    adb.setTitle(R.string.dialog_category);
                    adb.setSingleChoiceItems(categories, event.getCategory(), dialogClickListener);
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
        private boolean isNewDate (int pickedYear, int pickedMonth, int pickedDay) {
            return (event.getTime() == null)

                    ||

                    ((event.getTime().get(Calendar.YEAR) != pickedYear)
                    &&
                    (event.getTime().get(Calendar.MONTH) != pickedMonth)
                    &&
                    (event.getTime().get(Calendar.DAY_OF_MONTH) != pickedDay));
        }

        @Override
        public void onDateSet(DatePicker view, int pickedYear, int pickedMonth, int pickedDay)
        {
            if (isNewDate(pickedYear, pickedMonth, pickedDay)) {
                event.setTime(Calendar.getInstance());
                event.getTime().set(Calendar.YEAR, pickedYear);
                event.getTime().set(Calendar.MONTH, pickedMonth);
                event.getTime().set(Calendar.DAY_OF_MONTH, pickedDay);

                callTimePicker();
            }
        }
    };

    private void callTimePicker()
    {
        Calendar currTime = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(EventCreateActivity.this, timeCallBack,
                                                    currTime.get(Calendar.HOUR_OF_DAY),
                                                    currTime.get(Calendar.MINUTE), true);
        tpd.setTitle(R.string.dialog_time);
        tpd.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.set_time), tpd);

        tpd.show();
    }

    TimePickerDialog.OnTimeSetListener timeCallBack = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(TimePicker view, int pickedHour, int pickedMinute)
        {
            Calendar currTime = Calendar.getInstance();
            if (event.getTime().get(Calendar.YEAR) == currTime.get(Calendar.YEAR) &&
                event.getTime().get(Calendar.MONTH) == currTime.get(Calendar.MONTH) &&
                event.getTime().get(Calendar.DAY_OF_MONTH) == currTime.get(Calendar.DAY_OF_MONTH) &&
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
                event.getTime().set(Calendar.HOUR_OF_DAY, pickedHour);
                event.getTime().set(Calendar.MINUTE, pickedMinute);
                showDate();
            }
        }
    };

    private void showDate()
    {
        String hourView = "";
        String minuteView = "";
        btnDate.setTextColor(getResources().getColor(R.color.main));
        if (event.getTime().get(Calendar.HOUR_OF_DAY) < 10) { hourView = "0"; }
        if (event.getTime().get(Calendar.MINUTE) < 10) { minuteView = "0"; }
        tvDate.setText(event.getTime().get(Calendar.DAY_OF_MONTH) + "." + event.getTime().get(Calendar.MONTH)
                + "." + event.getTime().get(Calendar.YEAR) + "   " + hourView + event.getTime().get(Calendar.HOUR_OF_DAY)
                + ":" + minuteView + event.getTime().get(Calendar.MINUTE));
        tvDateIcon.setVisibility(View.VISIBLE);
        tvDate.setVisibility(View.VISIBLE);
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
            event.setCategory(lv.getCheckedItemPosition());
            showCategory();
        }
    };

    private void showCategory()
    {
        TypedArray cat = getResources().obtainTypedArray(R.array.categories);
        btnCategory.setTextColor(getResources().getColor(R.color.main));
        if (event.getCategory() == other_category)
        {
            btnCategory.setText(getResources().getString(R.string.tag));
            tvCategoryIcon.setText(getResources().getString(R.string.tag));
        }
        else
        {
            btnCategory.setText(getResources().getString(R.string.category));
            tvCategoryIcon.setText(getResources().getString(R.string.category));
        }
        tvCategory.setText(cat.getString(event.getCategory()));
        tvCategoryIcon.setVisibility(View.VISIBLE);
        tvCategory.setVisibility(View.VISIBLE);
    }

////////////////////////////////location

    public void chooseLocation(View view)
    {
        Intent intent = new Intent(this, SelectLocationActivity.class);
        intent.putExtra("LatLng", event.getLatLng());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
        if (data == null) {return;}
        double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            event.setLatLng(new LatLng(latitude, longitude));
        showLocation();

        onPrepareOptionsMenu(barMenu);
    }

    private void showLocation()
    {
        isBtnSaveEnabledByLocation = true;
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
                supportNavigateUpTo(new Intent(this, EventsShowActivity.class));
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
        if ((event.getLockType() == Event.LockType.PRIVATE))
        {
            event.setLockType(Event.LockType.PUBLIC);
            btnLock.setText(getResources().getString(R.string.unlock));
            tvLockIcon.setText(getResources().getString(R.string.unlock));
            tvLock.setText(getResources().getString(R.string.for_all_event));
        }
        else
        {
            event.setLockType(Event.LockType.PRIVATE);
            btnLock.setText(getResources().getString(R.string.lock));
            tvLockIcon.setText(getResources().getString(R.string.lock));
            tvLock.setText(getResources().getString(R.string.friends_event));
            tvLock.setText(getResources().getString(R.string.friends_event));
        }
        tvLockIcon.setVisibility(View.VISIBLE);
        tvLock.setVisibility(View.VISIBLE);
    }

////////////////////////////////event saving

    public void saving()
    {
        event.setDescription(etDescription.getText().toString());
        if (event.getTime() == null)
        {
            event.setTime(Calendar.getInstance());
        }
        if (event.getCategory() == -1)
        {
            event.setCategory(other_category);
        }
        Intent intent = new Intent();
        intent.putExtra("Event", event);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
