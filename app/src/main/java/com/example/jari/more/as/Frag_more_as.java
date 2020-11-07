package com.example.jari.more.as;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jari.R;

public class Frag_more_as extends Fragment {

    private View view;
    private Context context;

    private Button btn_as;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_more_as, container, false);
        context = container.getContext();

        btn_as = (Button) view.findViewById(R.id.more_as_btn);
        btn_as.setOnClickListener(v -> {
            // 버튼을 눌렀을 때 전화로 넘겨야함
            Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01023069431"));
            startActivity(it);
        });

        return view;
    }
}
