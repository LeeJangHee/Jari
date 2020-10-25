package com.example.jari.retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    // json 객체 받아오기
    @SerializedName("storeKor")
    @Expose
    private List<Store> storeKor = null;
    @SerializedName("storeJp")
    @Expose
    private List<Store> storeJp = null;
    @SerializedName("storeCh")
    @Expose
    private List<Store> storeCh = null;
    @SerializedName("storeWf")
    @Expose
    private List<Store> storeWf = null;
    @SerializedName("storeCafe")
    @Expose
    private List<Store> storeCafe = null;
    @SerializedName("storeBeer")
    @Expose
    private List<Store> storeBeer = null;
    @SerializedName("storeBest")
    @Expose
    private List<Store> storeBest = null;
    @SerializedName("storeInfo")
    @Expose
    private List<StoreInfo> storeInfo = null;
    @SerializedName("storeFood")
    @Expose
    private List<Store> storeFood = null;

    public List<Store> getStoreFood() {
        return storeFood;
    }

    public void setStoreFood(List<Store> storeFood) {
        this.storeFood = storeFood;
    }

    public List<StoreInfo> getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(List<StoreInfo> storeInfo) {
        this.storeInfo = storeInfo;
    }

    public List<Store> getStoreBest() {
        return storeBest;
    }

    public void setStoreBest(List<Store> storeBest) {
        this.storeBest = storeBest;
    }

    public List<Store> getStoreKor() {
        return storeKor;
    }

    public void setStoreKor(List<Store> storeKor) {
        this.storeKor = storeKor;
    }

    public List<Store> getStoreJp() {
        return storeJp;
    }

    public void setStoreJp(List<Store> storeJp) {
        this.storeJp = storeJp;
    }

    public List<Store> getStoreCh() {
        return storeCh;
    }

    public void setStoreCh(List<Store> storeCh) {
        this.storeCh = storeCh;
    }

    public List<Store> getStoreWf() {
        return storeWf;
    }

    public void setStoreWf(List<Store> storeWf) {
        this.storeWf = storeWf;
    }

    public List<Store> getStoreCafe() {
        return storeCafe;
    }

    public void setStoreCafe(List<Store> storeCafe) {
        this.storeCafe = storeCafe;
    }

    public List<Store> getStoreBeer() {
        return storeBeer;
    }

    public void setStoreBeer(List<Store> storeBeer) {
        this.storeBeer = storeBeer;
    }
}
