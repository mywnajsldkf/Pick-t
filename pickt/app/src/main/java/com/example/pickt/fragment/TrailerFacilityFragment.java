package com.example.pickt.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pickt.R;

import java.util.ArrayList;


public class TrailerFacilityFragment extends Fragment {


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

        final ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, items);

        final ListView listView = (ListView)getActivity().findViewById(R.id.facilityList);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer_facility, container, false);
    }
}