package com.Android.ICreated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mikhail on 20.10.2014.
 */
public class DBHelper extends SQLiteOpenHelper
{
    String name;

    public DBHelper(Context context, String name)
    {
        super(context, name, null, 1);
        this.name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        switch (name)
        {
            case EventsDataBase.NAME:
                db.execSQL("create table " + EventsDataBase.NAME + "("
                        + EventsDataBase.ID + " integer primary key autoincrement,"
                        + EventsDataBase.DESCRIPTION + " text,"
                        + EventsDataBase.TIME + " long,"
                        + EventsDataBase.LATITUDE + " double,"
                        + EventsDataBase.LONGITUDE + " double,"
                        + EventsDataBase.CATEGORY + " int" + ");");
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
