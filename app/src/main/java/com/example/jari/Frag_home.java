package com.example.jari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class Frag_home extends Fragment {
    View view;
    TextView toolbar_title;
    String str_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_home, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        toolbar_title = (TextView) ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);

        ImageView ig_bestMenu = (ImageView) view.findViewById(R.id.menuBest);
        ImageView ig_menuKor = (ImageView) view.findViewById(R.id.menuKor);
        ImageView ig_menuJp = (ImageView) view.findViewById(R.id.menuJp);
        ImageView ig_menuCh = (ImageView) view.findViewById(R.id.menuCh);
        ImageView ig_menuWf = (ImageView) view.findViewById(R.id.menuWf);
        ImageView ig_menuCafe = (ImageView) view.findViewById(R.id.menuCafe);
        ImageView ig_menuBeer = (ImageView) view.findViewById(R.id.menuBeer);


        ig_bestMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_best);
                menuOnClick(str_name, new Frag_home_menu_best());
            }
        });

        ig_menuKor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_kor);
                menuOnClick(str_name, new Frag_home_menu_kor());
            }
        });

        ig_menuJp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_jp);
                menuOnClick(str_name, new Frag_home_menu_jp());
            }
        });

        ig_menuCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_ch);
                menuOnClick(str_name, new Frag_home_menu_ch());
            }
        });

        ig_menuWf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_wf);
                menuOnClick(str_name, new Frag_home_menu_wf());
            }
        });

        ig_menuCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_cafe);
                menuOnClick(str_name, new Frag_home_menu_cafe());
            }
        });

        ig_menuBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = getString(R.string.menu_beer);
                menuOnClick(str_name, new Frag_home_menu_beer());
            }
        });

        return view;
    }

    //메뉴 클릭 Fragment 함수
    private void menuOnClick(String name, Fragment frag) {
        ((MainActivity) getActivity()).replaceFragment(frag);
        toolbar_title.setText(name);
        ActionBar actionBar_bestMenu = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar_bestMenu.setTitle("");
        actionBar_bestMenu.setDisplayHomeAsUpEnabled(true);
        actionBar_bestMenu.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }

}
