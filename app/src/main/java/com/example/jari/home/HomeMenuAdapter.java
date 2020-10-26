package com.example.jari.home;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jari.MainActivity;
import com.example.jari.R;

import java.util.ArrayList;
import java.util.List;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ItemViewHolder> {
    private List<HomeMenuItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private View view;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_home_menu_listitem, parent, false);
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

    public void addItem(HomeMenuItem homeMenuItem) {
        mRecyclerViewItemRecycler.add(homeMenuItem);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SelectStore {

        Context context;

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private TextView tv_phone;
        String str_menu;
        int ig_menu;

        private HomeMenuItem homeMenuItem;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            ig_icon = (ImageView) itemView.findViewById(R.id.home_listView_image);
            tv_title = (TextView) itemView.findViewById(R.id.home_listView_title);
            tv_address = (TextView) itemView.findViewById(R.id.home_listView_address);
            tv_phone = (TextView) itemView.findViewById(R.id.home_listView_phone);
        }

        public void onBind(HomeMenuItem homeMenuItem) {
            this.homeMenuItem = homeMenuItem;

            Glide.with(view).load(homeMenuItem.getIconStr()).into(ig_icon);
//            ig_icon.setImageResource(homeMenuItem.getIconId());
            tv_title.setText(homeMenuItem.getTitleStr());
            tv_address.setText(homeMenuItem.getAddressStr());
            tv_phone.setText(homeMenuItem.getPhoneStr());
//            ig_menu = homeMenuItem.getMenuId();
            str_menu = homeMenuItem.getMenuStr();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_list:
                    // 누르면 가게의 이름과 함께 거기에 맞는 조회 결과가 나와야함
                    onClickStore(homeMenuItem.getTitleStr(), new Frag_home_menu_store());
                    break;
            }
        }

        @Override
        public void onClickStore(String name, Fragment fragment) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment currentFrag = mainActivity.manager.findFragmentById(R.id.main_layout);
            String currentName = mainActivity.toolbarMain_title;
            String str_store_profile = homeMenuItem.getIconStr();
            String str_store_menu = str_menu;

            // fragment <--> fragment 데이터 교환 방법
            FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_layout,
                    Frag_home_menu_store.newInstance(name, str_store_profile, str_store_menu));
            fragmentTransaction.commit();

            // 뒤로가기 스택에 플레그먼트, 제목 저장
            mainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFrag, currentName));
            mainActivity.toolbar_title.setText(name);
            mainActivity.toolbarMain_title = name;
        }


    }
}
