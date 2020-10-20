package com.example.jari.home;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

        private HomeMenuItem homeMenuItem;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            ig_icon = itemView.findViewById(R.id.home_listView_image);
            tv_title = itemView.findViewById(R.id.home_listView_title);
            tv_address = itemView.findViewById(R.id.home_listView_address);
            tv_phone = itemView.findViewById(R.id.home_listView_phone);
        }

        public void onBind(HomeMenuItem homeMenuItem) {
            this.homeMenuItem = homeMenuItem;

            ig_icon.setImageResource(homeMenuItem.getIconId());
            tv_title.setText(homeMenuItem.getTitleStr());
            tv_address.setText(homeMenuItem.getAddressStr());
            tv_phone.setText(homeMenuItem.getPhoneStr());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_list:
                    Toast.makeText(context, "TITLE : " + homeMenuItem.getTitleStr() + "\nAddress : " + homeMenuItem.getAddressStr() +
                            "\nReservation : " + homeMenuItem.getPhoneStr(), Toast.LENGTH_SHORT).show();
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
            mainActivity.replaceFragment(fragment);
            mainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFrag, currentName));
            mainActivity.toolbar_title.setText(name);
            ActionBar actionBar = mainActivity.getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);

        }
    }
}
