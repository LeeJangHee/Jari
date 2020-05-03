package com.example.jari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_more extends Fragment {
    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_more, container, false);
        viewGroup.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        return viewGroup;
    }
}
