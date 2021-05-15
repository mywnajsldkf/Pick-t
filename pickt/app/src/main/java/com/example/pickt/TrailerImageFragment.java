package com.example.pickt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class TrailerImageFragment extends Fragment {


    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageButton;

    public String encodedImage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer_image, container, false);
    }
}