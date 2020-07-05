package com.example.jari.person.like;

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
import com.example.jari.booking.BookingItem;

import java.util.ArrayList;

public class PersonLikeAdapter extends RecyclerView.Adapter<PersonLikeAdapter.ItemViewHolder> {
    private ArrayList<PersonLikeItem> mRecyclerLikeItem = new ArrayList<>();
    private Context context;
    private PersonLikeItem itemView;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_person_like_listitem, parent, false);
        itemView = new PersonLikeItem();
        context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(mRecyclerLikeItem.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mRecyclerLikeItem.size();
    }

    public void addItem(PersonLikeItem personLikeItem) {
        mRecyclerLikeItem.add(personLikeItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private ImageView ig_heart;

        private PersonLikeItem mPersonLikeItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ig_icon = itemView.findViewById(R.id.like_image_listView);
            tv_title = itemView.findViewById(R.id.like_tv_title);
            tv_address = itemView.findViewById(R.id.like_tv_address);
            ig_heart = itemView.findViewById(R.id.like_iv_heart);
        }

        public void onBind(PersonLikeItem personLikeItem) {
            this.mPersonLikeItem = personLikeItem;

            tv_title.setText(personLikeItem.getLike_titleStr());
            tv_address.setText(personLikeItem.getLike_addressStr());
            ig_icon.setImageResource(personLikeItem.getLike_iconId());
            ig_heart.setImageResource(personLikeItem.getLike_heartId());

            itemView.setOnClickListener(this);
            ig_icon.setOnClickListener(this);
            tv_title.setOnClickListener(this);
            tv_address.setOnClickListener(this);
            ig_heart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.like_linear:
                    Toast.makeText(context, "TITLE : " + mPersonLikeItem.getLike_titleStr() +
                            "\nAddress : " + mPersonLikeItem.getLike_addressStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.like_tv_title:
                    Toast.makeText(context, mPersonLikeItem.getLike_titleStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.like_tv_address:
                    Toast.makeText(context, mPersonLikeItem.getLike_addressStr(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.like_iv_heart:
                    Toast.makeText(context, mPersonLikeItem.getLike_iconId()+"이미지 입니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.like_image_listView:
                    Toast.makeText(context, mPersonLikeItem.getLike_iconId() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
