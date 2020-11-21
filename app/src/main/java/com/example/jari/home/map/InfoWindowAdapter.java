package com.example.jari.home.map;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.jari.R;
import com.example.jari.retrofit2.Store;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

// 커스텀 정보창 구현
public class InfoWindowAdapter extends InfoWindow.ViewAdapter {

    @NonNull
    private final Context context;
    private View rootView;
    private ImageView ig_profile;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_address;

    private Marker marker;

    public InfoWindowAdapter(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(@NonNull InfoWindow infoWindow) {
        marker = infoWindow.getMarker();
        Store store = (Store) marker.getTag();

        rootView = (View) View.inflate(context, R.layout.frag_home_menu_map_infowindow, null);
        ig_profile = (ImageView) rootView.findViewById(R.id.infowindow_profile);
        tv_name = (TextView) rootView.findViewById(R.id.infowindow_name);
        tv_phone = (TextView) rootView.findViewById(R.id.infowindow_phone);
        tv_address = (TextView) rootView.findViewById(R.id.infowindow_address);

        Glide.with(rootView)
                .load(store.getImage_profile())
                .placeholder(R.drawable.jari_loding)
                .error(R.drawable.jari_user_profile)
                .into(ig_profile);

        tv_phone.setText(store.getPhone());
        tv_name.setText(store.getName());
        tv_address.setText(store.getAddress());

        return rootView;
    }
}
