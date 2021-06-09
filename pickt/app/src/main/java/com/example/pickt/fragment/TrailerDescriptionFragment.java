package com.example.pickt.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.example.pickt.R;
import com.example.pickt.UtilService.SharedPreferencesClass;

import java.io.File;

public class TrailerDescriptionFragment extends Fragment {

    SharedPreferencesClass sharedPreferencesClass;
    String token;

    private Button registerButton;

    private String result_Uri, result_name, result_licenceType, result_parking, result_capacity, result_facility, result_cost, result_description;

    private TextView DescriptionTextView;

    private String setUriString;
    private String setTrailerString;
    private String setLicenseType;
    private String setParkingArea;
    private String setCapacityString;
    private String setFacilityString;
    private String setCostString;

    public TrailerDescriptionFragment() {
        // Required empty public constructor
    }


    public static TrailerDescriptionFragment newInstance() {
        return new TrailerDescriptionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_trailer_description, container, false);

        sharedPreferencesClass = new SharedPreferencesClass(getActivity());
        token = sharedPreferencesClass.getValue_string("token");

        DescriptionTextView = (EditText)view.findViewById(R.id.TrailerDescription);
        registerButton = (Button)view.findViewById(R.id.TrailerRegisterButton);

        if (getArguments()!=null){
            String uri = getArguments().getString("uri");
            String name = getArguments().getString("name");
            String licenseType = getArguments().getString("licenseType");
            String parking = getArguments().getString("parking");
            String capacity = getArguments().getString("capacity");
            String facility = getArguments().getString("facility");
            String cost = getArguments().getString("cost");
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result_Uri = getUri();
                result_name = getTrailerName();
                result_licenceType = getLicense();
                result_parking = getParking();
                result_capacity = getTrailerCapacity();
                result_facility = getTrailerFacility();
                result_cost = getTrailerCost();
                result_description = DescriptionTextView.getText().toString();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void setLicense(String setLicenseType){
        this.setLicenseType = setLicenseType;
    }

    public String getLicense(){
        return setLicenseType;
    }

    public void setParking(String setParkingArea){
        this.setParkingArea= setParkingArea;
    }

    public String getParking(){
        return setParkingArea;
    }

    public void setUri(String setUriString){
        this.setUriString= setUriString;
    }

    public String getUri(){
        return setUriString;
    }

    public void setTrailerName(String setTrailerString){
        this.setTrailerString= setTrailerString;
    }

    public String getTrailerName(){
        return setTrailerString;
    }

    public void setTrailerCapacity(String setCapacityString){
        this.setCapacityString= setCapacityString;
    }

    public String getTrailerCapacity(){
        return setCapacityString;
    }

    public void setTrailerFacility(String setFacilityString){
        this.setFacilityString= setFacilityString;
    }

    public String getTrailerFacility(){
        return setFacilityString;
    }

    public void setTrailerCost(String setCostString){
        this.setCostString= setCostString;
    }

    public String getTrailerCost(){
        return setCostString;
    }
}