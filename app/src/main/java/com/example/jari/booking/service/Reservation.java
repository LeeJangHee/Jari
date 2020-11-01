package com.example.jari.booking.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reservation {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("storePhone")
    @Expose
    private String storePhone;
    @SerializedName("storeAddress")
    @Expose
    private String storeAddress;
    @SerializedName("storeProfile")
    @Expose
    private String storeProfile;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("num")
    @Expose
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreProfile() {
        return storeProfile;
    }

    public void setStoreProfile(String storeProfile) {
        this.storeProfile = storeProfile;
    }
}
