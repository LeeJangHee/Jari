package com.example.jari.logindb;

import com.example.jari.retrofit2.ServerConnect;

public class RegisterRequest {
    private String baseURL = "http://wkdgml96.ipitme.org:8080/";
    private LoginService loginService;

    public RegisterRequest(String id, String password, String name, String phone) {

        loginService = ServerConnect.getClient().create(LoginService.class);

    }
}
