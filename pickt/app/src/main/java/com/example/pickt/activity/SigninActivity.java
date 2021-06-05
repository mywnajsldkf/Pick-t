package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pickt.R;
import com.example.pickt.UtilService.SharedPreferencesClass;
import com.example.pickt.UtilService.UtilService;

public class SigninActivity extends AppCompatActivity {

    UtilService utilService;
    SharedPreferencesClass sharedPreferencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        utilService = new UtilService();

        sharedPreferencesClass = new SharedPreferencesClass(this);
    }

    public void signupButton(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}