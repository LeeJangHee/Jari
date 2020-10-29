package com.example.jari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


public class LodingActivity extends Activity {
    int RODING_TIME = 3000;
    private long backBtnTime = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LodingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, RODING_TIME);
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (0 <= gapTime && 2000 >= gapTime){
            handler.removeMessages(0);
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
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
