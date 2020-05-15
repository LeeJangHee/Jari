package com.example.jari;


public class ListViewItem {
    private String titleStr;
    private String addressStr;
    private String reservationStr;

    public ListViewItem(String titleStr, String addressStr, String reservationStr) {
        this.titleStr = titleStr;
        this.addressStr = addressStr;
        this.reservationStr = reservationStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public String getReservationStr() {
        return reservationStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public void setReservationStr(String reservationStr) {
        this.reservationStr = reservationStr;
    }

    @Override
    public String toString() {
        return "ListViewItem{" +
                "titleStr='" + titleStr + '\'' +
                ", addressStr='" + addressStr + '\'' +
                ", reservationStr='" + reservationStr + '\'' +
                '}';
    }
}
