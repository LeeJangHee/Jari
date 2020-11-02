package com.example.jari.booking;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.booking.service.Reservation;
import com.example.jari.booking.service.ReservationService;
import com.example.jari.home.Frag_home_menu_store;
import com.example.jari.home.SelectStore;
import com.example.jari.retrofit2.ServerConnect;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ItemViewHolder> {
    private ArrayList<BookingItem> mRecyclerViewItemRecycler = new ArrayList<>();
    private Context context;
    private MainActivity mainActivity;
    private View view;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_booking_listitem, parent, false);
        context = parent.getContext();
        mainActivity = (MainActivity) context;
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

    public void addItem(BookingItem bookingItem) {
        mRecyclerViewItemRecycler.add(bookingItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, SelectStore {

        Context context;

        private ImageView ig_icon;
        private TextView tv_title;
        private TextView tv_address;
        private TextView tv_phone;
        private Button btn_booking_cancel;
        private int reservation_id;

        private BookingItem bookingItem;
        private ReservationService reservationService;

        private AlertDialog dialog;

        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            ig_icon = (ImageView) itemView.findViewById(R.id.image_listView);
            tv_title = (TextView) itemView.findViewById(R.id.title_listView);
            tv_address = (TextView) itemView.findViewById(R.id.address_listView);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_listView);
            btn_booking_cancel = (Button) itemView.findViewById(R.id.reservation_cancel_btn);
        }

        public void onBind(BookingItem bookingItem) {
            this.bookingItem = bookingItem;

            Glide.with(view).load(bookingItem.getProfileStr()).into(ig_icon);
            tv_title.setText(bookingItem.getTitleStr());
            tv_address.setText(bookingItem.getAddressStr());
            tv_phone.setText(bookingItem.getPhoneStr());
            reservation_id = bookingItem.getId();

            btn_booking_cancel.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.booking_linear:   // 누르면 상점 인포로 이동
                    onClickStore(bookingItem.getTitleStr(), new Frag_home_menu_store());
                    break;

                case R.id.reservation_cancel_btn:   // 취소 버튼
                    reservationService = ServerConnect.getClient().create(ReservationService.class);
                    Call<Reservation> call_cancel = reservationService.getCancel(reservation_id);
                    call_cancel.enqueue(new Callback<Reservation>() {
                        @Override
                        public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                            if (response.isSuccessful()) {
                                Reservation reservation = response.body();
                                if (reservation.getSuccess().equals("true")) {
                                    // 삭제
                                    if (reservation.getSuccess().equals("true")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        dialog = builder.setMessage(mainActivity.id + "님 예약이 취소 되었습니다.")
                                                .setPositiveButton("확인", null).create();
                                        dialog.show();
                                    }
                                    mainActivity.replaceFragment(new Frag_booking());
                                } else {
                                    // 실패
                                    Log.d("TAG", "예약 취소 실패"+reservation_id);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Reservation> call, Throwable t) {

                        }
                    });
                    break;

            }
        }

        @Override
        public void onClickStore(String name, Fragment fragment) {
            Fragment currentFrag = mainActivity.manager.findFragmentById(R.id.main_layout);
            String currentName = mainActivity.toolbarMain_title;
            String str_store_profile = bookingItem.getProfileStr();
            String str_store_menu = bookingItem.getMenuStr();

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

            // 액션바 뒤로가기
            ActionBar actionBar = mainActivity.getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
        }
    }
}
