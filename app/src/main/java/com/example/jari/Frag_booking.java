package com.example.jari;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Frag_booking extends Fragment {
    private View view;
    private ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull final ViewGroup container,
                             @NonNull Bundle saveInstanceState) {

        view = (View) inflater.inflate(R.layout.frag_booking, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

        //데이터 준비
        ArrayList<ListViewItem> data = new ArrayList<>();
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));
        data.add(new ListViewItem("가게1", "주소1", "ok"));

        //어뎁터
        adapter = new ListViewAdapter(data);

        //뷰
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), position + "번째 아이탬", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

