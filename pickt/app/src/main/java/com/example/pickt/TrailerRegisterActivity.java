package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pickt.utils.BusProvider;

public class TrailerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_register);

        TrailerImageFragment trailerImageFragment = new TrailerImageFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.TrailerImageFragment, trailerImageFragment);
        fragmentTransaction.commit();
    }

    // 부모 Activity 에서 Event 생성
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        BusProvider.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));
    }

    /*
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode){
        if (requestCode == -1){
            super.startActivityForResult(intent, -1);
            return;
        }
    if ((requestCode&0xffff0000) != 0){
        throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }
    super.startActivityForResult(intent, ((fragment.mIndex+1)<<16)+(requestCode&0xffff));
    }
     */
}