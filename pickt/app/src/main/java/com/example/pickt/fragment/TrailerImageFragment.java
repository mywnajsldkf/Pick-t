package com.example.pickt.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class TrailerImageFragment extends Fragment {

    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageView;
    private Button nextButton;
    public String revertedImage;

    Uri imagePath;
    Uri imageUri;
    private String imgPath;
    private String imgName;

    public static TrailerImageFragment newInstance() {
        return new TrailerImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_trailer_image, container, false);

        imageView = (ImageView)v.findViewById(R.id.addImage);

        nextButton = (Button)v.findViewById(R.id.ImageNextButton);

        // 갤러리 선택 시 사진 추가
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // 갤러리에서 사진 가져오기 창을 띄운다.

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 액티비티 실행 후 이벤트 정의

                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        // 다음 fragment로 이동
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                imagePath = getImageUri();

                File file = null;
                try {
                    file = createFileFromUri(imagePath);
                }catch (IOException e){

                }

                bundle.putString("imageUri", String.valueOf(file));

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment infoFragment = new TrailerInfoFragment();  // fragment 생성
                infoFragment.setArguments(bundle);
                System.out.println("imagePath가 과연나올까...? 나온다면 성공!!!!!ㅎㅎ!! "+ imagePath);
                System.out.println("imagePath가 과연나올까...? 나온다면 성공ㅎㅎ!! "+ file);
                // ((TrailerRegisterActivity)getActivity()).replaceFragment(TrailerInfoFragment.newInstance());
                transaction.replace(R.id.RegisterTrailerActivity, infoFragment);
                transaction.commit();
            }
        });

        return v;
    }

    // 보내기에 적당하게 바꿀거다
    File createFileFromUri(Uri uri) throws IOException{
        InputStream is = getActivity().getContentResolver().openInputStream(uri);
        File file = new File(String.valueOf(getActivity().getCacheDir()));
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[2046];
        int read = -1;
        while ((read = is.read(buf)) != -1) {
            fos.write(buf, 0, read);
        }
        fos.flush();
        fos.close();
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        Log.d(TAG,"onActivityResult()");

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null){
            try {
                /*
                // Uri에서 이미지 이름을 얻어온다.
                String name_Str = getImageNameToUri(data.getData());
                System.out.println("이미지 이름 얻어옴 "+name_Str);
                Uri uri = data.getData();
                imageUri = uri;
                // setImageUri(imageUri);

                 */

                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                System.out.println("image_bitmap 출력"+image_bitmap); // android.graphics.Bitmap@35d5cbc

                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                // 이미지 표시
                imageView.setImageBitmap(img);

                // Uri에서 이미지 이름을 얻어온다.

                /*
                Uri uri = data.getData();
                imageUri = uri;
                 */

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_CANCELED){
            Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
        }
    }

    // Uri로부터 파일명 추
    private String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        imgPath = cursor.getString(column_index);
        imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri;
    }

    public Uri getImageUri(){
        return imageUri;
    }

    /*
    String getFileNameFromUri(Uri uri){
        Cursor returnCursor = getContentResolver().openInputStream(uri);
    }
     */
}