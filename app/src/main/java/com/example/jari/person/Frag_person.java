package com.example.jari.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.person.alarm.Frag_person_alarm;
import com.example.jari.person.like.Frag_person_like;

public class Frag_person extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private String str_name;

    private TextView toolbar_title;
    private TextView tv_alarm;
    private TextView tv_review;
    private TextView tv_information;
    private TextView tv_like;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        toolbar_title = ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);
        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        tv_alarm = view.findViewById(R.id.person_alarm);
        tv_review = view.findViewById(R.id.person_review);
        tv_information = view.findViewById(R.id.person_information);
        tv_like = view.findViewById(R.id.person_like);

        btn_sign_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        tv_alarm.setOnClickListener(this);
        tv_review.setOnClickListener(this);
        tv_information.setOnClickListener(this);
        tv_like.setOnClickListener(this);

        return view;
    }

    public void menuOnClick(String name, Fragment frag) {
        ((MainActivity) getActivity()).replaceFragment(frag);
        toolbar_title.setText(name);
        ActionBar actionBar_bestMenu = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar_bestMenu.setTitle("");
        actionBar_bestMenu.setDisplayHomeAsUpEnabled(true);
        actionBar_bestMenu.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                str_name = getString(R.string.person_sign_in);
                menuOnClick(str_name, new Frag_person_sign_in());
                break;
            case R.id.btn_sign_up:
                str_name = getString(R.string.person_sign_up);
                menuOnClick(str_name, new Frag_person_sign_up());
                break;
            case R.id.person_alarm:
                str_name = getString(R.string.person_alarm);
                menuOnClick(str_name, new Frag_person_alarm());
                break;
            case R.id.person_review:
                str_name = getString(R.string.person_review);
                menuOnClick(str_name, new Frag_person_review());
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