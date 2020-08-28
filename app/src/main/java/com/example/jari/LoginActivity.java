package com.example.jari;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginOpenHelper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    private Button btn_ok;
    private EditText et_name;
    private EditText et_number;
    private EditText et_people;

    private ArrayList<LoginData> loginArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_ok = findViewById(R.id.login_ok);
        et_name = findViewById(R.id.login_name);
        et_number = findViewById(R.id.login_number);
        et_people = findViewById(R.id.login_people);

        final String str_name = et_name.getText().toString();
        final String str_number = et_number.getText().toString();
        final String str_people = et_people.getText().toString();

        loginArrayList = new LoginOpenHelper(this).loadLoginList();

        // 확인 버튼 클릭
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();

                // Todo: 데이터를 확인 후 디비에 저장
                if(str_name == null || str_number == null || str_people == null) {
                    Toast.makeText(LoginActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // DB 저장하기
                    contentValues.put(LoginOpenHelper.NAME, str_name);
                    contentValues.put(LoginOpenHelper.PHONE_NUMBER, str_number);
                    contentValues.put(LoginOpenHelper.PEOPLE, str_people);

                    SQLiteDatabase db = LoginOpenHelper.getInstance(LoginActivity.this).getWritableDatabase();
                    long newRowID = db.insert(LoginOpenHelper.tableName, null, contentValues);

                    if(newRowID == -1) {
                        Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
                    } else {
                        // 전부 작정하였으면 -> MainAcitvity 이동
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                        finish();
                    }
                }

            }
        });
    }
}
