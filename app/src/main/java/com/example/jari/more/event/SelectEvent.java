package com.example.jari.more.event;

import androidx.fragment.app.Fragment;

public interface SelectEvent {
    void onClickEvent(String title, String date, String content, Fragment fragment);
}
