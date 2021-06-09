package com.example.pickt.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class TrailerFacilityFragment extends Fragment {

    private Button nextButton;
    /*
    private static final String[] facility_list = {"간이 침대","인덕션","주방","캠핑용품","화장실","해먹"};
    boolean[] checked_facility = new boolean[]{false, false, false, false, false, false, false};
    int i = 0;
     */
    private String result_Uri, result_name, result_licenceType, result_parking;
    private String result_capacity, result_facility, result_cost;

    private TextView CapacityTextView;
    private TextView FacilityTextView;
    private TextView CostTextView;

    private String setLicenseType;
    private String setParkingArea;
    private String setUriString;
    private String setTrailerString;

    public TrailerFacilityFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new TrailerFacilityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            String uri = getArguments().getString("uri");
            String name = getArguments().getString("name");
            String licenseType = getArguments().getString("licenseType");
            String parking = getArguments().getString("parking");

            setUri(uri);
            setTrailerName(name);
            setLicense(licenseType);
            setParking(parking);
            // setUri(setUri);
            // result_Uri = Uri;
            // System.out.println("Uri를 출력해볼까?!!!!!"+setUri);
        }else {
            // System.out.println("뭐야 비었잖아");
        }

        View view = inflater.inflate(R.layout.fragment_trailer_facility, container, false);

        nextButton = (Button)view.findViewById(R.id.FacilityNextButton);

        CapacityTextView = (TextView)view.findViewById(R.id.TrailerCapacity);
        FacilityTextView = (TextView)view.findViewById(R.id.TrailerFacility);
        CostTextView = (TextView)view.findViewById(R.id.TrailerCost);

        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, items);

        final ListView listView = (ListView)getActivity().findViewById(R.id.facilityList);
        // Inflate the layout for this fragment

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                result_Uri = getUri();
                result_name = getTrailerName();
                result_licenceType = getLicense();
                result_parking = getParking();
                result_capacity = CapacityTextView.getText().toString();
                result_facility = FacilityTextView.getText().toString();
                result_cost = CostTextView.getText().toString();

                bundle.putString("uri", result_Uri);
                bundle.putString("name", result_name);
                bundle.putString("licenseType", result_licenceType);
                bundle.putString("parking", result_parking);
                bundle.putString("capacity", result_capacity);
                bundle.putString("facility", result_facility);
                bundle.putString("cost", result_cost);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment DescriptionFragment = new TrailerDescriptionFragment();
                DescriptionFragment.setArguments(bundle);
                transaction.replace(R.id.RegisterTrailerActivity, DescriptionFragment);
                transaction.commit();
            }
        });
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

}