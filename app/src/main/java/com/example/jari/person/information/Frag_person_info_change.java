package com.example.jari.person.information;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginService;
import com.example.jari.retrofit2.ServerConnect;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_person_info_change extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private View view;
    private Context context;
    private MainActivity mainActivity;

    private TextView tv_id;
    private EditText et_password;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_people;

    private Button btn_check_ok;
    private Button btn_check_cancel;

    private LoginService loginService;
    private String str_password = "";
    private String str_name = "";
    private String str_phone = "";
    private String str_people = "";

    private Map<String, Boolean> checkInfo = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person_info_change, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        tv_id = (TextView) view.findViewById(R.id.person_info_check_id);
        et_password = (EditText) view.findViewById(R.id.person_info_check_pw);
        et_name = (EditText) view.findViewById(R.id.person_info_check_name);
        et_phone = (EditText) view.findViewById(R.id.person_info_check_phone);
        et_people = (EditText) view.findViewById(R.id.person_info_check_people);

        btn_check_ok = (Button) view.findViewById(R.id.person_info_check_btn_ok);
        btn_check_cancel = (Button) view.findViewById(R.id.person_info_check_btn_cancel);

        checkInfo.put("password", false);
        checkInfo.put("name", false);
        checkInfo.put("phone", false);
        checkInfo.put("people", false);

        et_password.setOnFocusChangeListener(this);
        et_name.setOnFocusChangeListener(this);
        et_phone.setOnFocusChangeListener(this);
        et_people.setOnFocusChangeListener(this);

        btn_check_ok.setOnClickListener(this);
        btn_check_cancel.setOnClickListener(this);

        tv_id.setText(mainActivity.id);

        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.person_info_check_pw:
                str_password = et_password.getText().toString();
                if (!str_password.isEmpty()) getChangePassword();
                break;
            case R.id.person_info_check_name:
                str_name = et_name.getText().toString();
                if (!str_name.isEmpty()) getChangeName();
                break;
            case R.id.person_info_check_phone:
                str_phone = et_phone.getText().toString();
                if (!str_phone.isEmpty()) getChangePhone();
                break;
            case R.id.person_info_check_people:
                str_people = et_people.getText().toString();
                if (!str_people.isEmpty()) {
                    if (mainActivity.people.equals(str_people)) {
                        // 같음
                        Toast.makeText(context, "이전 인원과 같습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 다름
                        checkInfo.put("people", true);
                        mainActivity.people = str_people;
                    }
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_info_check_btn_ok:
                // 변경하기
                if (str_name.isEmpty() && str_people.isEmpty() && str_password.isEmpty() && str_phone.isEmpty()) {
                    Toast.makeText(context, "변경사항을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                if (checkInfo.get("password") || checkInfo.get("name") || checkInfo.get("phone") || checkInfo.get("people")) {
                    mainActivity.backFragment();
                    Toast.makeText(context, "변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.person_info_check_btn_cancel:
                // 취소
                mainActivity.backFragment();
                break;
        }

    }

    public void getChangePassword() {
        loginService = ServerConnect.getClient().create(LoginService.class);
        loginService.getChangePassword(mainActivity.id, str_password).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData.getSuccess().equals("true")) {
                        // 변경 가능
                        checkInfo.put("password", true);
                        mainActivity.password = str_password;
                    } else {
                        // 변경 불가능
                        Toast.makeText(context, "다른 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

    public void getChangeName() {
        loginService = ServerConnect.getClient().create(LoginService.class);
        loginService.getChangeName(mainActivity.id, str_name).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData.getSuccess().equals("true")) {
                        // 변경 가능
                        checkInfo.put("name", true);
                        mainActivity.name = str_name;
                    } else {
                        // 변경 불가능
                        Toast.makeText(context, "다른 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

    public void getChangePhone() {
        loginService = ServerConnect.getClient().create(LoginService.class);
        loginService.getChangePhone(mainActivity.id, str_phone).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    if (loginData.getSuccess().equals("true")) {
                        // 변경 가능
                        checkInfo.put("phone", true);
                        mainActivity.phone = str_phone;
                    } else {
                        // 변경 불가능
                        Toast.makeText(context, "다른 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

}
