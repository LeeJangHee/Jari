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

public class Frag_home_menu_best extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_home_menu_best, container, false);

        //데이터 준비
        ArrayList<ListViewItem> data = new ArrayList<>();
        data.add(new ListViewItem("한식", "주소1", "ok"));
        data.add(new ListViewItem("일식", "주소1", "ok"));
        data.add(new ListViewItem("중식", "주소1", "ok"));
        data.add(new ListViewItem("양식", "주소1", "ok"));
        data.add(new ListViewItem("디저트", "주소1", "ok"));

        //어뎁터
        ListViewAdapter adapter;
        adapter = new ListViewAdapter(data);

        //뷰
        ListView listView = (ListView) view.findViewById(R.id.home_bestmenu_listview);
        listView.setAdapter(adapter);

        return view;
    }
}
