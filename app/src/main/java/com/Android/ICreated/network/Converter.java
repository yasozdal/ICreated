package com.Android.ICreated.network;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ������ on 19.04.2015.
 */
public final class Converter {
    public static String CalendarToString(Calendar calendar) {

//        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);//�������� ������� ����� ��������� �� �������, � ���� �� ���������� ������ ������� �� ��������
        //Mon, 20 Apr 2015 00:09:41 GMT+03:00 ��������
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        return formatter.format(date);

    }
}
