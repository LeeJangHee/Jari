package com.example.jari.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;

import java.util.ArrayList;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ItemViewHolder> {
    private ArrayList<HomeMenuItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private HomeMenuItem itemView;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_home_menu_listitem, parent, false);
        itemView = new HomeMenuItem();
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

    public void addItem(HomeMenuItem homeMenuItem) {
        mRecyclerViewItemRecycler.add(homeMenuItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private TextView tv_reservation;

        private HomeMenuItem homeMenuItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ig_icon = itemView.findViewById(R.id.image_listView);
            tv_title = itemView.findViewById(R.id.title_listView);
            tv_address = itemView.findViewById(R.id.address_listView);
            tv_reservation = itemView.findViewById(R.id.reservation_listView);
        }

        public void onBind(HomeMenuItem homeMenuItem) {
            this.homeMenuItem = homeMenuItem;

            ig_icon.setImageResource(homeMenuItem.getIconId());
            tv_title.setText(homeMenuItem.getTitleStr());
            tv_address.setText(homeMenuItem.getAddressStr());
            tv_reservation.setText(homeMenuItem.getReservationStr());

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
                    Toast.makeText(context, "TITLE : " + homeMenuItem.getTitleStr() + "\nAddress : " + homeMenuItem.getAddressStr() +
                            "\nReservation : " + homeMenuItem.getReservationStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.title_listView:
                    Toast.makeText(context, homeMenuItem.getTitleStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.address_listView:
                    Toast.makeText(context, homeMenuItem.getAddressStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.reservation_listView:
                    Toast.makeText(context, homeMenuItem.getReservationStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.image_listView:
                    Toast.makeText(context, homeMenuItem.getIconId() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
