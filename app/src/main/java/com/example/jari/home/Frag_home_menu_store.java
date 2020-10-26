package com.example.jari.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jari.R;
import com.example.jari.retrofit2.Result;
import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.retrofit2.ServerConnect;
import com.example.jari.retrofit2.StoreInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_home_menu_store extends Fragment {
    View view;
    Context context;
    private RetrofitService retrofitService;
    private static final String STORE_NAME = "name";
    private static final String STORE_PROFILE = "profile";
    private static final String STORE_IG_MENU = "menu";

    private String mStoreName;
    private String mStoreProfile;
    private String mStoreMenu;

    String str_name;
    String str_phone;
    String str_address;
    String str_intro;
    String str_menu_name;
    String str_menu_price;

    TextView tv_name;
    TextView tv_phone;
    TextView tv_address;
    TextView tv_intro;
    TextView tv_menu_name;
    TextView tv_menu_price;

    ImageView ig_profile;
    ImageView ig_menu;

    public static Fragment newInstance(String storeName, String storeProfile, String storeMenu){
        Frag_home_menu_store fragment = new Frag_home_menu_store();
        Bundle args = new Bundle();
        args.putString(STORE_NAME, storeName);
        args.putString(STORE_PROFILE, storeProfile);
        args.putString(STORE_IG_MENU, storeMenu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoreName = getArguments().getString(STORE_NAME);
            mStoreProfile = getArguments().getString(STORE_PROFILE);
            mStoreMenu = getArguments().getString(STORE_IG_MENU);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_store_info, container, false);
        context = container.getContext();

        tv_name = (TextView)view.findViewById(R.id.store_info_name);
        tv_phone = (TextView)view.findViewById(R.id.store_info_phone);
        tv_address = (TextView)view.findViewById(R.id.store_info_address);
        tv_intro = (TextView)view.findViewById(R.id.store_info_intro);
        tv_menu_name = (TextView)view.findViewById(R.id.store_info_menu);
        tv_menu_price = (TextView)view.findViewById(R.id.store_info_price);

        ig_profile = (ImageView)view.findViewById(R.id.store_info_ig_profile);
        ig_menu = (ImageView)view.findViewById(R.id.store_info_ig_menu);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getService();
        Glide.with(view).load(mStoreProfile).into(ig_profile);
        Glide.with(view).load(mStoreMenu).into(ig_menu);
    }

    public void getService() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        retrofitService.getStoreInfo(mStoreName).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<StoreInfo> storeList = result.getStoreInfo();
                    getData(storeList);

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    private void getData(List<StoreInfo> storeList) {

        for (StoreInfo storeinfo : storeList) {
            str_name = storeinfo.getName();
            str_phone = storeinfo.getPhone();
            str_address = storeinfo.getAddress();
            str_intro = storeinfo.getIntro();
            str_menu_name = storeinfo.getMenuName();
            str_menu_price = storeinfo.getMenuPrice();
        }

        tv_name.setText(str_name);
        tv_phone.setText(str_phone);
        tv_address.setText(str_address);
        tv_intro.setText(str_intro);
        tv_menu_name.setText(str_menu_name);
        tv_menu_price.setText(str_menu_price);
    }

}
