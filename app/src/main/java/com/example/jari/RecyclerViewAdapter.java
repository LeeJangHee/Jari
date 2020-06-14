package com.example.jari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {
    private ArrayList<RecyclerViewItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private RecyclerViewItem itemView;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_booking_listitem, parent, false);
        itemView = new RecyclerViewItem();
        context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(mRecyclerViewItemRecycler.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItemRecycler.size();
    }

    public void addItem(RecyclerViewItem recyclerViewItem) {
        mRecyclerViewItemRecycler.add(recyclerViewItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private TextView tv_reservation;

        private RecyclerViewItem recyclerViewItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ig_icon = itemView.findViewById(R.id.image_listView);
            tv_title = itemView.findViewById(R.id.title_listView);
            tv_address = itemView.findViewById(R.id.address_listView);
            tv_reservation = itemView.findViewById(R.id.reservation_listView);
        }

        public void onBind(RecyclerViewItem recyclerViewItem) {
            this.recyclerViewItem = recyclerViewItem;

            ig_icon.setImageResource(recyclerViewItem.getIconId());
            tv_title.setText(recyclerViewItem.getTitleStr());
            tv_address.setText(recyclerViewItem.getAddressStr());
            tv_reservation.setText(recyclerViewItem.getReservationStr());

            itemView.setOnClickListener(this);
            ig_icon.setOnClickListener(this);
            tv_title.setOnClickListener(this);
            tv_address.setOnClickListener(this);
            tv_reservation.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_list:
                    Toast.makeText(context, "TITLE : " + recyclerViewItem.getTitleStr() + "\nAddress : " + recyclerViewItem.getAddressStr() +
                            "\nReservation : " + recyclerViewItem.getReservationStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.title_listView:
                    Toast.makeText(context, recyclerViewItem.getTitleStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.address_listView:
                    Toast.makeText(context, recyclerViewItem.getAddressStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.reservation_listView:
                    Toast.makeText(context, recyclerViewItem.getReservationStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.image_listView:
                    Toast.makeText(context, recyclerViewItem.getIconId() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
