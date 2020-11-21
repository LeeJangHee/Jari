package com.example.jari.home;


public class HomeMenuItem {
    private String titleStr;
    private String addressStr;
    private String phoneStr;
    private String iconStr;
    private String menuStr;

    public String getIconStr() {
        return iconStr;
    }

    public void setIconStr(String iconStr) {
        this.iconStr = iconStr;
    }

    public String getMenuStr() {
        return menuStr;
    }

    public void setMenuStr(String menuStr) {
        this.menuStr = menuStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public void setPhoneStr(String reservationStr) {
        this.phoneStr = reservationStr;
    }

}
