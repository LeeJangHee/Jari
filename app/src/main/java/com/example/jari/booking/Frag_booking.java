package com.example.jari.booking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.booking.service.Reservation;
import com.example.jari.booking.service.ReservationList;
import com.example.jari.booking.service.ReservationService;
import com.example.jari.retrofit2.ServerConnect;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_booking extends Fragment {
    private View view;
    private Context context;
    private BookingAdapter adapter;

    private MainActivity mainActivity;
    private ReservationService reservationService;

    private List<Reservation> checkList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 서버 연결
        getReservationCheck();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull final ViewGroup container,
                             @NonNull Bundle saveInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_booking, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        init();

        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.booking_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new BookingAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(List<Reservation> reservation){
        List<String> listName = new ArrayList<>();
        List<String> listPhone = new ArrayList<>();
        List<String> listAddress = new ArrayList<>();
        List<String> listProfile = new ArrayList<>();
        List<String> listMenu = new ArrayList<>();
        List<Integer> listID = new ArrayList<>();

        for (Reservation res : reservation) {
            listName.add(res.getStoreName());
            listPhone.add(res.getStorePhone());
            listAddress.add(res.getStoreAddress());
            listProfile.add(res.getStoreProfile());
            listMenu.add(res.getStoreMenu());
            listID.add(res.getId());
        }

        for(int i = 0 ; i < listName.size(); i++){
            BookingItem bookingItem = new BookingItem();

            bookingItem.setTitleStr(listName.get(i));
            bookingItem.setAddressStr(listAddress.get(i));
            bookingItem.setPhoneStr(listPhone.get(i));
            bookingItem.setProfileStr(listProfile.get(i));
            bookingItem.setMenuStr(listMenu.get(i));
            bookingItem.setId(listID.get(i));

            adapter.addItem(bookingItem);
        }
        adapter.notifyDataSetChanged();
    }

    public void getReservationCheck() {
        reservationService = ServerConnect.getClient().create(ReservationService.class);
        Call<ReservationList> call_check = reservationService.getCheck(mainActivity.id);
        call_check.enqueue(new Callback<ReservationList>() {
            @Override
            public void onResponse(Call<ReservationList> call, Response<ReservationList> response) {
                if (response.isSuccessful()) {
                    ReservationList mReservation = response.body();
                    List<Reservation> reservationLists = mReservation.getReservationCheck();
                    getData(reservationLists);
                }
            }

            @Override
            public void onFailure(Call<ReservationList> call, Throwable t) {

            }
        });
    }

}

