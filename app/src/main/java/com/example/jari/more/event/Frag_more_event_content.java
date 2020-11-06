package com.example.jari.more.event;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;

public class Frag_more_event_content extends Fragment {

    private View view;
    private Context context;
    private MainActivity mainActivity;

    private static final String EVENT_TITLE = "title";
    private static final String EVENT_DATE = "date";
    private static final String EVENT_CONTENT = "content";

    private String mEventTitle;
    private String mEventDate;
    private String mEventContent;

    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_content;

    public static Fragment newInstance(String title, String date, String content) {
        Frag_more_event_content fragment = new Frag_more_event_content();
        Bundle args = new Bundle();
        args.putString(EVENT_TITLE, title);
        args.putString(EVENT_DATE, date);
        args.putString(EVENT_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventTitle = getArguments().getString(EVENT_TITLE);
            mEventDate = getArguments().getString(EVENT_DATE);
            mEventContent = getArguments().getString(EVENT_CONTENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_more_event_content, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        tv_title = (TextView) view.findViewById(R.id.more_event_content_title);
        tv_date = (TextView) view.findViewById(R.id.more_event_content_date);
        tv_content = (TextView) view.findViewById(R.id.more_event_content_content);

        tv_title.setText(mEventTitle);
        tv_date.setText(mEventDate);
        tv_content.setText(mEventContent);

        return view;
    }
}
