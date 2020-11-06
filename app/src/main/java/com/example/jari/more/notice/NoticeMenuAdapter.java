package com.example.jari.more.notice;

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
import com.example.jari.more.SelectNotice;

import java.util.ArrayList;
import java.util.List;

public class NoticeMenuAdapter extends RecyclerView.Adapter<NoticeMenuAdapter.ItemViewHolder> {
    private List<NoticeMenuItem> mRecyclerViewItemRecycler = new ArrayList<>();
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

    public void addItem(NoticeMenuItem noticeMenuItem) {
        mRecyclerViewItemRecycler.add(noticeMenuItem);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SelectNotice {

        Context context;

        private TextView tv_title;
        private TextView tv_date;
        private String str_content;

        private NoticeMenuItem noticeMenuItem;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            tv_title = (TextView) itemView.findViewById(R.id.more_notice_title);
            tv_date = (TextView) itemView.findViewById(R.id.more_notice_date);
        }

        public void onBind(NoticeMenuItem noticeMenuItem) {
            this.noticeMenuItem = noticeMenuItem;


            tv_title.setText(noticeMenuItem.getTitleStr());
            tv_date.setText(noticeMenuItem.getDateStr());
            str_content = noticeMenuItem.getContentStr();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.notice_item_linear:
                    // 공지사항 누르기
                    onClickNotice(tv_title.getText().toString(),
                            tv_date.getText().toString(),
                            str_content,
                            new Frag_more_notice_content());
                    break;
            }
        }

        @Override
        public void onClickNotice(String title, String date, String content, Fragment fragment) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment currentFrag = mainActivity.manager.findFragmentById(R.id.main_layout);
            String currentName = mainActivity.toolbarMain_title;

            // fragment <--> fragment 데이터 교환 방법
            FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_layout,
                    Frag_more_notice_content.newInstance(title, date, content));
            fragmentTransaction.commit();

            // 뒤로가기 스택에 플레그먼트, 제목 저장
            mainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFrag, currentName));
        }
    }
}
