package com.example.jari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginService;
import com.example.jari.retrofit2.ServerConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    private EditText et_id;
    private EditText et_password;
    private EditText et_people;

    private Button btn_sign_in;
    private Button btn_sign_up;

    private LoginService loginService;
    private AlertDialog dialog;


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
        loginService = ServerConnect.getClient().create(LoginService.class);
        Call<LoginData> callLogin = loginService.getLogin(et_id.getText().toString(), et_password.getText().toString());

        callLogin.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData.getSuccess().equals("true")) {    // 로그인 성공
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("PEOPLE", et_people.getText().toString());
                        it.putExtra("PASSWORD", loginData.getPassword());
                        it.putExtra("ID", loginData.getId());
                        it.putExtra("NAME", loginData.getName());
                        it.putExtra("PHONE", loginData.getPhone());
                        startActivity(it);
                        finish();
                    } else {    // 로그인 실패
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("로그인 실패")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });

    }

    public void SignUp(View v) {
        // 회원가입 버튼 클릭시 수행되는 부분
        Intent it = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(it);
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
