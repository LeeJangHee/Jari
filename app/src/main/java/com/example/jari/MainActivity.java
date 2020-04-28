package com.example.jari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    frag1 frag1;
    frag2 frag2;
    frag3 frag3;
    frag4 frag4;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.main_toolbar);

        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNaviView);

        frag1 = new frag1();
        frag2 = new frag2();
        frag3 = new frag3();
        frag4 = new frag4();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,frag1).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,frag1)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.action_my:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,frag2)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.action_book:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,frag3)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.action_more:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,frag4)
                                .commitAllowingStateLoss();
                        return true;
                    }

                    default: return false;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);

        return true;
    }
}
