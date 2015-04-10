package com.Android.ICreated;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Android.ICreated.Activity.EventShowActivity;

/**
 * Created by Mikhail on 03.04.2015.
 */
public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder>
{
    private String[] itemsData;
    private Storage storage;
    private Context context;

    public EventsListAdapter(String[] itemsData, Context context)
    {
        this.itemsData = itemsData;
        this.context = context;
        this.storage = (Storage) context.getApplicationContext();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.txtViewTitle.setText(itemsData[position]);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_elem, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txtViewTitle;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.tv_recycler_item);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            storage.setCurLatLng(storage.getEvent(getAdapterPosition()).getLatLng());
            Intent intent = new Intent(context, EventShowActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}
