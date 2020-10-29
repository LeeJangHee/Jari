package com.example.jari.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.retrofit2.Result;
import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.retrofit2.ServerConnect;
import com.example.jari.retrofit2.Store;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_home_menu_map extends Fragment
        implements OnMapReadyCallback, Overlay.OnClickListener, SelectStore {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;

    List<Marker> markers_cafe = new ArrayList<>();
    List<Marker> markers_beer = new ArrayList<>();
    List<Marker> markers_food = new ArrayList<>();

    public LatLng curr_LOC;
    public LatLng prev_LOC;

    FusedLocationSource fusedLocationSource;

    private double latitude;
    private double longitude;

    View view;
    Context context;

    RetrofitService retrofitService;
    Store store;

    private OverlayImage marker_image;
    private OverlayImage marker_image_cafe;
    private OverlayImage marker_image_beer;
    private InfoWindow infoWindow;

    MainActivity mainActivity;
    private LocationOverlay locationOverlay;
    private CameraUpdate cameraUpdate;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_home_menu_map, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.frag_map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        fusedLocationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (fusedLocationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!fusedLocationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // Retrofit2 연결 부분
    public void getServiceFood() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        retrofitService.getStoreFood().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreFood();
                    getDataFood(storeList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    public void getServiceCafe() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        // storeKor
        retrofitService.getStoreCafe().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreCafe();
                    getDataCafe(storeList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    public void getServiceBeer() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        // storeKor
        retrofitService.getStoreBeer().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreBeer();
                    getDataBeer(storeList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }


    private void getDataFood(List<Store> storeList) {
        Log.d("TAG", "getData: ");

        marker_image = OverlayImage.fromResource(R.drawable.ic_food_marker);

        for (Store st : storeList) {
            Marker marker = new Marker();
            marker.setTag(st);
            marker.setPosition(new LatLng(
                    Double.parseDouble(st.getLatitude()),
                    Double.parseDouble(st.getLongitude())
            ));

            marker.setIcon(marker_image);

            // 마커가 겹치면 심벌 숨기기
            marker.setHideCollidedSymbols(true);

            marker.setCaptionText(st.getName());
            marker.setCaptionTextSize(14);
            marker.setWidth(Marker.SIZE_AUTO);
            marker.setHeight(Marker.SIZE_AUTO);
            marker.setAnchor(Marker.DEFAULT_ANCHOR);
            marker.setMap(naverMap);
            marker.setOnClickListener(this);
            markers_food.add(marker);

        }
    }

    private void getDataCafe(List<Store> storeList) {
        Log.d("TAG", "getDataCafe: ");

        marker_image_cafe = OverlayImage.fromResource(R.drawable.ic_cafe_marker);

        for (Store st : storeList) {
            Marker marker_cafe = new Marker();
            marker_cafe.setTag(st);
            marker_cafe.setPosition(new LatLng(
                    Double.parseDouble(st.getLatitude()),
                    Double.parseDouble(st.getLongitude())
            ));

            marker_cafe.setIcon(marker_image_cafe);

            // 마커가 겹치면 심벌 숨기기
            marker_cafe.setHideCollidedSymbols(true);

            marker_cafe.setCaptionText(st.getName());
            marker_cafe.setCaptionTextSize(14);
            marker_cafe.setWidth(Marker.SIZE_AUTO);
            marker_cafe.setHeight(Marker.SIZE_AUTO);
            marker_cafe.setAnchor(Marker.DEFAULT_ANCHOR);
            marker_cafe.setMap(naverMap);
            marker_cafe.setOnClickListener(this);
            markers_cafe.add(marker_cafe);
        }


    }

    private void getDataBeer(List<Store> storeList) {
        Log.d("TAG", "getDataBeer: ");
        marker_image_beer = OverlayImage.fromResource(R.drawable.ic_beer_marker);

        for (Store st : storeList) {
            Marker marker_beer = new Marker();
            marker_beer.setTag(st);
            marker_beer.setPosition(new LatLng(
                    Double.parseDouble(st.getLatitude()),
                    Double.parseDouble(st.getLongitude())
            ));

            marker_beer.setIcon(marker_image_beer);

            // 마커가 겹치면 심벌 숨기기
            marker_beer.setHideCollidedSymbols(true);

            marker_beer.setCaptionText(st.getName());
            marker_beer.setCaptionTextSize(14);
            marker_beer.setWidth(Marker.SIZE_AUTO);
            marker_beer.setHeight(Marker.SIZE_AUTO);
            marker_beer.setAnchor(Marker.DEFAULT_ANCHOR);
            marker_beer.setMap(naverMap);
            marker_beer.setOnClickListener(this);
            markers_beer.add(marker_beer);

        }


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        Log.d("TAG", "onMapReady: ");

        // 지도 타입 변경
        naverMap.setMapType(NaverMap.MapType.Basic);

        // 위치 오버레이
        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);

        // 내장 위치 추적
        naverMap.setLocationSource(fusedLocationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        // 카메라
        naverMap.setCameraPosition(new CameraPosition(
                new LatLng(latitude, longitude), 17));

        // 위치가 변경 될 때 실행
        naverMap.addOnLocationChangeListener(location -> {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            curr_LOC = new LatLng(latitude, longitude);

            if (prev_LOC == null) {     // 이전 위치가 없는 경우
                cameraUpdate = CameraUpdate.zoomTo(17);
                naverMap.moveCamera(cameraUpdate);

                locationOverlay.setPosition(curr_LOC);
                prev_LOC = curr_LOC;
            } else {    // 이전 위치가 있는 경우
                cameraUpdate = CameraUpdate.scrollTo(curr_LOC)
                        .animate(CameraAnimation.Easing);
                naverMap.moveCamera(cameraUpdate);

                locationOverlay.setPosition(curr_LOC);
                prev_LOC = curr_LOC;
            }
        });


        // UI Interface 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);

        // 지도 클릭 ---> 정보창 닫기
        naverMap.setOnMapClickListener((point, coord) -> {
            if (infoWindow.getMarker() != null) infoWindow.close();
        });

        // 레트로핏2 서버 연동 ---> 마커 표시
        getServiceFood();
        getServiceCafe();
        getServiceBeer();

        // 정보창 구현
        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindowAdapter(context));
        infoWindow.setOnClickListener(this::onClick);

    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            Marker marker = (Marker) overlay;
            if (marker.getInfoWindow() != null) {
                infoWindow.close();
            } else {
                infoWindow.open(marker);
            }
            return true;
        }

        if (overlay instanceof InfoWindow) {
            InfoWindow infoWindow = (InfoWindow) overlay;
            store = (Store) infoWindow.getMarker().getTag();
            onClickStore(store.getName(), new Frag_home_menu_store());
            Log.i("TAG", "onClick: infoWindow");
            return true;
        }

        return false;
    }

    @Override
    public void onClickStore(String name, Fragment fragment) {
        Log.i("TAG", "onClickStore: ");
        mainActivity = (MainActivity) context;
        Fragment currentFrag = mainActivity.manager.findFragmentById(R.id.main_layout);
        String currentName = mainActivity.toolbarMain_title;
        String str_store_profile = store.getImage_profile();
        String str_store_menu = store.getImage_menu();

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


    // 커스텀 정보창 구현
    private static class InfoWindowAdapter extends InfoWindow.ViewAdapter {

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
}


