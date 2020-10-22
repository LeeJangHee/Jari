package com.example.jari.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class Frag_home_menu_map extends Fragment implements LocationListener, OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private NaverMap nMap;
    private Marker marker;

    public LatLng curr_LOC;
    public LatLng prev_LOC;

    LocationManager locationManager;
    LocationListener locationListener;

    private double latitude;
    private double longitude;

    View view;

    RetrofitService retrofitService;
    List<Double> marker_latitude = new ArrayList<>();
    List<Double> marker_longitude = new ArrayList<>();
    List<String> storeName = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_home_menu_map, container, false);
        locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(LOCATION_SERVICE);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        if (mapFragment == null) {
            NaverMapOptions options = new NaverMapOptions()
                    .camera(new CameraPosition(new LatLng(35.8655313,128.5912003), 15));
            mapFragment = MapFragment.newInstance(options);
            getChildFragmentManager().beginTransaction().add(R.id.frag_map, mapFragment).commit();
        }

        mapFragment.getMapAsync(naverMap -> nMap = naverMap);

        return view;
    }

    // 위치가 변할 때마다 호출
    @Override
    public void onLocationChanged(Location location) {
        if (nMap == null || location == null) {
            return;
        }
        updateMap(location);
    }

    // 위치서비스가 변경될 때
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        alertStatus(provider);
    }

    // 사용자에 의해 Provider 가 사용 가능하게 설정될 때
    @Override
    public void onProviderEnabled(String provider) {
//        alertProvider(provider);
    }

    // 사용자에 의해 Provider 가 사용 불가능하게 설정될 때
    @Override
    public void onProviderDisabled(String provider) {
//        checkProvider(provider);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasPermission() && locationManager != null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 1000, 10, this);
            }
            return;
        }

        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
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
                    ((MainActivity) getActivity()), PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void updateMap(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        curr_LOC = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate;
        LocationOverlay locationOverlay = nMap.getLocationOverlay();
        // 이전 위치가 없는 경우
        if (prev_LOC == null) {
            cameraUpdate = CameraUpdate.zoomTo(15);
            CameraUpdate.scrollTo(curr_LOC);
            nMap.moveCamera(cameraUpdate);

            locationOverlay.setVisible(true);
            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        } else {
            cameraUpdate = CameraUpdate.scrollTo(curr_LOC);
            nMap.moveCamera(cameraUpdate);

            locationOverlay.setVisible(true);
            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        }

    }

    private boolean hasPermission() {
        return PermissionChecker.checkSelfPermission(((MainActivity) getContext()), PERMISSIONS[0])
                == PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(((MainActivity) getContext()), PERMISSIONS[1])
                == PermissionChecker.PERMISSION_GRANTED;
    }


    // Retrofit2 연결 부분
    public void getService() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        retrofitService.getStoreKor().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreKor();
                    getData(storeList);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    private void getData(List<Store> storeList) {

        for (Store st : storeList) {
            storeName.add(st.getName());
            marker_latitude.add(Double.parseDouble(st.getLatitude()));
            marker_longitude.add(Double.parseDouble(st.getLongitude()));
            Log.d("TAG", "onMapReady: \n"+storeName
            +"\n"+marker_latitude+", "+marker_longitude);

        }

        for (int i = 0; i < storeName.size(); i++) {
            marker = new Marker();
            marker.setPosition(new LatLng(marker_latitude.get(i), marker_longitude.get(i)));
            marker.setMap(nMap);
        }


    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        // Retrofit start
        getService();
        Log.d("TAG", "onMapReady: \n"+storeName
                +"\n"+marker_latitude+", "+marker_longitude);

    }
}
