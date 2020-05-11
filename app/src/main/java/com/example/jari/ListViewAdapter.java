package com.example.jari;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> mlistViewItemList;

    public ListViewAdapter(ArrayList<ListViewItem> listViewItemList) {
        this.mlistViewItemList = listViewItemList;
    }

    @Override
    public int getCount() {
        return mlistViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mlistViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_main, parent, false);

            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image_listView);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.title_listView);
            TextView addressTextView = (TextView) convertView.findViewById(R.id.adress_listView);
            TextView reservationTextView = (TextView) convertView.findViewById(R.id.reservation_listView);

            holder.iconImageView = iconImageView;
            holder.titleTextView = titleTextView;
            holder.addressTextView = addressTextView;
            holder.reservationTextView = reservationTextView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListViewItem listViewItem = mlistViewItemList.get(position);
        holder.titleTextView.setText(listViewItem.getTitleStr());
        holder.addressTextView.setText(listViewItem.getAddressStr());
        holder.reservationTextView.setText(listViewItem.getReservationStr());

        return convertView;
    }

    static class ViewHolder {
        ImageView iconImageView;
        TextView titleTextView ;
        TextView addressTextView;
        TextView reservationTextView;
    }

}
