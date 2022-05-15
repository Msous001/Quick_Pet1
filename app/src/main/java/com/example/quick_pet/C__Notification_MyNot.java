package com.example.quick_pet;

import java.util.List;

public class C__Notification_MyNot {
    List<C__Notifications> myNotList;

    public C__Notification_MyNot(List<C__Notifications> myNotList) {
        this.myNotList = myNotList;
    }

    public C__Notification_MyNot() {
    }

    public List<C__Notifications> getMyNotList() {
        return myNotList;
    }

    public void setMyNotList(List<C__Notifications> myNotList) {
        this.myNotList = myNotList;
    }
}
