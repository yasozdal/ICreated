package com.Android.ICreated;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Mikhail on 12.10.2014.
 */
class CustomAdapter extends ArrayAdapter<CharSequence>
{

    Context context;
    int layoutResourceId;
    CharSequence data[] = null;
    Typeface tf;
    int textViewId;

    public CustomAdapter(Context context, int layoutResourceId, int textViewId,  CharSequence[] data, String FONT)
    {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.textViewId = textViewId;
        tf = Typeface.createFromAsset(context.getAssets(), FONT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(layoutResourceId, null);
        TextView tv = (TextView) v.findViewById(textViewId);
        tv.setText(data[position]);
        tv.setTypeface(tf);

        return v;
    }
}