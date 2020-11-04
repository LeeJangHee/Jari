package com.example.jari;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity {

    private EditText et_id;
    private EditText et_password;
    private EditText et_name;
    private EditText et_phone;

    private AlertDialog dialog;
    private boolean validate = false;

    LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_id = (EditText) findViewById(R.id.sign_up_id);
        et_password = (EditText) findViewById(R.id.sign_up_password);
        et_name = (EditText) findViewById(R.id.sign_up_name);
        et_phone = (EditText) findViewById(R.id.sign_up_phone);

        final Button btn_make = (Button) findViewById(R.id.sign_up_make);
        final Button btn_cancel = (Button) findViewById(R.id.sign_up_cancel);
        final Button btn_idCheck = (Button) findViewById(R.id.sign_up_idCheck);

        loginService = ServerConnect.getClient().create(LoginService.class);

    }

    // 아이디 중복 확인
    public void idCheck(View v) {
        String userID = et_id.getText().toString();

        if (validate) {
            return;
        }

        if (userID.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
            return;
        }

        loginService.getValidate(userID).enqueue(new Callback<LoginData>() {

            @Override
            public void onResponse(Call<LoginData> call, retrofit2.Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    if (loginData.getSuccess().equals("true")) {
                        // 아이디 중복 없음
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                .setPositiveButton("확인", null)
                                .create();
                        dialog.show();
                        et_id.setEnabled(false);
                        validate = true;
                    } else {
                        // 아이디 중복
                        dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                // 네트워크 통신실패
                Log.d("TAG", "onFailure: idCheck " + t.getMessage());
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
        String userId = et_id.getText().toString();
        String userPassword = et_password.getText().toString();
        String userName = et_name.getText().toString();
        String userPhone = et_phone.getText().toString();

        if (!validate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                    .setNegativeButton("확인", null)
                    .create();
            dialog.show();
            return;
        }

        if (userId.equals("") || userPassword.equals("") || userName.equals("") || userPhone.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            dialog = builder.setMessage("빈칸 없이 입력해주세요.")
                    .setNegativeButton("확인", null)
                    .create();
            dialog.show();
            return;
        }

        Call<LoginData> callRegister = loginService.getRegister(userId, userPassword, userName, userPhone);
        callRegister.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, retrofit2.Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    DialogInterface.OnClickListener loginButton = (dialog, which) -> finish();
                    if (loginData.getSuccess().equals("true")) {
                        dialog = builder.setMessage("회원등록에 성공했습니다.")
                                .setPositiveButton("확인", loginButton)
                                .create();
                        dialog.show();
                    } else {
                        dialog = builder.setMessage("회원등록에 실패했습니다.")
                                .setNegativeButton("확인", null)
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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
