package com.example.jari.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.retrofit2.Result;
import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.retrofit2.ServerConnect;
import com.example.jari.retrofit2.Store;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
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

import static android.content.Context.LOCATION_SERVICE;

public class Frag_home_menu_map extends Fragment
        implements LocationListener, OnMapReadyCallback, Overlay.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;

    List<Marker> markers_cafe = new ArrayList<>();
    List<Marker> markers_beer = new ArrayList<>();
    List<Marker> markers_food = new ArrayList<>();

    public LatLng curr_LOC;
    public LatLng prev_LOC;

    FusedLocationSource fusedLocationSource;

    LocationManager locationManager;

    private double latitude;
    private double longitude;

    View view;
    Context context;

    RetrofitService retrofitService;

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

        view = (View) inflater.inflate(R.layout.frag_home_menu_map, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;
        locationManager = (LocationManager) mainActivity.getSystemService(LOCATION_SERVICE);

        fusedLocationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.frag_map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();

        if (hasPermission()) {
            if (locationManager != null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 1000, 10, this);
            }
        } else {
            ActivityCompat.requestPermissions(
                    mainActivity, PERMISSIONS, PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }


    // 위치가 변할 때마다 호출
    @Override
    public void onLocationChanged(Location location) {
        if (naverMap == null || location == null) {
            return;
        }
        updateMap(location);
    }

    // 위치서비스가 변경될 때
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    // 사용자에 의해 Provider 가 사용 가능하게 설정될 때
    @Override
    public void onProviderEnabled(String provider) {
    }

    // 사용자에 의해 Provider 가 사용 불가능하게 설정될 때
    @Override
    public void onProviderDisabled(String provider) {
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasPermission() && locationManager != null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 1000, 10, this);
            }
            return;
        }
        if (fusedLocationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!fusedLocationSource.isActivated()){
                // 권한 거부
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }

    }

    public void updateMap(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        curr_LOC = new LatLng(latitude, longitude);

        // 이전 위치가 없는 경우
        if (prev_LOC == null) {
            cameraUpdate = CameraUpdate.zoomTo(15);
            CameraUpdate.scrollTo(curr_LOC)
                        .animate(CameraAnimation.Easing);
            naverMap.moveCamera(cameraUpdate);

            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        } else {
            cameraUpdate = CameraUpdate.scrollTo(curr_LOC)
                                       .animate(CameraAnimation.Easing);
            naverMap.moveCamera(cameraUpdate);

            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        }

    }

    private boolean hasPermission() {
        return PermissionChecker.checkSelfPermission(mainActivity, PERMISSIONS[0])
                == PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(mainActivity, PERMISSIONS[1])
                == PermissionChecker.PERMISSION_GRANTED;
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
            marker.setWidth(64);
            marker.setHeight(64);
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
            marker_cafe.setWidth(64);
            marker_cafe.setHeight(64);
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
            marker_beer.setWidth(64);
            marker_beer.setHeight(64);
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


        // 내장 위치 추적
        naverMap.setLocationSource(fusedLocationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        // 위치 오버레이
        locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);

        // 카메라
        cameraUpdate = CameraUpdate.scrollTo(new LatLng(
                locationOverlay.getPosition().latitude,
                locationOverlay.getPosition().longitude
        ));
        naverMap.moveCamera(cameraUpdate);

        // 정보창 구현
        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindowAdapter(context));
        infoWindow.setOnClickListener(overlay -> {
            Marker marker = infoWindow.getMarker();
            Store store = (Store) marker.getTag();
            Log.d("TAG", "onMapReady, infoClick: "+store.getName());
            // fragment 이동 구현

            return true;
        });

        // 지도 클릭 ---> 정보창 닫기
        naverMap.setOnMapClickListener((point, coord) -> {
            if (infoWindow.getMarker() != null)  infoWindow.close();
        });

        // 레트로핏2 서버 연동 ---> 마커 표시
        getServiceFood();
        getServiceCafe();
        getServiceBeer();


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
        else if (overlay instanceof InfoWindow) {

        }
        return false;
    }

    // 커스텀 정보창 구현
    private static class InfoWindowAdapter extends InfoWindow.ViewAdapter
            implements SelectStore {

        @NonNull
        private Context context;
        private View rootView;
        private ImageView ig_profile;
        private TextView tv_name;
        private TextView tv_phone;
        private TextView tv_address;

        private InfoWindowAdapter(@NonNull Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(@NonNull InfoWindow infoWindow) {
            Marker marker = infoWindow.getMarker();
            Store store = (Store) marker.getTag();
            if (rootView == null) {
                rootView = View.inflate(context, R.layout.frag_home_menu_map_infowindow, null);
                ig_profile = rootView.findViewById(R.id.infowindow_profile);
                tv_name = rootView.findViewById(R.id.infowindow_name);
                tv_phone = rootView.findViewById(R.id.infowindow_phone);
                tv_address = rootView.findViewById(R.id.infowindow_address);
            }

            if (infoWindow.getMarker() != null) {
                tv_phone.setText(store.getPhone());
                tv_name.setText(store.getName());
                tv_address.setText(store.getAddress());
            }

            return rootView;
        }

        @Override
        public void onClickStore(String name, Fragment fragment) {

        }
    }


}
