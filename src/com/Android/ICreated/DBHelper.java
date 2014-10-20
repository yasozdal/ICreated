package com.Android.ICreated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mikhail on 20.10.2014.
 */
public class DBHelper extends SQLiteOpenHelper
{
    Context context;

    public DBHelper(Context context)
    {

        super(context, context.getString(R.string.db_name), null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + context.getString(R.string.db_name) + "("
                + "id integer primary key autoincrement,"
                + "description text,"
                + "long time,"
                + "double lat,"
                + "double lng,"
                + "int category" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
