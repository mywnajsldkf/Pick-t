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

import android.os.Environment;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class TrailerImageFragment extends Fragment {

    private static String UploadImgPath;
    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageView;
    private Button nextButton;
    public String revertedImage;

    File tempSelectFile;

    Uri imagePath;
    Uri imageUri;
    private String imgPath;
    private String imgName;
    private String stringImage;

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

                imgPath = getStringImage();
                // System.out.println("받아온 imgPath" + imgPath);
                bundle.putString("uri", imgPath);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment infoFragment = new TrailerInfoFragment();  // fragment 생성
                infoFragment.setArguments(bundle);
                // System.out.println("imagePath가 과연나올까...? 나온다면 성공!!!!!ㅎㅎ!! "+ imagePath);
                // System.out.println("imagePath가 과연나올까...? 나온다면 성공ㅎㅎ!! "+ file);
                // ((TrailerRegisterActivity)getActivity()).replaceFragment(TrailerInfoFragment.newInstance());
                transaction.replace(R.id.RegisterTrailerActivity, infoFragment);
                transaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        Log.d(TAG,"onActivityResult()");

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null){
            try {


                /*
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                System.out.println("image_bitmap 출력"+image_bitmap); // android.graphics.Bitmap@35d5cbc
                 */

                // 이미지 저
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(inputStream);
                // System.out.println("이미지 비트맵 형식으로 출력"+img);
                inputStream.close();
                imageView.setImageBitmap(img);
                /*
                saveBitmaptoJpeg(img, "trailers", "Trailer");
                System.out.println("Jpeg형식의 bitmap 출력"+img);
                 */

                setStringImage(BitmapToString(img));
                // System.out.println("String으로 바뀐 것 출력"+BitmapToString(img));

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_CANCELED){
            Toast.makeText(getActivity(), "사진 선택 취소", Toast.LENGTH_LONG).show();
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name){
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder_name="/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+folder_name;
        UploadImgPath = string_path+file_name;

        //boolean translatedBitmap;

        File file_path;
        try{
            file_path = new File(string_path);
            // 파일 저장소가 없으면 디렉토리 생성
            if (!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream outputStream = new FileOutputStream(string_path+file_name);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setStringImage(String stringImage){
        this.stringImage = stringImage;
    }

    public String getStringImage(){
        return stringImage;
    }

    public static String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }
}