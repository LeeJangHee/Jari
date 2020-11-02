package com.example.jari.booking.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationList {
    @SerializedName("reservationCheck")
    @Expose
    private List<Reservation> reservationCheck = null;

    public List<Reservation> getReservationCheck() {
        return reservationCheck;
    }

    public void setReservationCheck(List<Reservation> reservationCheck) {
        this.reservationCheck = reservationCheck;
    }
}
