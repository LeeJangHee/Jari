package com.example.jari;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Frag_home frag_home;
    private Frag_person frag_person;
    private Frag_booking frag_booking;
    private Frag_more frag_more;

    private Toolbar toolbar;


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


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_home)
                                .commitAllowingStateLoss();
                        frag_home.viewGroup.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                        return true;
                    }
                    case R.id.action_person: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_person)
                                .commitAllowingStateLoss();
                        frag_person.viewGroup.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                        return true;
                    }
                    case R.id.action_booking: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_booking)
                                .commitAllowingStateLoss();
                        frag_booking.viewGroup.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                        return true;
                    }
                    case R.id.action_more: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frag_more)
                                .commitAllowingStateLoss();
                        frag_more.viewGroup.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                        return true;
                    }
                    default:
                        break;

                }
                return false;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);

        return true;
    }



}
