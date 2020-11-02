package com.example.jari.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jari.R;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ItemViewHolder> {
    private ArrayList<BookingItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private BookingItem itemView;
    private View view;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_booking_listitem, parent, false);
        itemView = new BookingItem();
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

    public void addItem(BookingItem bookingItem) {
        mRecyclerViewItemRecycler.add(bookingItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private TextView tv_phone;
        private Button btn_booking_cancel;

        private BookingItem bookingItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ig_icon = (ImageView) itemView.findViewById(R.id.image_listView);
            tv_title = (TextView) itemView.findViewById(R.id.title_listView);
            tv_address = (TextView) itemView.findViewById(R.id.address_listView);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_listView);
            btn_booking_cancel = (Button) itemView.findViewById(R.id.reservation_cancel_btn);
        }

        public void onBind(BookingItem bookingItem) {
            this.bookingItem = bookingItem;

            Glide.with(view).load(bookingItem.getProfileStr()).into(ig_icon);
            tv_title.setText(bookingItem.getTitleStr());
            tv_address.setText(bookingItem.getAddressStr());
            tv_phone.setText(bookingItem.getPhoneStr());

            btn_booking_cancel.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.booking_linear:
                    Toast.makeText(context, "TITLE : " + bookingItem.getTitleStr() + "\nAddress : " + bookingItem.getAddressStr() +
                            "\nReservation : " + bookingItem.getPhoneStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.reservation_cancel_btn:
                    Toast.makeText(context, "버튼 클릭", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}
