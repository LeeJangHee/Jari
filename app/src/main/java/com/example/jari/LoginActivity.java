package com.example.jari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    private EditText et_id;
    private EditText et_password;
    private EditText et_people;

    private Button btn_sign_in;
    private Button btn_sign_up;

    private String str_id;
    private String str_password;
    private String str_people;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.login_id);
        et_password = (EditText) findViewById(R.id.login_password);
        et_people = (EditText) findViewById(R.id.login_people);

        btn_sign_in = (Button) findViewById(R.id.login_sign_in);
        btn_sign_in = (Button) findViewById(R.id.login_sign_up);

    }

    public void SignIn(View v) {
        // 로그인 버튼 클릭시 수행되는 부분
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void SignUp(View v) {
        // 회원가입 버튼 클릭시 수행되는 부분
        Intent it = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (0 <= gapTime && 2000 >= gapTime){
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
