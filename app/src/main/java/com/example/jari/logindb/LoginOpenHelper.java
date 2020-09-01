package com.example.jari.logindb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LoginOpenHelper extends SQLiteOpenHelper {
    private static LoginOpenHelper sInstaces;

    public static final String tableName = "Users";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "number";
    public static final String PEOPLE = "people";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Login.db";
    public static final String SQL_CREATE_ENTRIES =
            String.format("create table %s (%s char(36) primary key, %s text not null, %s text)",
                    tableName, PHONE_NUMBER, NAME, PEOPLE);

    public static final String SQL_DELETE_ENTRIES = String.format("drop table if exists %s", tableName);

    public static LoginOpenHelper getInstance(Context context) {
        if(sInstaces == null) {
            sInstaces = new LoginOpenHelper(context);
        }
        return sInstaces;
    }

    public LoginOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public ArrayList<LoginData> loadLoginList() {
        ArrayList<LoginData> loginArrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] userInfo = {
                LoginOpenHelper.PHONE_NUMBER,
                LoginOpenHelper.NAME,
                LoginOpenHelper.PEOPLE
        };

        Cursor cursor = db.query(LoginOpenHelper.tableName, userInfo,
                null, null, null, null, null);

        //커서 처음~끝 조회
        while(cursor.moveToNext()) {
            LoginData loginData = new LoginData();
            //객체에 DB 저장
            loginData.name = cursor.getString(cursor.getColumnIndex(NAME));
            loginData.phone_number = cursor.getString(cursor.getColumnIndex(PHONE_NUMBER));
            loginData.people = cursor.getString(cursor.getColumnIndex(PEOPLE));

            loginArrayList.add(loginData);
        }
        return loginArrayList;
    }
}
