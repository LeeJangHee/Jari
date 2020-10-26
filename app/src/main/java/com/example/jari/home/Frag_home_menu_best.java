package com.example.jari.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;
import com.example.jari.retrofit2.Result;
import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.retrofit2.ServerConnect;
import com.example.jari.retrofit2.Store;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_home_menu_best extends Fragment {
    private View view;
    private HomeMenuAdapter adapter;
    private RetrofitService retrofitService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull final ViewGroup container,
                             @NonNull Bundle saveInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_home_menu_best, container, false);

        init();
        getService();

        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new HomeMenuAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void getService() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        retrofitService.getStoreBest().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreBest();
                    getData(storeList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });


    }

    private void getData(List<Store> retrofitList) {
        List<String> listTitle = new ArrayList<>();
        List<String> listAddress = new ArrayList<>();
        List<String> listPhone = new ArrayList<>();
        List<String> list_icon = new ArrayList<>();
        List<String> list_menu = new ArrayList<>();

        for (Store st : retrofitList) {
            listTitle.add(st.getName());
            listAddress.add(st.getAddress());
            listPhone.add(st.getPhone());
            // url 객체
            list_icon.add(st.getImage_profile());
            list_menu.add(st.getImage_menu());
        }
        for (int i = 0; i < listTitle.size(); i++) {
            HomeMenuItem homeMenuItem = new HomeMenuItem();

            homeMenuItem.setTitleStr(listTitle.get(i));
            homeMenuItem.setAddressStr(listAddress.get(i));
            homeMenuItem.setPhoneStr(listPhone.get(i));
            // url 객체 추가
            homeMenuItem.setIconStr(list_icon.get(i));
            homeMenuItem.setMenuStr(list_menu.get(i));

            adapter.addItem(homeMenuItem);
        }
        adapter.notifyDataSetChanged();
    }
}

