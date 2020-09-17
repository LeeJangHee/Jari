package com.example.jari.person;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginOpenHelper;
import com.example.jari.person.alarm.Frag_person_alarm;
import com.example.jari.person.like.Frag_person_like;

import java.util.ArrayList;

public class Frag_person extends Fragment implements View.OnClickListener {
    private View view;
    private String str_name;

    LoginOpenHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    private ArrayList<LoginData> loginArrayList;

    private TextView toolbar_title;
    private TextView tv_alarm;
    private TextView tv_information;
    private TextView tv_like;
    private TextView tv_user_name;
    private TextView tv_user_number;
    private TextView tv_user_people;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        // 로그인 디비 불러오기
        loginArrayList = new LoginOpenHelper(view.getContext()).loadLoginList();

        toolbar_title = ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);
        tv_alarm = view.findViewById(R.id.person_alarm);
        tv_information = view.findViewById(R.id.person_information);
        tv_like = view.findViewById(R.id.person_like);

        tv_alarm.setOnClickListener(this);
        tv_information.setOnClickListener(this);
        tv_like.setOnClickListener(this);

        tv_user_name = view.findViewById(R.id.person_user_name);
        tv_user_number = view.findViewById(R.id.person_user_number);
        tv_user_people = view.findViewById(R.id.person_user_people);

        // Todo: 로그인한 유저의 정보를 person 뷰에 표시해주기
        helper = new LoginOpenHelper(view.getContext());
        db = helper.getWritableDatabase();
        loginArrayList = helper.loadLoginList();

        for (int i = 0; i < loginArrayList.size(); i++) {

        }
        return view;
    }

    public void menuOnClick(String name, Fragment frag) {
        ((MainActivity) getActivity()).replaceFragment(frag);
        MainActivity.frag_stack_back.push(this);
        toolbar_title.setText(name);
        ActionBar actionBar_bestMenu = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar_bestMenu.setTitle("");
        actionBar_bestMenu.setDisplayHomeAsUpEnabled(true);
        actionBar_bestMenu.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_alarm:
                str_name = getString(R.string.person_alarm);
                menuOnClick(str_name, new Frag_person_alarm());
                break;
            case R.id.person_like:
                str_name = getString(R.string.person_like);
                menuOnClick(str_name, new Frag_person_like());
                break;
            case R.id.person_information:
                str_name = getString(R.string.person_information);
                menuOnClick(str_name, new Frag_person_information());
                break;

        }
    }
}
