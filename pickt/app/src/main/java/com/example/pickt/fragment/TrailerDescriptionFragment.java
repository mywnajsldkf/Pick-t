package com.example.pickt.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pickt.R;
import com.example.pickt.UtilService.SharedPreferencesClass;
import com.example.pickt.UtilService.UtilService;
import com.example.pickt.activity.TrailerRegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TrailerDescriptionFragment extends Fragment {

    UtilService utilService;
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

        utilService = new UtilService();
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

            setUri(uri);
            setTrailerName(name);
            setLicense(licenseType);
            setParking(parking);
            setTrailerCapacity(capacity);
            setTrailerFacility(facility);
            setTrailerCost(cost);

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

                System.out.println("URI 출력"+result_Uri);
                System.out.println("name 출력"+result_name);
                System.out.println("licenseType 출력"+result_licenceType);
                System.out.println("parking 출력"+result_parking);
                System.out.println("capacity 출력"+result_capacity);
                System.out.println("facility 출력"+result_facility);
                System.out.println("cost 출력"+result_cost);
                System.out.println("설명 출력"+result_description);

                registerTrailer(v);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void registerTrailer(View v) {
        final HashMap<String, String> params = new HashMap<String, String>();
        // params.put("userId", result_)
        // params.put("trailerPhoto", result_Uri);
        params.put("trailerName", result_name);
        params.put("license", result_licenceType);
        params.put("rentalPlace", result_parking);
        params.put("cost", result_cost);
        params.put("capacity", result_capacity);
        params.put("facilities", result_facility);
        params.put("description", result_description);

        String apikey = "http://118.67.132.247:3001/api/pickt/trailers";
        final String token = sharedPreferencesClass.getValue_string("token");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apikey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // success -> true
                    if (response.getBoolean("Success")) {
                        System.out.println("등록에 성공했습니다. ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
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