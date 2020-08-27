package com.example.jari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private Button btn_ok;
    private EditText et_name;
    private EditText et_number;
    private EditText et_people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_ok = findViewById(R.id.login_ok);
        et_name = findViewById(R.id.login_name);
        et_number = findViewById(R.id.login_number);
        et_people = findViewById(R.id.login_people);

        // 확인 버튼 클릭
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: 데이터를 확인 후 디비에 저장

                // 전부 작정하였으면 -> MainAcitvity 이동
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}
