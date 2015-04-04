package com.Android.ICreated.Activity;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.Android.ICreated.DrawerAdapter;
import com.Android.ICreated.Event;
import com.Android.ICreated.R;
import com.Android.ICreated.Storage;

import java.util.Calendar;

/**
 * Created by Филипп on 05.10.2014.
 */
public class EventShowActivity extends ActionBarActivity
{
    Storage storage;
    ActionBar actionBar;
    String[] drawerTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;
    TextView tvCategory, tvLocation, tvDate, tvPhoto, tvLock;
    TextView tvCategoryIcon, tvLocationIcon, tvDateIcon, tvPhotoIcon, tvLockIcon, equalizer;
    String[] categories;
    int other_category, selected_category;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        storage = (Storage) getApplication();
        tf = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.icon_font));
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategoryIcon = (TextView) findViewById(R.id.tvCategoryIcon);
        tvCategory.setTypeface(tf);
        tvCategoryIcon.setTypeface(tf);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocationIcon = (TextView) findViewById(R.id.tvLocationIcon);
        tvLocation.setTypeface(tf);
        tvLocationIcon.setTypeface(tf);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDateIcon = (TextView) findViewById(R.id.tvDateIcon);
        tvDate.setTypeface(tf);
        tvDateIcon.setTypeface(tf);
        tvPhoto = (TextView) findViewById(R.id.tvPhoto);
        tvPhotoIcon = (TextView) findViewById(R.id.tvPhotoIcon);
        tvPhoto.setTypeface(tf);
        tvPhotoIcon.setTypeface(tf);
        tvLock = (TextView) findViewById(R.id.tvLock);
        tvLockIcon = (TextView) findViewById(R.id.tvLockIcon);
        tvLock.setTypeface(tf);
        tvLockIcon.setTypeface(tf);
        equalizer = (TextView) findViewById(R.id.equalizer);
        equalizer.setTypeface(tf);
        categories = getResources().getStringArray(R.array.categories);
        other_category = categories.length - 1;
        Toolbar toolbar = toolbarInit();
        if (toolbar != null)
        {
            drawerInit();
        }
        eventShow();
    }

    private Toolbar toolbarInit()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle(R.string.show_event);
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.bar_icon);
        }

        return toolbar;
    }

    private void eventShow()
    {
        Event event = storage.getEvent(storage.getCurLatLng());
        String hourView = "";
        String minuteView = "";
        TypedArray cat = getResources().obtainTypedArray(R.array.categories);
        TextView EventDescription = (TextView) findViewById(R.id.EventDescription);

        if (event == null)
        {
            getSupportActionBar().setTitle("Ошибка");
        }
        else
        {
            Calendar time = event.getTime();
            int day = time.get(Calendar.DAY_OF_MONTH);
            int month = time.get(Calendar.MONTH);
            int year = time.get(Calendar.YEAR);
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);
            EventDescription.setText(event.getDescription());

            selected_category = event.getCategory();
            if (selected_category == other_category)
            {
                tvCategoryIcon.setText(getResources().getString(R.string.tag));
            }
            else
            {
                tvCategoryIcon.setText(getResources().getString(R.string.category));
            }
            tvCategory.setText(cat.getString(selected_category));

            tvLocation.setText("Курлык");

            tvDate.setText(day + "." + month + "." + year + "   " + hourView + hour + ":" + minuteView + minute);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void drawerInit()
    {
        drawerTitles = getResources().getStringArray(R.array.drawer_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        String[] icons = getResources().getStringArray(R.array.drawer_icons);

        drawerList.setAdapter(new DrawerAdapter(this, R.layout.drawer_list_elem, R.id.tvTitle, R.id.tvIcon, drawerTitles, icons, getResources().getString(R.string.menu_font)));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.events)
        {

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.show_event);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
    }
}