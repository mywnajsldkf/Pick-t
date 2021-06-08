package com.example.pickt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.pickt.R;
import com.example.pickt.UtilService.UtilService;

import okhttp3.internal.Util;

public class SignupActivity extends AppCompatActivity {
    private EditText idEditText;
    private EditText passwordEditText;
    // private EditText passwordCheckEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button nextButton;

    private String id, password, name, phone, email;
    UtilService utilService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        idEditText = (EditText)findViewById(R.id.userID);
        passwordEditText = (EditText)findViewById(R.id.userPassword);
        // passwordCheckEditText = (EditText)findViewById(R.id.confirmPassword);
        nameEditText = (EditText)findViewById(R.id.userName);
        phoneEditText = (EditText)findViewById(R.id.userNumber);
        emailEditText = (EditText)findViewById(R.id.userEmail);

        utilService = new UtilService();

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilService.hideKeyboard(v, SignupActivity.this);

                id = idEditText.getText().toString();
                password = passwordEditText.getText().toString();
                name = nameEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                email = emailEditText.getText().toString();

                if (validate(v)){
                    registerUser(v);
                }
            }
        });
    }

    // 입력 받은 값 전달 -> register license result activity 에서 한번에 저장이 이루어질 예정
    private void registerUser(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterLicenseActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("password", password);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }

    // 필수적으로 들어가야하는 EditText에 비어있는 값이 있는지 검증하는 함수
    public boolean validate(View view){
        boolean isValid;
        if (!TextUtils.isEmpty(id)){
            if (!TextUtils.isEmpty(password)){
                if (!TextUtils.isEmpty(name)){
                    if (!TextUtils.isEmpty(phone)){
                        if (!TextUtils.isEmpty(email)){
                            isValid=true;
                        }else {
                            utilService.showSnackBar(view, "이메일 번호를 입력해주세요.");
                            isValid=false;
                        }
                    }else {
                        utilService.showSnackBar(view, "핸드폰 번호를 입력해주세요.");
                        isValid=false;
                    }
                }else {
                    utilService.showSnackBar(view, "이름을 입력해주세요.");
                    isValid=false;
                }
            }else {
                utilService.showSnackBar(view, "비밀번호를 입력해주세요.");
                isValid=false;
            }
        }else {
            utilService.showSnackBar(view, "아이디를 입력해주세요.");
            isValid=false;
        }
        return isValid;
    }
}