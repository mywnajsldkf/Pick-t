package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.pickt.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

public class RegisterLicenseActivity extends AppCompatActivity {

    private Boolean isPermission = true;

    // request code
    private static final int GET_GALLERY_IMAGE = 200;

    private ImageView addImageButton;
    private Button ocrButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_license);

        addImageButton = (ImageView) findViewById(R.id.addLicense);
        ocrButton = (Button)findViewById(R.id.ocrButton);

        ocrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 인증 정보 load
        final String ocrApiGwUrl = "https://47baef1c217f4730936dd5ceef8f42bb.apigw.ntruss.com/custom/v1/8758/2def4c437322fdddf9083b77ff65906d6631d48c4045f0432ea01bd80cd56c85/infer";
        final String ocrSecretKey = "SGRCanZBenhPWkt3dVNlaUJic3JmVndzU3hyaEtKRE4=";

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_GALLERY_IMAGE){
            if (resultCode == RESULT_OK){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    Bitmap img = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    addImageButton.setImageBitmap(img);
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
            else if (resultCode==RESULT_CANCELED){
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void getCameraPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    /*
    public class PapagoNmtTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
        }
    }
     */
}