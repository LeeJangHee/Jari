package com.example.jari.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jari.FragmentClickFunction;
import com.example.jari.R;
import com.example.jari.home.Frag_home;

public class Frag_person extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private String str_name;

    private FragmentClickFunction fragmentClick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        fragmentClick = new FragmentClickFunction(new Frag_person(), "");

        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 화면을 구성하는 플레그먼트 생성
            str_name = getString(R.string.person_sign_in);
            fragmentClick.setFragment(new Frag_person_sign_in());
            fragmentClick.setToolbarName(str_name);
            fragmentClick.menuOnClick(fragmentClick.getToolbarName(), fragmentClick.getFragment());
            new Frag_home().menuOnClick(str_name, new Frag_person_sign_in());
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 화면을 구성하는 플레그먼트 생성
                str_name = getString(R.string.person_sign_up);
                new Frag_home().menuOnClick(str_name, new Frag_person_sign_up());
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
