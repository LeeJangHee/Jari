package com.example.jari.more.notice;

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

public class Frag_more_notice_content extends Fragment {

    private View view;
    private Context context;
    private MainActivity mainActivity;

    private static final String NOTICE_TITLE = "title";
    private static final String NOTICE_DATE = "date";
    private static final String NOTICE_CONTENT = "content";

    private String mNoticeTitle;
    private String mNoticeDate;
    private String mNoticeContent;

    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_content;

    public static Fragment newInstance(String title, String date, String content) {
        Frag_more_notice_content fragment = new Frag_more_notice_content();
        Bundle args = new Bundle();
        args.putString(NOTICE_TITLE, title);
        args.putString(NOTICE_DATE, date);
        args.putString(NOTICE_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoticeTitle = getArguments().getString(NOTICE_TITLE);
            mNoticeDate = getArguments().getString(NOTICE_DATE);
            mNoticeContent = getArguments().getString(NOTICE_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_more_notice_content, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        tv_title = (TextView) view.findViewById(R.id.more_notice_content_title);
        tv_date = (TextView) view.findViewById(R.id.more_notice_content_date);
        tv_content = (TextView) view.findViewById(R.id.more_notice_content_content);

        tv_title.setText(mNoticeTitle);
        tv_date.setText(mNoticeDate);
        tv_content.setText(mNoticeContent);

        return view;
    }
}
