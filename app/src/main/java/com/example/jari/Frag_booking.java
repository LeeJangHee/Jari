package com.example.jari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Frag_booking extends Fragment {
    private View view;
    private ListViewAdapter adapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {

        ArrayList<ListViewItem> data = new ArrayList<>();
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));

        adapter = new ListViewAdapter(data);

        view = (View) inflater.inflate(R.layout.frag_booking, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        return view;
    }
}

