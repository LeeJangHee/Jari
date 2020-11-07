package com.example.jari.more;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.more.as.Frag_more_as;
import com.example.jari.more.event.Frag_more_event;
import com.example.jari.more.notice.Frag_more_notice;

public class Frag_more extends Fragment implements View.OnClickListener{
    private View view;
    private Context context;
    private MainActivity mainActivity;

    private TextView tv_notice;
    private TextView tv_event;
    private TextView tv_as;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_more, container, false);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        tv_notice = (TextView) view.findViewById(R.id.more_notice);
        tv_event = (TextView) view.findViewById(R.id.more_event);
        tv_as = (TextView) view.findViewById(R.id.more_as);

        tv_notice.setOnClickListener(this);
        tv_event.setOnClickListener(this);
        tv_as.setOnClickListener(this);

        return view;
    }

    //메뉴 클릭 Fragment 함수
    public void menuOnClick(String name, Fragment frag) {
        Fragment currentFragment = mainActivity.manager.findFragmentById(R.id.main_layout);
        String currentName = mainActivity.toolbarMain_title;
        // 프레그먼트 전환
        mainActivity.replaceFragment(frag);

        // 뒤로가기 스택에 현재 프레그먼트 저장
        mainActivity.frag_stack_back.push(new Pair<Fragment, String>(currentFragment, currentName));
        mainActivity.toolbar_title.setText(name);
        mainActivity.toolbarMain_title = name;

        // 액션바 정의
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_20px);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_notice:
                menuOnClick(tv_notice.getText().toString(), new Frag_more_notice());
                break;
            case R.id.more_event:
                menuOnClick(tv_event.getText().toString(), new Frag_more_event());
                break;
            case R.id.more_as:
                menuOnClick(tv_as.getText().toString(), new Frag_more_as());
                break;
        }
    }
}
