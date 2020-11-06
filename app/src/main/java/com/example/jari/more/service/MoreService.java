package com.example.jari.more.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoreService {
    @GET("more/notice.php")
    Call<MoreList> getNotice();

    @GET("more/event.php")
    Call<MoreList> getEvent();
}
