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
    String[] icons = null;
    Typeface tf;
    int textViewId;
    int iconId;

    public CustomAdapter(Context context, int layoutResourceId, int textViewId,  CharSequence[] data, String FONT)
    {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.icons = null;
        this.textViewId = textViewId;
        this.iconId = -1;
        tf = Typeface.createFromAsset(context.getAssets(), FONT);
    }

    public CustomAdapter(Context context, int layoutResourceId, int textViewId, int iconId,  CharSequence[] data, String[] icons, String FONT)
    {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.textViewId = textViewId;
        this.iconId = iconId;
        this.icons = icons;
        tf = Typeface.createFromAsset(context.getAssets(), FONT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(layoutResourceId, null);
        if (icons != null)
        {
            TextView tvIcon = (TextView) v.findViewById(iconId);
            tvIcon.setText(icons[position]);
            tvIcon.setTypeface(Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.icon_font)));
        }
        TextView tvTitle = (TextView) v.findViewById(textViewId);
        tvTitle.setText(data[position]);
        tvTitle.setTypeface(tf);

        return v;
    }
}