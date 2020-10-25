package com.example.jari.retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    // url = "http://wkdgml96.iptime.org:8080/";

    @GET("storeKor.php")
    Call<Result> getStoreKor();

    @GET("storeJp.php")
    Call<Result> getStoreJp();

    @GET("storeCh.php")
    Call<Result> getStoreCh();

    @GET("storeWf.php")
    Call<Result> getStoreWf();

    @GET("storeCafe.php")
    Call<Result> getStoreCafe();

    @GET("storeBeer.php")
    Call<Result> getStoreBeer();

    @GET("storeBest.php")
    Call<Result> getStoreBest();

    @GET("storeInfo.php")
    Call<Result> getStoreInfo(@Query("name") String storeName);

    @GET("storeFood.php")
    Call<Result> getStoreFood();
}
