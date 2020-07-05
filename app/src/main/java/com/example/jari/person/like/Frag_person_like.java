package com.example.jari.person.like;

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

public class Frag_person_like extends Fragment {
    private View view;
    private PersonLikeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person_like, container, false);

        init();
        getData();

        return view;
    }

    private void init() {
        RecyclerView recyclerView = view.findViewById(R.id.like_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new PersonLikeAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        List<String> listTitle = Arrays.asList("가게1", "가게2", "가게3", "가게4", "가게5");
        List<String> listAddress = Arrays.asList("address1", "address2", "address3", "address4", "address5");
        List<Integer> listHeart = Arrays.asList(R.drawable.ic_heart, R.drawable.ic_heart, R.drawable.ic_heart, R.drawable.ic_heart, R.drawable.ic_heart);
        List<Integer> listIcon = Arrays.asList(R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        for(int i = 0 ; i < listTitle.size(); i++){
            PersonLikeItem personLikeItem = new PersonLikeItem();

            personLikeItem.setLike_titleStr(listTitle.get(i));
            personLikeItem.setLike_addressStr(listAddress.get(i));
            personLikeItem.setLike_heartId(listHeart.get(i));
            personLikeItem.setLike_iconId(listIcon.get(i));

            adapter.addItem(personLikeItem);
        }
        adapter.notifyDataSetChanged();
    }

}

