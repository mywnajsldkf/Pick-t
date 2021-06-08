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
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.pickt.UtilService.OcrProc;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegisterLicenseActivity extends AppCompatActivity {

    private Boolean isPermission = true;

    // request code
    private static final int GET_GALLERY_IMAGE = 200;

    private ImageView addImageButton;
    private Button ocrButton;
    private long mLastClickTime = 0;

    public String encodedImage;

    public String result_id, result_password, result_name, result_phone, result_email;
    public String licenseType, licenseNumber, licensePrimaryKey;

    // 인증 정보 load (네이버 CLOVA OCR API 사용 전용 게이트웨이 및 인증키)
    final String ocrApiGwUrl = "https://47baef1c217f4730936dd5ceef8f42bb.apigw.ntruss.com/custom/v1/8758/2def4c437322fdddf9083b77ff65906d6631d48c4045f0432ea01bd80cd56c85/infer";
    final String ocrSecretKey = "SGRCanZBenhPWkt3dVNlaUJic3JmVndzU3hyaEtKRE4=";

    protected static String ocrText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_license);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");

        result_id = id;
        result_password = password;
        result_name = name;
        result_phone = phone;
        result_email = email;

        /*
        System.out.println("test id" + id);
        System.out.println("test password" + password);
        System.out.println("test name" + name);
        System.out.println("test phone" + phone);
        System.out.println("test email" + email);
         */

        addImageButton = (ImageView) findViewById(R.id.addLicense);
        ocrButton = (Button)findViewById(R.id.ocrButton);

        //이미지 추가
        ocrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime()-mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
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

                    img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                    // System결과iVBORw0KGgoAAAANSUhEUgAAAjAAAAFuCAYAAACfnSJ1AAAABHNCSVQICAgIfAhkiAAAIABJREFU
                    // System.out.println("System결과"+encodedImage);

                    // OCR 실행
                    ocrText = "";       // ocr이 실행되면 값이 오는 textview
                    OcrTask ocrTask = new OcrTask();
                    ocrTask.execute(ocrApiGwUrl, ocrSecretKey, encodedImage);

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

    // OCR 실행 클래스
    public class OcrTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            return OcrProc.main(strings[0], strings[1], strings[2]);
        }
        @Override
        protected void onPostExecute(String result){
            ReturnThreadResult(result);
        }
    }

    // OCR 결과 출력 메소드(TextView에 표시됨)
    public void ReturnThreadResult(String result) {
        System.out.println("### Return Thread Result");
        String rlt = result;
        try {
            JSONObject jsonObject = new JSONObject(rlt);
            JSONArray jsonArray = jsonObject.getJSONArray("images");
            JSONArray jsonArray_fields = jsonArray.getJSONObject(0).getJSONArray("fields");

            licenseType = jsonArray_fields.getJSONObject(0).getString("inferText");
            licenseNumber = jsonArray_fields.getJSONObject(1).getString("inferText");
            licensePrimaryKey = jsonArray_fields.getJSONObject(3).getString("inferText");

            // licenseResult로 값 전달
            Intent intent = new Intent(getApplicationContext(), RegisterLicenseResultActivity.class);
            intent.putExtra("id", result_id);
            intent.putExtra("password", result_password);
            intent.putExtra("name", result_name);
            intent.putExtra("phone", result_phone);
            intent.putExtra("email", result_email);
            intent.putExtra("licenseType", licenseType);
            intent.putExtra("licenseNumber", licenseNumber);
            intent.putExtra("licensePrimaryKey", licensePrimaryKey);

            startActivity(intent);
            finish();   // 이전 Activity 종료
        }catch (Exception e){
        }
    }
}