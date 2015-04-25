package com.Android.ICreated.network;

import android.util.Log;
import com.Android.ICreated.Event;
import com.Android.ICreated.network.responses.models.EventModel;
import com.google.android.gms.maps.model.LatLng;
import org.apache.commons.lang3.NotImplementedException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Филипп on 19.04.2015.
 */
public final class Converter {
    public static String CalendarToString(Calendar calendar) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        return formatter.format(date);

    }

    public static Calendar StringToCalendar(String strDate) {
        Calendar cal = null;

        if (strDate != null) {
            try {
                strDate = strDate.replace("T", " ");
                DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
                Date date = formatter.parse(strDate);
                cal = Calendar.getInstance();
                cal.setTime(date);
            } catch (Exception e) {
                Log.d("Converter", "convertStringDateToCalendar " + e.getMessage());
            }
        }
        return cal;
    }

    public static Event EventModelToEvent(EventModel eventModel) {
        Event event = new Event();
        event.setDescription(eventModel.getDescription());
        event.setLatLng(new LatLng(Double.parseDouble(eventModel.getLatitude()), Double.parseDouble(eventModel.getLongitude())));
        event.setCategory(1);// заглушка, на сервере пока не реализованы категории
        event.setTime(StringToCalendar(eventModel.getEventDate()));
        return event;
    }

    public static ArrayList<Event> EventModelsToEvents(EventModel[] eventModels) {
        ArrayList<Event> events = new ArrayList<>();
        for (EventModel eventModel: eventModels) {
            events.add(EventModelToEvent(eventModel));
        }
        return events;
    }

}
