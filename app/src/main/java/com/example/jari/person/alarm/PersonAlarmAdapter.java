package com.example.jari.person.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jari.R;

import java.util.ArrayList;

public class PersonAlarmAdapter extends RecyclerView.Adapter<PersonAlarmAdapter.ItemViewHolder> {
    private ArrayList<PersonAlarmItem> mPersonRecyclerView = new ArrayList<>();
    private Context context;
    private PersonAlarmItem personItemView;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_person_alarm_listitem,
                parent, false);
        personItemView = new PersonAlarmItem();
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

    public void addItem(PersonAlarmItem personAlarmItem) {
        mPersonRecyclerView.add(personAlarmItem);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_person_alarm;

        private PersonAlarmItem personAlarmItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_person_alarm = itemView.findViewById(R.id.alarm_tv_text);
        }

        public void onBind(PersonAlarmItem personAlarmItem) {
            this.personAlarmItem = personAlarmItem;

            tv_person_alarm.setText(personAlarmItem.getStr_alarm());
        }


    }
}
