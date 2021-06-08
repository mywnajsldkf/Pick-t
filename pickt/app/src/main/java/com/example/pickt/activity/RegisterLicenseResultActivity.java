package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pickt.R;
import com.example.pickt.UtilService.SharedPreferencesClass;
import com.example.pickt.UtilService.UtilService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterLicenseResultActivity extends AppCompatActivity {
    private TextView licenseTypeTextView, licenseNumberTextView, licensePrimaryKeyTextView;
    private Button registerButton;
    private String userID, userPassword, userName, userPhone, userEmail, userLicenseType, userLicenseNumber, userLicensePrimaryKey;

    UtilService utilService;
    SharedPreferencesClass sharedPreferencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_license_result);

        Intent intent = getIntent();
        // String stringJson = intent.getStringExtra("jsonObject");
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String licenseType = intent.getStringExtra("licenseType");
        String licenseNumber = intent.getStringExtra("licenseNumber");
        String licensePrimaryKey = intent.getStringExtra("licensePrimaryKey");

        licenseTypeTextView = (TextView) findViewById(R.id.licenseType);
        licenseNumberTextView = (TextView) findViewById(R.id.licenseNumber);
        licensePrimaryKeyTextView = (TextView) findViewById(R.id.licensePrimaryKey);

        // TextView에 Intent로 전달받은 type, number, primarykey를 대입한다.
        licenseTypeTextView.setText(licenseType);
        licenseNumberTextView.setText(licenseNumber);
        licensePrimaryKeyTextView.setText(licensePrimaryKey);

        utilService = new UtilService();
        sharedPreferencesClass = new SharedPreferencesClass(this);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilService.hideKeyboard(v, RegisterLicenseResultActivity.this);

                userID = id;
                userPassword = password;
                userName = name;
                userPhone = phone;
                userEmail = email;
                userLicenseType = licenseType;
                userLicenseNumber = licenseNumber;
                userLicensePrimaryKey = licensePrimaryKey;

                registerUser(v);
            }
        });
        // 확인
        /*
        System.out.println("id" + id);
        System.out.println("password" + password);
        System.out.println("name" + name);
        System.out.println("phone" + phone);
        System.out.println("email" + email);
        System.out.println("licenseType" + licenseType);
        System.out.println("licenseNumber" + licenseNumber);
        System.out.println("licensePrimaryKey" + licensePrimaryKey);
         */
    }

    private void registerUser(View v) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("email", userEmail);
        params.put("password", userPassword);
        params.put("phoneNumber", userPhone);
        params.put("licenseNumber", userLicenseNumber);
        params.put("licensePrimaryKey", userLicensePrimaryKey);
        params.put("licenseType", userLicenseType);

        String apikey = "http://118.67.132.247:3001/api/pickt/users";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apikey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        String token = response.getString("token");
                        sharedPreferencesClass.setValue_string("token", token);
                        String msg = "회원가입을 성공하였습니다. ";
                        Toast.makeText(RegisterLicenseResultActivity.this, msg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterLicenseResultActivity.this, SigninActivity.class));
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
                        Toast.makeText(RegisterLicenseResultActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return params;
            }
        };

        // set retry policy
        int socketTime = 3001;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pickt_pref = getSharedPreferences("user_pickt", MODE_PRIVATE);
        if(pickt_pref.contains("token")) {
            startActivity(new Intent(RegisterLicenseResultActivity.this, SigninActivity.class));
            finish();
        }
    }


}