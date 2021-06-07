package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private EditText userID;
    private EditText userPassword;
    private Button signinButton;
    private TextView signupButton;
    ProgressBar progressBar;

    private String id, password;
    UtilService utilService;
    SharedPreferencesClass sharedPreferencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userID = (EditText)findViewById(R.id.userID);
        userPassword = (EditText)findViewById(R.id.userPassword);
        signinButton = (Button)findViewById(R.id.singinButton);
        signupButton = (TextView)findViewById(R.id.signupButton);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        utilService = new UtilService();
        sharedPreferencesClass = new SharedPreferencesClass(this);

        // 로그인 버튼 클릭
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, SigninActivity.this);

                id = userID.getText().toString();
                password = userPassword.getText().toString();
                if (validate(view)){
                    userLogin(view);
                }
            }
        });

        // 회원가입 버튼 클릭
        /*
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
         */
    }

    private void userLogin(View view){
        // progressBar.setVisibility(View.VISIBLE);

        final HashMap<String, String>params = new HashMap<String, String>();
        params.put("email", id);
        params.put("password", password);

        String apikey = "http://118.67.132.247:3001/api/pickt/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        /*
        String msg = "로그인 버튼을 클릭했습니다.";
        Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_LONG).show();
         */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apikey, new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        String token = response.getString("token");
                        sharedPreferencesClass.setValue_string("token", token);
                        String msg = "로그인에 성공하셨습니다.";
                        Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    }
                    // progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null){
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(SigninActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // progressBar.setVisibility(View.GONE);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        // progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String>headers = new HashMap<String, String>();
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
        requestQueue.add(jsonObjectRequest);
    }

    public void signupButton(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences pickt_pref = getSharedPreferences("user_pickt", MODE_PRIVATE);
        if (pickt_pref.contains("token")){
            startActivity(new Intent(SigninActivity.this, MainActivity.class));
            finish();
        }
    }

    private boolean validate(View view){
        boolean isValid;
        if (!TextUtils.isEmpty(id)){
            if (!TextUtils.isEmpty(password)){
                isValid = true;
            }else {
                utilService.showSnackBar(view, "비밀번호를 입력해주세요.");
                isValid = false;
            }
        }else {
            utilService.showSnackBar(view, "아이디를 입력해주세요.");
            isValid = false;
        }
        return isValid;
    }
}