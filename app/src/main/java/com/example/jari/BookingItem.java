package com.example.jari;


public class BookingItem {
    private String titleStr;
    private String addressStr;
    private String reservationStr;
    private int iconId;

    public String getTitleStr() {
        return titleStr;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public String getReservationStr() {
        return reservationStr;
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

    public void setReservationStr(String reservationStr) {
        this.reservationStr = reservationStr;
    }

    @Override
    public String toString() {
        return "RecyclerViewItem{" +
                "titleStr='" + titleStr + '\'' +
                ", addressStr='" + addressStr + '\'' +
                ", reservationStr='" + reservationStr + '\'' +
                ", iconId=" + iconId +
                '}';
    }
}
