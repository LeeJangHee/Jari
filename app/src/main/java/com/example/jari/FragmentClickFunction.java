package com.example.jari;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class FragmentClickFunction extends Fragment {
    private Fragment fragment;
    private String toolbarName;
    private TextView toolbar_title;

    public FragmentClickFunction(Fragment fragment, String toolbarName) {
        this.fragment = fragment;
        this.toolbarName = toolbarName;
    }

    public FragmentClickFunction(int contentLayoutId, Fragment fragment, String toolbarName) {
        super(contentLayoutId);
        this.fragment = fragment;
        this.toolbarName = toolbarName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getToolbarName() {
        return toolbarName;
    }

    public void setToolbarName(String toolbarName) {
        this.toolbarName = toolbarName;
    }

    public void menuOnClick(String name, Fragment frag) {
        toolbar_title = ((MainActivity) getActivity()).findViewById(R.id.toolbar_title);
        ((MainActivity) getActivity()).replaceFragment(frag);
        toolbar_title.setText(name);
        ActionBar actionBar_bestMenu = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar_bestMenu.setTitle("");
        actionBar_bestMenu.setDisplayHomeAsUpEnabled(true);
        actionBar_bestMenu.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }
}
