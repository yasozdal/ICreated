package com.Android.ICreated;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by Mikhail on 05.10.2014.
 */
public class Event implements Parcelable
{
    private long id;
    private Calendar time;
    private LatLng latLng;
    private String description;
    private int category;
    private LockType lockType;

    public enum LockType implements Parcelable
    {
        PRIVATE,
        PUBLIC;

        public static final Parcelable.Creator<LockType> CREATOR = new Parcelable.Creator<LockType>()
        {
            @Override
            public LockType createFromParcel(Parcel parcel)
            {
                return LockType.values()[parcel.readInt()];
            }

            @Override
            public LockType[] newArray(int i)
            {
                return new LockType[i];
            }
        };

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i)
        {
            parcel.writeInt(ordinal());
        }
    }

    public Event()
    {
        time = null;
        latLng = null;
        description = null;
        category = -1;
        lockType = null;
    }

    private Event(Parcel parcel)
    {
        id = parcel.readLong();
        time = Calendar.getInstance();
        time.set(Calendar.YEAR, parcel.readInt());
        time.set(Calendar.MONTH, parcel.readInt());
        time.set(Calendar.DAY_OF_MONTH, parcel.readInt());
        time.set(Calendar.HOUR_OF_DAY, parcel.readInt());
        time.set(Calendar.MINUTE, parcel.readInt());
        latLng = parcel.readParcelable(LatLng.class.getClassLoader());
        description = parcel.readString();
        category = parcel.readInt();
        lockType = parcel.readParcelable(LockType.class.getClassLoader());
    }

    public Event(Calendar time, LatLng latLng, String description, int category)
    {
        id = -1;
        this.time = time;
        this.latLng = latLng;
        this.description = description;
        this.category = category;
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>()
    {
        @Override
        public Event createFromParcel(Parcel parcel)
        {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int i)
        {
            return new Event[0];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(id);
        parcel.writeInt(time.get(Calendar.YEAR));
        parcel.writeInt(time.get(Calendar.MONTH));
        parcel.writeInt(time.get(Calendar.DAY_OF_MONTH));
        parcel.writeInt(time.get(Calendar.HOUR_OF_DAY));
        parcel.writeInt(time.get(Calendar.MINUTE));
        parcel.writeParcelable(latLng, 0);
        parcel.writeString(description);
        parcel.writeInt(category);
        parcel.writeParcelable(lockType, 1);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setTime(Calendar time)
    {
        this.time = time;
    }

    public Calendar getTime()
    {
        return time;
    }

    public void setLatLng(LatLng latLng)
    {
        this.latLng = latLng;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public int getCategory()
    {
        return category;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setLockType(LockType type)
    {
        this.lockType = type;
    }

    public LockType getLockType()
    {
        return lockType;
    }
}