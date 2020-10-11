package com.example.jari;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginOpenHelper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<LoginData> loginArrayList;
    private long backBtnTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginArrayList = new LoginOpenHelper(this).loadLoginList();
    }

    public void Check(View v) {
        EditText et_name = findViewById(R.id.login_name);
        EditText et_number = findViewById(R.id.login_number);
        EditText et_people = findViewById(R.id.login_people);

        String str_name = et_name.getText().toString();
        String str_number = et_number.getText().toString();
        String str_people = et_people.getText().toString();

        ContentValues contentValues = new ContentValues();

        // 데이터를 확인 후 디비에 저장
        if (str_name == null || str_number == null || str_people == null) {
            Toast.makeText(LoginActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            // DB 저장하기
            contentValues.put(LoginOpenHelper.PHONE_NUMBER, str_number);
            contentValues.put(LoginOpenHelper.NAME, str_name);
            contentValues.put(LoginOpenHelper.PEOPLE, str_people);

            SQLiteDatabase db = LoginOpenHelper.getInstance(this).getWritableDatabase();
            long newRowID = db.insert(LoginOpenHelper.tableName, null, contentValues);

            if (newRowID == -1) {
                Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
            } else {
                // 전부 작정하였으면 -> MainActivity 이동
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                finish();
            }
        }

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
