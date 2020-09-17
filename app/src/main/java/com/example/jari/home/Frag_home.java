package com.example.jari.home;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;

public class Frag_home extends Fragment implements View.OnClickListener {
    private View view;
    private String str_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_home, container, false);

        MainActivity.toolbar_title = (TextView) ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);

        ImageView ig_bestMenu = (ImageView) view.findViewById(R.id.menuBest);
        ImageView ig_menuKor = (ImageView) view.findViewById(R.id.menuKor);
        ImageView ig_menuJp = (ImageView) view.findViewById(R.id.menuJp);
        ImageView ig_menuCh = (ImageView) view.findViewById(R.id.menuCh);
        ImageView ig_menuWf = (ImageView) view.findViewById(R.id.menuWf);
        ImageView ig_menuCafe = (ImageView) view.findViewById(R.id.menuCafe);
        ImageView ig_menuBeer = (ImageView) view.findViewById(R.id.menuBeer);
        ImageView ig_menuMap = (ImageView) view.findViewById(R.id.menuMap);

        ig_bestMenu.setOnClickListener(this);
        ig_menuKor.setOnClickListener(this);
        ig_menuJp.setOnClickListener(this);
        ig_menuCh.setOnClickListener(this);
        ig_menuWf.setOnClickListener(this);
        ig_menuCafe.setOnClickListener(this);
        ig_menuBeer.setOnClickListener(this);
        ig_menuMap.setOnClickListener(this);

        return view;
    }

    //메뉴 클릭 Fragment 함수
    public void menuOnClick(String name, Fragment frag) {
        Fragment currentFragment = MainActivity.manager.findFragmentById(R.id.main_layout);
        String currentName = MainActivity.toolbarMain_title;
        ((MainActivity) getActivity()).replaceFragment(frag);
        MainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFragment, currentName));
        MainActivity.toolbar_title.setText(name);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuBest:
                str_name = getString(R.string.menu_best);
                menuOnClick(str_name, new Frag_home_menu_best());
                break;
            case R.id.menuKor:
                str_name = getString(R.string.menu_kor);
                menuOnClick(str_name, new Frag_home_menu_kor());
                break;
            case R.id.menuCh:
                str_name = getString(R.string.menu_ch);
                menuOnClick(str_name, new Frag_home_menu_ch());
                break;
            case R.id.menuJp:
                str_name = getString(R.string.menu_jp);
                menuOnClick(str_name, new Frag_home_menu_jp());
                break;
            case R.id.menuWf:
                str_name = getString(R.string.menu_wf);
                menuOnClick(str_name, new Frag_home_menu_wf());
                break;
            case R.id.menuCafe:
                str_name = getString(R.string.menu_cafe);
                menuOnClick(str_name, new Frag_home_menu_cafe());
                break;
            case R.id.menuBeer:
                str_name = getString(R.string.menu_beer);
                menuOnClick(str_name, new Frag_home_menu_beer());
                break;
            case R.id.menuMap:
                str_name = getString(R.string.menu_map);
                menuOnClick(str_name, new Frag_home_menu_map());
                break;
        }
    }

}
