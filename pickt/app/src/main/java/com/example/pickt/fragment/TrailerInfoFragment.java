package com.example.pickt.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import java.lang.reflect.Array;

public class TrailerInfoFragment extends Fragment {

    private Spinner LicenseSpinner;
    private static final String[] license_type = {"1종 대형면허","1종 보통면허","1종 특수면허","2종 보통","2종 소형"};
    private static final String[] parking_lot = {"광주 광천 터미널","북구청","전남대학교","광주역","광주교육대학교","광주공업고등학교","조선대학교"};

    private Button nextButton;

    public TrailerInfoFragment() {
        // Required empty public constructor
    }

    public static TrailerInfoFragment newInstance(){
        return new TrailerInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trailer_info, container, false);

        // 스피너
        Spinner licenseSpinner = view.findViewById(R.id.licenseSpinner);
        Spinner parkingSpinner = view.findViewById(R.id.parkingSpinner);

        nextButton = (Button)view.findViewById(R.id.InfoNextButton);

        ArrayAdapter<String> licenseAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, license_type);
        // 정의된 레이아웃 사용
        licenseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        licenseSpinner.setAdapter(licenseAdapter);
        licenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> parkingAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, parking_lot);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        parkingSpinner.setAdapter(parkingAdapter);
        parkingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TrailerRegisterActivity)getActivity()).replaceFragment(TrailerFacilityFragment.newInstance());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}