package com.example.jari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.retrofit2.Store;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LodingActivity extends Activity {
    int RODING_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wkdgml96.iptime.org:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        service.getData().enqueue(new Callback<ArrayList<Store>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Store>> call, @NonNull Response<ArrayList<Store>> response) {
                if (response.isSuccessful()){
                    ArrayList<Store> result = response.body();
                    Log.d("php: ", result.toString());
                }
                else{
                    Log.d("php: ", "실패");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Store>> call, @NonNull Throwable t) {
                Log.d("php: ", Objects.requireNonNull(t.getMessage()));
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LodingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, RODING_TIME);
    }
}
