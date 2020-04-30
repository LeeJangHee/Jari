package com.example.jari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Frag_home frag_home;
    Frag_person frag_person;
    Frag_booking frag_booking;
    Frag_more frag_more;

    private Toolbar toolbar;

    @SuppressLint({"WrongConstant", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNaviView);

        frag_home = new Frag_home();
        frag_person = new Frag_person();
        frag_booking = new Frag_booking();
        frag_more = new Frag_more();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_home).commitAllowingStateLoss();

        //아이템 선택
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_home)
                                .commitAllowingStateLoss();
                        break;
                    }
                    case R.id.action_person: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_person)
                                .commitAllowingStateLoss();
                        break;
                    }
                    case R.id.action_booking: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_booking)
                                .commitAllowingStateLoss();
                        break;
                    }
                    case R.id.action_more: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_more)
                                .commitAllowingStateLoss();
                        break;
                    }

                }
                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);

        return true;
    }
}
