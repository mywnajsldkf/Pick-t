package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SinginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
    }

    public void signupButton(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}