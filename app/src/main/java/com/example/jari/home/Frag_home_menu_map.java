package com.example.jari.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;

public class Frag_home_menu_map extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private NaverMap nMap;

    public LatLng curr_LOC;
    public LatLng prev_LOC;

    LocationManager locationManager;
    LocationListener locationListener;

    private double latitude;
    private double longitude;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_home_menu_map, container, false);


        return view;
    }

    // 네이버 지도 뷰
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        nMap = naverMap;

        locationListener = new LocationListener() {
            // 위치가 변할 때마다 호출
            public void onLocationChanged(Location location) {
                updateMap(location);
            }

            // 위치서비스가 변경될 때
            public void onStatusChanged(String provider, int status, Bundle extras) {
                alertStatus(provider);
            }

            // 사용자에 의해 Provider 가 사용 가능하       게 설정될 때
            public void onProviderEnabled(String provider) {
                alertProvider(provider);
            }

            // 사용자에 의해 Provider 가 사용 불가능하게 설정될 때
            public void onProviderDisabled(String provider) {
                checkProvider(provider);
            }
        };

        locationManager = (LocationManager) ((MainActivity) getActivity()).getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(((MainActivity) getActivity())
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , 100);
            return;
        }

        String locationProvider;

        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 1, 1, locationListener);

        locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 1, 1, locationListener);
    }

    public void checkProvider(String provider) {
        Toast.makeText(view.getContext(), provider + "에 의한 위치서비스가 꺼져 있습니다. 켜주세요."
                , Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public void alertProvider(String provider) {
        Toast.makeText(view.getContext(), provider + "서비스가 켜졌습니다!", Toast.LENGTH_LONG).show();
    }

    public void alertStatus(String provider) {
        Toast.makeText(view.getContext(), "위치서비스가 " + provider + "로 변경되었습니다!", Toast.LENGTH_LONG).show();
    }

    public void updateMap(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        curr_LOC = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate;
        // 이전 위치가 없는 경우
        if (prev_LOC == null) {
            cameraUpdate = CameraUpdate.zoomTo(15);
            CameraUpdate.scrollTo(curr_LOC);
            nMap.moveCamera(cameraUpdate);

            LocationOverlay locationOverlay = nMap.getLocationOverlay();
            locationOverlay.setVisible(true);
            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        } else {
            cameraUpdate = CameraUpdate.scrollTo(curr_LOC);
            nMap.moveCamera(cameraUpdate);

            LocationOverlay locationOverlay = nMap.getLocationOverlay();
            locationOverlay.setVisible(true);
            locationOverlay.setPosition(curr_LOC);

            prev_LOC = curr_LOC;
        }

    }
}
