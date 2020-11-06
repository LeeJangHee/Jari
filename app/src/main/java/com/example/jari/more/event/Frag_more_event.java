package com.example.jari.more.event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.more.service.MoreList;
import com.example.jari.more.service.MoreService;
import com.example.jari.more.service.Notice;
import com.example.jari.retrofit2.ServerConnect;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_more_event extends Fragment {

    private View view;
    private Context context;
    private MainActivity mainActivity;

    private EventMenuAdapter adapter;

    private MoreService moreService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_more_event, container, false);
        init();
        getEventService();
        return view;
    }

    private void getEventService() {
        moreService = ServerConnect.getClient().create(MoreService.class);
        moreService.getEvent().enqueue(new Callback<MoreList>() {
            @Override
            public void onResponse(Call<MoreList> call, Response<MoreList> response) {
                if (response.isSuccessful()) {
                    MoreList moreList = response.body();
                    List<Notice> eventList = moreList.getEventList();
                    getData(eventList);
                }
            }

            @Override
            public void onFailure(Call<MoreList> call, Throwable t) {

            }
        });
    }

    private void getData(List<Notice> eventList) {
        List<String> listTitle = new ArrayList<>();
        List<String> listDate = new ArrayList<>();
        List<String> listContent = new ArrayList<>();

        for (Notice notice : eventList) {
            listTitle.add(notice.getTitle());
            listDate.add(notice.getDate());
            listContent.add(notice.getContent());
        }

        for (int i = 0; i < listTitle.size(); i++) {
            EventMenuItem eventMenuItem = new EventMenuItem();

            eventMenuItem.setTitleStr(listTitle.get(i));
            eventMenuItem.setDateStr(listDate.get(i));
            eventMenuItem.setContentStr(listContent.get(i));

            adapter.addItem(eventMenuItem);
        }
        adapter.notifyDataSetChanged();
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.event_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new EventMenuAdapter();
        recyclerView.setAdapter(adapter);
    }
}
