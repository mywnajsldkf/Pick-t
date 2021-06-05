package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.pickt.R;
import com.example.pickt.fragment.TrailerImageFragment;

public class TrailerRegisterActivity extends AppCompatActivity {
    public static final String TAG = "TrailerImageFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_register);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        // MainActivity 의 onCreate() 메소드에서 기본적으로 사용할 Fragment 공간과 초기 Fragment의 Instance를 전달한다.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.RegisterTrailerActivity, TrailerImageFragment.newInstance()).commit();
    }

    // Fragment를 전환할 때 사용하는 메소드
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 기존에 RegisterActivity(TrailerRegisterActivity의 id)에서 다른 fragment로 교체한다.
        fragmentTransaction.replace(R.id.RegisterTrailerActivity,fragment).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int request = requestCode;

        /*
        Fragment fragment = getSupportFragmentManager().findFragmentById(TAG);
        fragment.onActivityResult(request, resultCode, data);
         */
    }
}