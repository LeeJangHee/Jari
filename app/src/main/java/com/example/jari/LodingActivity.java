package com.example.jari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class LodingActivity extends Activity {
    int RODING_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
/**
 * retrofit2 사용 예시2
 *
 *     << 전역 변수 선언 >>
 *     ServerConnect serverConnect;
 *     RetrofitService service;
 *
        service = ServerConnect.getClient().create(RetrofitService.class);

        Call<Result> call = service.getStoreJp();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                List<Store> storeList = result.getStoreJp();
                for (Store st : storeList) {
                    Log.d("storeList",
                            "id: " + st.getId()
                                    + "\nname: " + st.getName()
                                    + "\nphone: " + st.getPhone()
                                    + "\naddress: " + st.getAddress()
                                    + "\nlatitude: " + st.getLatitude()
                                    + "\nlongitude: " + st.getLongitude());
                }
                Log.d("php", String.valueOf(storeList.get(3)));

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
**/
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LodingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, RODING_TIME);
    }
}
