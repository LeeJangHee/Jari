package com.example.jari;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginService;
import com.example.jari.retrofit2.ServerConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_id;
    private EditText et_password;
    private EditText et_name;
    private EditText et_phone;

    private Button btn_make;
    private Button btn_cancel;
    private Button btn_idCheck;

    LoginService loginService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_id = (EditText) findViewById(R.id.sign_up_id);
        et_password = (EditText) findViewById(R.id.sign_up_password);
        et_name = (EditText) findViewById(R.id.sign_up_name);
        et_phone = (EditText) findViewById(R.id.sign_up_phone);

        btn_make = (Button) findViewById(R.id.sign_up_make);
        btn_cancel = (Button) findViewById(R.id.sign_up_cancel);
        btn_idCheck = (Button) findViewById(R.id.sign_up_idCheck);

        loginService = ServerConnect.getClient().create(LoginService.class);
    }

    // 아이디 중복 확인
    public void idCheck(View v) {
        Call<LoginData> call =  loginService.getValidate(et_id.getText().toString());

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse: 중복확인"+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                // 네트워크 연결 실패
            }
        });
    }

    // 회원가입 취소
    public void cancel(View v) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
        Toast.makeText(this, "회원가입 취소", Toast.LENGTH_SHORT).show();
    }

    // 회원가입 성공
    public void make(View v) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }
}
