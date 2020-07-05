package com.example.jari.person.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;

import java.util.Arrays;
import java.util.List;

public class Frag_person_alarm extends Fragment {
    private View view;
    private PersonAlarmAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person_alarm, container, false);

        init();
        getData();

        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.alarm_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PersonAlarmAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        List<String> listAlarm = Arrays.asList("알람1", "알람1", "알람1", "알람1", "알람1", "알람1", "알람1");

        for (int i = 0; i < listAlarm.size(); i++) {
            PersonAlarmItem personAlarmItem = new PersonAlarmItem();

            personAlarmItem.setStr_alarm(listAlarm.get(i));

            adapter.addItem(personAlarmItem);
        }
        adapter.notifyDataSetChanged();
    }
}
