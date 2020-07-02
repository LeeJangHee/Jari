package com.example.jari.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;

import java.util.ArrayList;

public class PersonMenuAdapter extends RecyclerView.Adapter<PersonMenuAdapter.ItemViewHolder> {
    private ArrayList<PersonMenuItem> mPersonRecyclerView = new ArrayList<>();
    private Context context;
    private PersonMenuItem personItemView;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_person_alarm_listitem,
                parent, false);
        personItemView = new PersonMenuItem();
        context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(mPersonRecyclerView.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPersonRecyclerView.size();
    }

    public void addItem(PersonMenuItem personMenuItem ) {
        mPersonRecyclerView.add(personMenuItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_person_alarm;

        private PersonMenuItem personMenuItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_person_alarm = itemView.findViewById(R.id.alarm_recycler_text);
        }

        public void onBind(PersonMenuItem personMenuItem) {
            this.personMenuItem = personMenuItem;

            tv_person_alarm.setText(personMenuItem.getStr_alarm());
        }


    }
}
