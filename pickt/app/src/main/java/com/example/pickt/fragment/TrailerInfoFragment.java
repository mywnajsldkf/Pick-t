package com.example.pickt.fragment;

import android.content.Context;
import android.icu.text.SymbolTable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import java.lang.reflect.Array;

public class TrailerInfoFragment extends Fragment {

    private static final String[] license_type = {"1종 대형면허","1종 보통면허","1종 특수면허","2종 보통","2종 소형"};
    private static final String[] parking_lot = {"광주 광천 터미널","북구청","전남대학교","광주역","광주교육대학교","광주공업고등학교","조선대학교"};

    private Button nextButton;

    private String result_Uri;
    private String result_name, result_licenceType, result_parking;
    private TextView TrailerName;
    private String setParkingArea;
    private String setLicenseType;
    private String setUriString;

    public TrailerInfoFragment() {
        // Required empty public constructor
    }

    public static TrailerInfoFragment newInstance(){
        return new TrailerInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        if (getArguments() != null){
            String setUri = getArguments().getString("imageUri");
            setUri(setUri);
            // result_Uri = Uri;
            // System.out.println("Uri를 출력해볼까?!!!!!"+Uri);
        }else {
            // System.out.println("뭐야 비었잖아");
        }

        View view = inflater.inflate(R.layout.fragment_trailer_info, container, false);

        // 스피너
        Spinner licenseSpinner = view.findViewById(R.id.licenseSpinner);
        Spinner parkingSpinner = view.findViewById(R.id.parkingSpinner);

        TrailerName = (TextView)view.findViewById(R.id.TrailerName);
        nextButton = (Button)view.findViewById(R.id.InfoNextButton);

        ArrayAdapter<String> licenseAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, license_type);
        licenseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        licenseSpinner.setAdapter(licenseAdapter);
        licenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result_licenceType = licenseSpinner.getSelectedItem().toString();
                setLicense(result_licenceType);
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
                String parkingResult = parkingSpinner.getSelectedItem().toString();
                setParking(parkingResult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                result_Uri = getUri();
                result_name = TrailerName.getText().toString();
                result_licenceType = getLicense();
                result_parking = getParking();

                bundle.putString("uri", result_Uri);
                bundle.putString("name", result_name);
                bundle.putString("licenseType", result_licenceType);
                bundle.putString("parking", result_parking);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment FacilityFragment = new TrailerFacilityFragment();
                FacilityFragment.setArguments(bundle);
                /*
                System.out.println("내가 선택한 Uri는"+result_Uri);
                System.out.println("내가 선택한 이름은"+result_name);
                System.out.println("내가 선택한 라이센스는"+result_licenceType);
                System.out.println("내가 선택한 주차장은"+result_parking);
                 */
                transaction.replace(R.id.RegisterTrailerActivity, FacilityFragment);
                transaction.commit();
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
}