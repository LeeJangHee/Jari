package com.example.jari.home;


public class HomeMenuItem {
    private String titleStr;
    private String addressStr;
    private String phoneStr;
    private int iconId;
    private int menuId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
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

    @Override
    public String toString() {
        return "RecyclerViewItem{" +
                "titleStr='" + titleStr + '\'' +
                ", addressStr='" + addressStr + '\'' +
                ", reservationStr='" + phoneStr + '\'' +
                ", iconId=" + iconId +
                '}';
    }
}
