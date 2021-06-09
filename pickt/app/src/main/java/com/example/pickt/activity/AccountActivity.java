package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pickt.R;
import com.example.pickt.UtilService.SharedPreferencesClass;

public class AccountActivity extends AppCompatActivity {

    private TextView registerTrailerButton;
    private TextView logoutButton;

    SharedPreferencesClass sharedPreferencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        registerTrailerButton = (TextView)findViewById(R.id.registerTrailerButton);
        logoutButton = (TextView)findViewById(R.id.logoutButton);
        sharedPreferencesClass = new SharedPreferencesClass(this);

        registerTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, TrailerRegisterActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesClass.clear();
                Intent intent = new Intent(AccountActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}