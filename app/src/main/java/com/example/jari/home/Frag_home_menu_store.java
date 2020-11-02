package com.example.jari.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.booking.service.Reservation;
import com.example.jari.booking.service.ReservationService;
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
    MainActivity mainActivity;
    AlertDialog dialog;

    private RetrofitService retrofitService;
    private ReservationService reservationService;
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
    TextView tv_wait;

    ImageView ig_profile;
    ImageView ig_menu;

    Button btn_wait;
    private DialogInterface.OnClickListener updateWait;

    public static Fragment newInstance(String storeName, String storeProfile, String storeMenu) {
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
        // 가게 정보 창 레트로핏
        getService();
        // 대기 인원 레트로핏
        getWaitService();
        updateWait = (dialog, which) -> {
            getWaitService();
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_store_info, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        tv_name = (TextView) view.findViewById(R.id.store_info_name);
        tv_phone = (TextView) view.findViewById(R.id.store_info_phone);
        tv_address = (TextView) view.findViewById(R.id.store_info_address);
        tv_intro = (TextView) view.findViewById(R.id.store_info_intro);
        tv_menu_name = (TextView) view.findViewById(R.id.store_info_menu);
        tv_menu_price = (TextView) view.findViewById(R.id.store_info_price);
        tv_wait = (TextView) view.findViewById(R.id.store_info_wait);


        ig_profile = (ImageView) view.findViewById(R.id.store_info_ig_profile);
        ig_menu = (ImageView) view.findViewById(R.id.store_info_ig_menu);

        btn_wait = (Button) view.findViewById(R.id.store_info_waitBtn);
        btn_wait.setOnClickListener(v -> reservationOnClick());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    public void getWaitService() {
        reservationService = ServerConnect.getClient().create(ReservationService.class);
        reservationService.getWaitNum(mStoreName).enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {
                    Reservation reservation_wait = response.body();
                    if (reservation_wait.getSuccess().equals("false")) {
                        tv_wait.setText("0");
                    } else {
                        tv_wait.setText(reservation_wait.getNum());
                    }
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {

            }
        });
    }

    public void reservationOnClick() {
        reservationService = ServerConnect.getClient().create(ReservationService.class);
        reservationService.getRegister(mainActivity.id, str_name, str_phone, str_address, mStoreProfile, mStoreMenu).enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {
                    Reservation reservation = response.body();
                    if (reservation.getSuccess().equals("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        dialog = builder.setMessage(mainActivity.id + "님 예약 되었습니다")
                                .setPositiveButton("확인", updateWait)
                                .create();
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {

            }
        });
    }


}
