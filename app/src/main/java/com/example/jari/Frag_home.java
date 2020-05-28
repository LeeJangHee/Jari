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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_home, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        ImageView ig_bestMenu = (ImageView) view.findViewById(R.id.bestMenu);
        final TextView toolbar_title = (TextView) ((MainActivity)getActivity()).findViewById(R.id.toolbar_title);

        ig_bestMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(new Frag_home_bestmenu());

                String str_bestMenu = "명예의 전당";
                toolbar_title.setText(str_bestMenu);
                ActionBar actionBar_bestMenu = ((MainActivity) getActivity()).getSupportActionBar();
                actionBar_bestMenu.setTitle("");
                actionBar_bestMenu.setDisplayHomeAsUpEnabled(true);
                actionBar_bestMenu.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
            }
        });

        return view;
    }
}
