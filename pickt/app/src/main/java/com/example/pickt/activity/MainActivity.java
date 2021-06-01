package com.example.pickt.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.pickt.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.regex.MatchResult;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG="MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private NaverMap mNaverMap;
    private FusedLocationSource fusedLocationSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 지도 객체 생성
        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        if (mapFragment == null){
            mapFragment = mapFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);

        fusedLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "onMapReady");

        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        naverMap.setLocationSource(fusedLocationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        // 광주광천터미널
        Marker terminalMarker = new Marker();
        terminalMarker.setPosition(new LatLng(35.16163259005393, 126.87979003936667));
        terminalMarker.setMap(mNaverMap);

        // 북구청
        Marker bukguMarker = new Marker();
        bukguMarker.setPosition(new LatLng(35.17460731148258, 126.9120247067833));
        bukguMarker.setMap(mNaverMap);

        // 전남대학교
        Marker chonnamMarker = new Marker();
        chonnamMarker.setPosition(new LatLng(35.17644198294752, 126.90570575580902));
        chonnamMarker.setMap(mNaverMap);

        // 광주역
        Marker stationMarker = new Marker();
        stationMarker.setPosition(new LatLng(35.1641487108431, 126.90972308169597));
        stationMarker.setMap(mNaverMap);

        // 광주교육대학교
        Marker teachingMarker = new Marker();
        teachingMarker.setPosition(new LatLng(35.165226695812294, 126.9263511528607));
        teachingMarker.setMap(mNaverMap);

        // 광주공고
        Marker techMarker = new Marker();
        techMarker.setPosition(new LatLng(35.19028220982834, 126.89737194121555));
        techMarker.setMap(mNaverMap);

        // 조선대학교
        Marker chosunMarker = new Marker();
        chosunMarker.setPosition(new LatLng(35.141663619952006, 126.93194999518909));
        chosunMarker.setMap(mNaverMap);


        // 권한 확인 결과는 onRequestPermissionResult 콜백 메서드 호출
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
    }
}