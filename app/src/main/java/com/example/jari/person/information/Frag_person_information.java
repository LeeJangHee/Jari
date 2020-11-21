package com.example.jari.person.information;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jari.MainActivity;
import com.example.jari.R;
import com.example.jari.logindb.LoginData;
import com.example.jari.logindb.LoginService;
import com.example.jari.retrofit2.ServerConnect;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_person_information extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;
    private MainActivity mainActivity;

    private EditText et_password_check;
    private String str_password_check;
    private Button btn_pwCheck;

    private LoginService loginService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle saveInstanceState) {
        view = (View) inflater.inflate(R.layout.frag_person_information, container, false);
        context = container.getContext();
        mainActivity = (MainActivity) context;

        et_password_check = (EditText) view.findViewById(R.id.person_info_password);
        btn_pwCheck = (Button) view.findViewById(R.id.person_info_pwCheck);

        btn_pwCheck.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_info_pwCheck:
                str_password_check = et_password_check.getText().toString();
                if (str_password_check.equals(mainActivity.password)) {
                    mainActivity.replaceFragment(new Frag_person_info_change());
                } else {
                    Toast.makeText(context, "비밀번호가 틀렸습니다.\n다시 입력 해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void getPasswordCheck() {
        loginService = ServerConnect.getClient().create(LoginService.class);
        loginService.getChangeValidate(mainActivity.id, mainActivity.password).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    LoginData loginData = response.body();
                    // 아이디 받아오기 성공
                    mainActivity.replaceFragment(new Frag_person_info_change());
                }
            }


            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }
}
