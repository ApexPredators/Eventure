package com.example.hai.eventfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


public class myFoldingCellListAdapter extends ArrayAdapter<Event> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    Logger log = Logger.getAnonymousLogger();

    //Event myEvent = new Event();

    public myFoldingCellListAdapter(Context context, int resource) {
        super(context, resource);
    }


    public myFoldingCellListAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);

        //myEvent.requestEvent();

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View v =  super.getView(position, convertView, parent);
        FoldingCell v = (FoldingCell) convertView;
        ViewHolder viewHolder;

        Event eachEvent = getItem(position);

        if(v == null) {
            log.info("cell isn't null");
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = (FoldingCell) inflater.inflate(R.layout.cell, parent, false);


            viewHolder.price = (TextView) v.findViewById(R.id.title_price);
            viewHolder.date = (TextView) v.findViewById(R.id.title_date);
            viewHolder.time = (TextView) v.findViewById(R.id.title_time);
            viewHolder.name = (TextView) v.findViewById(R.id.title_name);
            viewHolder.address = (TextView) v.findViewById(R.id.title_address);
            viewHolder.ratingLabel = (TextView) v.findViewById(R.id.title_ratinglabel);
            viewHolder.ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            viewHolder.timeLabel = (TextView) v.findViewById(R.id.title_timeLabel2);
            viewHolder.eventTypeLabel = (TextView) v.findViewById(R.id.eventTypeLabel);
            viewHolder.eventType = (TextView) v.findViewById(R.id.eventType);

            viewHolder.eventName = (TextView) v.findViewById(R.id.content_title);
            v.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                v.unfold(true);
            } else {
                v.fold(true);
            }
            log.info("cell is null");
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.eventName.setText(eachEvent.eventName);

        return v;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }



    private static class ViewHolder{
        TextView price;
        TextView date;
        TextView time;
        TextView name;
        TextView address;
        TextView ratingLabel;
        RatingBar ratingBar;
        TextView timeLabel;
        TextView eventTypeLabel;
        TextView eventType;

        TextView eventName;


    }
}
