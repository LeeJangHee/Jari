package com.example.jari.more.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;

import java.util.ArrayList;
import java.util.List;

public class EventMenuAdapter extends RecyclerView.Adapter<EventMenuAdapter.ItemViewHolder> {
    private List<EventMenuItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private View view;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_more_notice_listitem, parent, false);
        context = parent.getContext();
        return new ItemViewHolder(view, context);
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

    public void addItem(EventMenuItem noticeMenuItem) {
        mRecyclerViewItemRecycler.add(noticeMenuItem);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;

        private TextView tv_title;

        private EventMenuItem eventMenuItem;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            tv_title = (TextView) itemView.findViewById(R.id.home_listView_title);
        }

        public void onBind(EventMenuItem eventMenuItem) {
            this.eventMenuItem = eventMenuItem;


            tv_title.setText(eventMenuItem.getTitleStr());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_list:
                    // 누르면 가게의 이름과 함께 거기에 맞는 조회 결과가 나와야함
                    break;
            }
        }

    }
}
