package com.example.jari.retrofit2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnect {
    private String baseUrl = "http://wkdgml96.iptime.org:8080/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://wkdgml96.iptime.org:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
/**
 * retrofit2 사용 예제
 *
 *  public ServerConnect() {
 *
 *       RetrofitService service = retrofit.create(RetrofitService.class);
 *       service.getStore().enqueue(new Callback<Result>() {
 *           @Override
 *           public void onResponse(Call<Result> call, Response<Result> response) {
 *               Result result = response.body();
 *               List<Store> storeList = result.getStoreKor();
 *               for (Store st : storeList) {
 *                   Log.d("storeList",
 *                           "id: " + st.getId()
 *                                   + "\nname: " + st.getName()
 *                                   + "\nphone: " + st.getPhone()
 *                                   + "\naddress: " + st.getAddress()
 *                                   + "\nlatitude: " + st.getLatitude()
 *                                   + "\nlongitude: " + st.getLongitude());
 *               }
 *               Log.d("php", String.valueOf(storeList.get(3)));
 *
 *           }
 *
 *           @Override
 *           public void onFailure(Call<Result> call, Throwable t) {
 *
 *           }
 *       });
 *   }
 **/


}
