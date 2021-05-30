package com.example.pickt.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pickt.R;

public class TrailerInfoFragment extends Fragment {

    private Spinner LicenseSpinner;
    private static final String[] license_type = {"1종 대형면허","1종 보통면허","1종 특수면허","2종 보통","2종 소형"};

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
        LicenseSpinner = (Spinner) view.findViewById(R.id.licenseSpinner);
        ArrayAdapter<String> license_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, license_type);
        license_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LicenseSpinner.setAdapter(license_adapter);
        LicenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer_info, container, false);
    }
}