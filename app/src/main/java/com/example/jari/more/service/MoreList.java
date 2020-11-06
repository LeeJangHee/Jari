package com.example.jari.more.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreList {
    @SerializedName("notice")
    @Expose
    private List<Notice> noticeList = null;
    @SerializedName("event")
    @Expose
    private List<Notice> eventList = null;

    public List<Notice> getEventList() {
        return eventList;
    }

    public void setEventList(List<Notice> eventList) {
        this.eventList = eventList;
    }

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }
}
