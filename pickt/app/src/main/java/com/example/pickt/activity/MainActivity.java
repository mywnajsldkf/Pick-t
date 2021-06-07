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
import android.widget.Toast;

import com.example.pickt.R;
import com.example.pickt.fragment.BukguFragment;
import com.example.pickt.fragment.ChonnamFragment;
import com.example.pickt.fragment.ChosunFragment;
import com.example.pickt.fragment.StationFragment;
import com.example.pickt.fragment.TeachingFragment;
import com.example.pickt.fragment.TechFragment;
import com.example.pickt.fragment.TerminalFragment;
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
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.ScaleBarView;

import org.jetbrains.annotations.NotNull;

import java.util.regex.MatchResult;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private MapView mapView;
    private static NaverMap naverMap;
    private static final String TAG="MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private FusedLocationSource fusedLocationSource;

    // 마커 변수 선언 및 초기화
    private Marker terminalMarker = new Marker();
    private Marker bukguMarker = new Marker();
    private Marker chonnamMarker = new Marker();
    private Marker stationMarker = new Marker();
    private Marker teachingMarker = new Marker();
    private Marker techMarker = new Marker();
    private Marker chosunMarker = new Marker();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        terminalMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                TerminalFragment terminalFragment = new TerminalFragment();
                terminalFragment.show(getSupportFragmentManager(), "TerminalFragment");
                return false;
            }
        });

        chonnamMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                ChonnamFragment chonnamFragment = new ChonnamFragment();
                chonnamFragment.show(getSupportFragmentManager(), "ChonnamFragment");
                return false;
            }
        });

        bukguMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                BukguFragment bukguFragment = new BukguFragment();
                bukguFragment.show(getSupportFragmentManager(), "BukguFragment");
                return false;
            }
        });

        stationMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                StationFragment stationFragment = new StationFragment();
                stationFragment.show(getSupportFragmentManager(), "StationFragment");
                return false;
            }
        });

        teachingMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                TeachingFragment teachingFragment = new TeachingFragment();
                teachingFragment.show(getSupportFragmentManager(), "TeachingFragment");
                return false;
            }
        });

        techMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                TechFragment techFragment = new TechFragment();
                techFragment.show(getSupportFragmentManager(), "TechFragment");
                return false;
            }
        });

        chosunMarker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull @NotNull Overlay overlay) {
                ChosunFragment chosunFragment = new ChosunFragment();
                chosunFragment.show(getSupportFragmentManager(), "ChosunFragment");
                return false;
            }
        });

        fusedLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        Log.d(TAG,"onMapReady");
        this.naverMap = naverMap;

        setMarker(terminalMarker, 35.16163259005393, 126.87979003936667);
        setMarker(bukguMarker, 35.17460731148258, 126.9120247067833);
        setMarker(chonnamMarker, 35.17644198294752, 126.90570575580902);
        setMarker(stationMarker, 35.1641487108431, 126.90972308169597);
        setMarker(teachingMarker, 35.165226695812294, 126.9263511528607);
        setMarker(techMarker,35.19028220982834,126.89737194121555);
        setMarker(chosunMarker, 35.141663619952006, 126.93194999518909);

        naverMap.setLocationSource(fusedLocationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    private void setMarker(Marker marker, double lat, double lng)
    {
        marker.setIconPerspectiveEnabled(true);
        marker.setPosition(new LatLng(lat, lng));
        marker.setMap(naverMap);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}