package com.example.pickt.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.pickt.R;
import com.example.pickt.activity.TrailerRegisterActivity;

import java.util.ArrayList;


public class TrailerFacilityFragment extends Fragment {

    private Button nextButton;
    private static final String[] facility_list = {"간이 침대","인덕션","주방","캠핑용품","화장실","해먹"};
    boolean[] checked_facility = new boolean[]{false, false, false, false, false, false, false};
    int i = 0;

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

        View view = inflater.inflate(R.layout.fragment_trailer_facility, container, false);

        nextButton = (Button)view.findViewById(R.id.FacilityNextButton);

        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, items);

        final ListView listView = (ListView)getActivity().findViewById(R.id.facilityList);
        // Inflate the layout for this fragment

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TrailerRegisterActivity)getActivity()).replaceFragment(TrailerDescriptionFragment.newInstance());
            }
        });
        return view;
    }
}