package com.example.quick_pet;

import java.util.List;

public class C__Appointment_MyApp {

    List<C__Appointment> myAppList;

    public C__Appointment_MyApp(List<C__Appointment> myAppList) {
        this.myAppList = myAppList;
    }
    public C__Appointment_MyApp(){}

    public List<C__Appointment> getMyAppList() {
        return myAppList;
    }

    public void setMyAppList(List<C__Appointment> myAppList) {
        this.myAppList = myAppList;
    }
}
