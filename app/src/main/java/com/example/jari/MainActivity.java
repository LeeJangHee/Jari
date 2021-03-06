package com.example.jari;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.jari.booking.Frag_booking;
import com.example.jari.home.Frag_home;
import com.example.jari.home.SelectStore;
import com.example.jari.more.Frag_more;
import com.example.jari.person.Frag_person;
import com.example.jari.retrofit2.RetrofitService;
import com.example.jari.search.Frag_search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.NaverMapSdk;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements SelectStore {

    private BottomNavigationView bottomNavigationView;

    private Frag_home frag_home;
    private Frag_person frag_person;
    private Frag_booking frag_booking;
    private Frag_more frag_more;

    public static Stack<Pair<Fragment, String>> frag_stack_back;
    public static FragmentManager manager;

    public Toolbar toolbar;
    public ActionBar actionBar;
    public static TextView toolbar_title;
    public static String toolbarMain_title;

    private long backBtnTime = 0;

    public static String id;
    public static String password;
    public static String name;
    public static String phone;
    public static String people;
    private SearchView searchView;
    private EditText et_searchView;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        frag_stack_back = new Stack<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인 정보 받아오기
        Intent mainIntent = getIntent();
        id = mainIntent.getStringExtra("ID");
        password = mainIntent.getStringExtra("PASSWORD");
        name = mainIntent.getStringExtra("NAME");
        phone = mainIntent.getStringExtra("PHONE");
        people = mainIntent.getStringExtra("PEOPLE");


        // 네이버 지도 호출
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("azqxoh0k6z"));

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNaviView);

        frag_home = new Frag_home();
        frag_person = new Frag_person();
        frag_booking = new Frag_booking();
        frag_more = new Frag_more();

        replaceFragment(frag_home);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbarMain_title = getText(R.string.app_name).toString();

        //하단 버튼 메뉴 Fragment
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_home: {
                    replaceFragment(frag_home);
                    toolbarMain_title = getText(R.string.app_name).toString();
                    toolbar_title.setText(toolbarMain_title);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    if (!frag_stack_back.empty()) frag_stack_back.clear();
                }
                return true;

                case R.id.action_person: {
                    replaceFragment(frag_person);
                    toolbarMain_title = menuItem.getTitle().toString();
                    toolbar_title.setText(toolbarMain_title);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    if (!frag_stack_back.empty()) frag_stack_back.clear();

                    return true;
                }
                case R.id.action_booking: {
                    replaceFragment(frag_booking);
                    toolbarMain_title = menuItem.getTitle().toString();
                    toolbar_title.setText(toolbarMain_title);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    if (!frag_stack_back.empty()) frag_stack_back.clear();
                    return true;
                }
                case R.id.action_more: {
                    replaceFragment(frag_more);
                    toolbarMain_title = menuItem.getTitle().toString();
                    toolbar_title.setText(toolbarMain_title);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    if (!frag_stack_back.empty()) frag_stack_back.clear();
                    return true;
                }
                default:
                    break;
            }
            return false;
        });


    }

    // Appbar 아이템 목록 표시
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        et_searchView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        et_searchView.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(12)});
        searchView.setQueryHint("가게 이름 검색");

        ImageView ig_search = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ig_search.setColorFilter(Color.BLACK);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색이 완료 되었을
                onClickStore(query, new Frag_search());
                // 툴바 닫기
                toolbar.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 12) {
                    Toast.makeText(MainActivity.this, "검색어는 12자 까지만 가능합니다.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    // Appbar 아이템 목록 선택시 발생 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 앱바의 뒤로가기 버튼이다.
                // 플레그먼트 이름을 받아서 replaceFragment를 실행시키자
                backFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //fragment 변경하기
    public void replaceFragment(Fragment frg) {
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_layout, frg).commit();
    }

    // fragment 뒤로가기
    public void backFragment() {
        if (frag_stack_back.size() == 1)
            actionBar.setDisplayHomeAsUpEnabled(false);

        if (!frag_stack_back.empty()) {
            replaceFragment(frag_stack_back.peek().first);
            toolbar_title.setText(frag_stack_back.peek().second);
            toolbarMain_title = frag_stack_back.peek().second;
            frag_stack_back.pop();
        } else {
            Toast.makeText(this, "뒤로가기 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        /** 스택이 비었으면 엑티비티 종료
         * 비어있지 않았으면 스택 뒤로가기
         * 하단 버튼을 클릭 하면 스택 초기화
         * -> 하단 버튼 플레그먼트를 스택에 푸쉬.
         * 스택이 계속 없어지지 않으니까 스택 사이즈가 1이면 끝
         */
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        if (frag_stack_back.empty()) {
            // 2초안에 누르면 앱 끄기
            if (0 <= gapTime && 2000 >= gapTime) {
                super.onBackPressed();
            } else {
                backBtnTime = curTime;
                Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 사이즈가 더 크면 홈화면 이외에 다른 프레그먼트가 들어가있으니
            // replacefragment 함수 호출
            backFragment();
        }
    }

    @Override
    public void onClickStore(String name, Fragment fragment) {
        Fragment currentFrag = manager.findFragmentById(R.id.main_layout);
        String currentName = toolbarMain_title;

        // fragment <--> fragment 데이터 교환 방법
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout,
                Frag_search.newInstance(name));
        fragmentTransaction.commit();

        // 뒤로가기 스택에 플레그먼트, 제목 저장
        frag_stack_back.push(new Pair<Fragment, String>(currentFrag, currentName));
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.search_title);
        toolbarMain_title = toolbar_title.getText().toString();
    }
}

