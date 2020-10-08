package com.example.jari.retrofit2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    String url = "http://wkdgml96.iptime.org:8080";

    @GET("/dbconnect.php")
    Call<ArrayList<Store>> getData();
}
