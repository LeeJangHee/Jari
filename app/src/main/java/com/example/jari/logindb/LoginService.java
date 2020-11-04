package com.example.jari.logindb;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("users/Register.php")
    Call<LoginData> getRegister(@Field("id") String id,
                                @Field("password") String password,
                                @Field("name") String name,
                                @Field("phone") String phone);

    @FormUrlEncoded
    @POST("users/Validate.php")
    Call<LoginData> getValidate(@Field("id") String id);

    @FormUrlEncoded
    @POST("users/ChangeValidate.php")
    Call<LoginData> getChangeValidate(@Field("id") String id,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("users/Login.php")
    Call<LoginData> getLogin(@Field("id") String id,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("users/update/password.php")
    Call<LoginData> getChangePassword(@Field("id") String id,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("users/update/name.php")
    Call<LoginData> getChangeName(@Field("id") String id,
                             @Field("name") String name);

    @FormUrlEncoded
    @POST("users/update/phone.php")
    Call<LoginData> getChangePhone(@Field("id") String id,
                                      @Field("phone") String phone);

}
