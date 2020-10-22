package com.example.jari.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class Frag_home_menu_map extends Fragment
        implements LocationListener, OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap nMap;

    public LatLng curr_LOC;
    public LatLng prev_LOC;

    FusedLocationSource fusedLocationSource;

    LocationManager locationManager;
    LocationListener locationListener;

    private double latitude;
    private double longitude;

    View view;

    RetrofitService retrofitService;
    List<String> marker_latitude = new ArrayList<>();
    List<String> marker_longitude = new ArrayList<>();
    List<String> storeName = new ArrayList<>();

    Executor executor = Executors.newFixedThreadPool(100);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_home_menu_map, container, false);
        locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(LOCATION_SERVICE);

        fusedLocationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        if (mapFragment == null) {
            NaverMapOptions options = new NaverMapOptions()
                    .symbolScale(0)
                    .mapType(NaverMap.MapType.Basic);
            mapFragment = MapFragment.newInstance(options);
            getChildFragmentManager().beginTransaction().add(R.id.frag_map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

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
    public void getServiceKor() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        retrofitService.getStoreKor().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreKor();
                    getData(storeList, 0);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    public void getServiceJp() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        // storeKor
        retrofitService.getStoreJp().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreJp();
                    getData(storeList, 0);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    public void getServiceCh() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        // storeKor
        retrofitService.getStoreCh().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreCh();
                    getData(storeList, 0);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    public void getServiceWf() {
        retrofitService = ServerConnect.getClient().create(RetrofitService.class);
        // storeKor
        retrofitService.getStoreWf().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    List<Store> storeList = result.getStoreWf();
                    getData(storeList, 0);
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
                    getData(storeList, 1);
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
                    getData(storeList, 2);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // 실패했을 때
            }
        });
    }

    private void getData(List<Store> storeList, int idx_image) {
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            Log.d("TAG", "getData: ");

            List<Marker> markers = new ArrayList<>();
            List<OverlayImage> marker_image = Arrays.asList(
                    OverlayImage.fromResource(R.drawable.ic_food_marker),
                    OverlayImage.fromResource(R.drawable.ic_cafe_marker),
                    OverlayImage.fromResource(R.drawable.ic_beer_marker)
            );

            for (Store st : storeList) {
                storeName.add(st.getName());
                marker_latitude.add(st.getLatitude());
                marker_longitude.add(st.getLongitude());
            }

            for (int i = 0; i < storeName.size(); i++) {
                Marker marker = new Marker();
                marker.setPosition(new LatLng(Double.parseDouble(marker_latitude.get(i))
                        , Double.parseDouble(marker_longitude.get(i))));

                if (idx_image == 0) marker.setIcon(marker_image.get(0));
                else if (idx_image == 1) marker.setIcon(marker_image.get(1));
                else marker.setIcon(marker_image.get(2));

                marker.setCaptionText(storeName.get(i));
                marker.setCaptionTextSize(16);
                marker.setWidth(80);
                marker.setHeight(80);
                markers.add(marker);
            }

            handler.post(() -> {
                for (Marker mk : markers) {
                    mk.setMap(nMap);
                }
            });

        });
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        nMap = naverMap;

        // 내장 위치 추적
        nMap.setLocationSource(fusedLocationSource);

        // Retrofit start
        getServiceKor();
        getServiceJp();
        getServiceCh();
        getServiceWf();
        getServiceCafe();
        getServiceBeer();
        Log.d("TAG", "onMapReady: \n" + storeName
                + "\n" + marker_latitude + ", " + marker_longitude);

    }
}
