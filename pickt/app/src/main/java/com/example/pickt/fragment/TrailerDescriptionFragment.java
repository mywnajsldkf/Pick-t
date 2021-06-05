package com.example.pickt.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pickt.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrailerDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailerDescriptionFragment extends Fragment {

    public TrailerDescriptionFragment() {
        // Required empty public constructor
    }


    public static TrailerDescriptionFragment newInstance() {

        return new TrailerDescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer_description, container, false);
    }
}