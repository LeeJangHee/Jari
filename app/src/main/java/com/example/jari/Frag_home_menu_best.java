package com.example.jari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Frag_home_menu_best extends Fragment {
    private View view;
    private HomeMenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull final ViewGroup container,
                             @NonNull Bundle saveInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_home_menu_best, container, false);

        init();
        getData();

        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new HomeMenuAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        List<String> listTitle = Arrays.asList("가게1", "가게2", "가게3", "가게4", "가게5");
        List<String> listAddress = Arrays.asList("address1", "address2", "address3", "address4", "address5");
        List<String> listReservation = Arrays.asList("ok", "ok", "ok", "no", "no");
        List<Integer> listIcon = Arrays.asList(R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        for(int i = 0 ; i < listTitle.size(); i++){
            HomeMenuItem homeMenuItem = new HomeMenuItem();

            homeMenuItem.setTitleStr(listTitle.get(i));
            homeMenuItem.setAddressStr(listAddress.get(i));
            homeMenuItem.setReservationStr(listReservation.get(i));
            homeMenuItem.setIconId(listIcon.get(i));

            adapter.addItem(homeMenuItem);
        }
        adapter.notifyDataSetChanged();
    }
}

