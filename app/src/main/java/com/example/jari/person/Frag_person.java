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

public class Frag_person extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_sign_in;
    private Button btn_sign_up;
    private String str_name;
    private TextView toolbar_title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        btn_sign_in = view.findViewById(R.id.btn_sign_in);
        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        toolbar_title = ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);

        btn_sign_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

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
        }
    }
}
