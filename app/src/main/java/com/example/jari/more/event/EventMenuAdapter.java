package com.example.jari.more.event;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.MainActivity;
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_more_event_listitem, parent, false);
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

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SelectEvent {

        Context context;

        private TextView tv_title;
        private TextView tv_date;
        private String str_content;
        private EventMenuItem eventMenuItem;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            tv_title = (TextView) itemView.findViewById(R.id.more_event_title);
            tv_date = (TextView) itemView.findViewById(R.id.more_event_date);
        }

        public void onBind(EventMenuItem eventMenuItem) {
            this.eventMenuItem = eventMenuItem;

            tv_title.setText(eventMenuItem.getTitleStr());
            tv_date.setText(eventMenuItem.getDateStr());
            str_content = eventMenuItem.getContentStr();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.event_item_linear:
                    // 누르면 가게의 이름과 함께 거기에 맞는 조회 결과가 나와야함
                    onClickEvent(tv_title.getText().toString(),
                            tv_date.getText().toString(),
                            str_content,
                            new Frag_more_event_content());
                    break;
            }
        }

        @Override
        public void onClickEvent(String title, String date, String content, Fragment fragment) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment currentFrag = mainActivity.manager.findFragmentById(R.id.main_layout);
            String currentName = mainActivity.toolbarMain_title;

            // fragment <--> fragment 데이터 교환 방법
            FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_layout,
                    Frag_more_event_content.newInstance(title, date, content));
            fragmentTransaction.commit();

            // 뒤로가기 스택에 플레그먼트, 제목 저장
            mainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFrag, currentName));
        }
    }
}
