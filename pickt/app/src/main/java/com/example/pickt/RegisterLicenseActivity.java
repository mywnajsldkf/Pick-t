package com.example.pickt;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegisterLicenseActivity extends AppCompatActivity {

    private Boolean isPermission = true;

    // request code
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_license);

        getCameraPermission();

        findViewById(R.id.licenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.picturepopup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.camera){
                            /*
                            if (isPermission) {
                                // takePhoto();
                            }
                            else Toast.makeText(RegisterLicenseActivity.this, getResources().getString(R.string.permission_1), Toast.LENGTH_SHORT).show();
                             */
                        }
                        else if(item.getItemId() == R.id.gallery){
                            /*
                            if (isPermission){
                                //  getGalleryImage();
                            }
                            else Toast.makeText(RegisterLicenseActivity.this, getResources().getString(R.string.permission_2), Toast.LENGTH_SHORT).show();
                             */
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_FROM_ALBUM){
            Uri imageUri = data.getData();
            Cursor cursor = null;
            try {
                /*
                Uri 스키마를
                content:/// 에서 file:/// 로 변경한다.
                */
                String[] projectedImage = {MediaStore.Images.Media.DATA};

                assert imageUri != null;
                cursor = getContentResolver().query(imageUri, projectedImage, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
            }finally {
                if (cursor != null){
                    cursor.close();
                }
            }

        }
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            tempFile = createImageFile();
        }catch (IOException e){
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile!= null){
            Uri imageUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PICK_FROM_ALBUM);
        }
    }

    private File createImageFile() throws IOException{
        // 이미지 파일
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "pickt"+timeStamp+"_";

        // 이미지가 저장될 폴더 이름
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/pickt/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    private void getCameraPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void getGalleryImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
}