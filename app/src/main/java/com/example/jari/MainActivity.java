package com.example.jari;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.jari.booking.Frag_booking;
import com.example.jari.home.Frag_home;
import com.example.jari.more.Frag_more;
import com.example.jari.person.Frag_person;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private Frag_home frag_home;
    private Frag_person frag_person;
    private Frag_booking frag_booking;
    private Frag_more frag_more;

    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNaviView);

        frag_home = new Frag_home();
        frag_person = new Frag_person();
        frag_booking = new Frag_booking();
        frag_more = new Frag_more();

        replaceFragment(frag_home);

        final TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        final String toolbarMain_title = toolbar_title.getText().toString();

        //하단 버튼 메뉴 Fragment
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home: {
                        replaceFragment(frag_home);
                        toolbar_title.setText(toolbarMain_title);
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        return true;
                    }
                    case R.id.action_person: {
                        replaceFragment(frag_person);
                        toolbar_title.setText(menuItem.getTitle());
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        return true;
                    }
                    case R.id.action_booking: {
                        replaceFragment(frag_booking);
                        toolbar_title.setText(menuItem.getTitle());
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        return true;
                    }
                    case R.id.action_more: {
                        replaceFragment(frag_more);
                        toolbar_title.setText(menuItem.getTitle());
                        actionBar.setDisplayHomeAsUpEnabled(false);
                        return true;
                    }
                    default:
                        break;

                }
                return false;
            }
        });


    }

    // Appbar 아이템 목록 표시
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Appbar 아이템 목록 선택시 발생 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "검색버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //fragment 변경하기
    public void replaceFragment(Fragment frg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, frg)
                .commit();
    }

}

