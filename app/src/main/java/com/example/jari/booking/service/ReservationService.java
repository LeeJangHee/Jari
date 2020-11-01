package com.example.jari.booking.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReservationService {
    @FormUrlEncoded
    @POST("reservation/ok.php")
    Call<Reservation> getRegister(@Field("userID") String userID,
                                @Field("storeName") String name,
                                @Field("storePhone") String phone,
                                @Field("storeAddress") String address,
                                @Field("storeProfile") String profile);
    @GET("reservation/num.php")
    Call<Reservation> getWaitNum(@Query("storeName") String storeName);
}
