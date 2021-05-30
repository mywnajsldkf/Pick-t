package com.example.pickt.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TrailerImageFragment extends Fragment {

    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageView;
    private String imagePath;
    private Button nextButton;
    public String revertedImage;

    public static TrailerImageFragment newInstance() {
        return new TrailerImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_trailer_image, container, false);

        ImageView imageView = (ImageView)v.findViewById(R.id.addImage);
        imagePath = revertedImage;

        nextButton = (Button)v.findViewById(R.id.ImageNextButton);

        // 갤러리 선택 시 사진 추가
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // 갤러리에서 사진 가져오기 창을 띄운다.
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 액티비티 실행 후 이벤트 정의
                getActivity().startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        
        // 다음 fragment로 이동
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TrailerRegisterActivity)getActivity()).replaceFragment(TrailerInfoFragment.newInstance());
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        byte[] image = null;
        byte[] encodedImage = null;

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK & data != null){
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap img = BitmapFactory.decodeStream(inputStream);

                img.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String revertedImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                setRevertedImage(revertedImage);
                inputStream.close();
                imageView.setImageBitmap(img);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_CANCELED){

        }
    }
    private void setRevertedImage(String revertedImage){
        this.revertedImage = revertedImage;
    }

    public String getRevertedImage(){
        return revertedImage;
    }
}