package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Grooming_MyGrooming {
    List<C__Grooming> myGroomingList;

    public C__Grooming_MyGrooming(List<C__Grooming> myGroomingList) {
        this.myGroomingList = myGroomingList;
    }
    public C__Grooming_MyGrooming(){
        this.myGroomingList = new ArrayList<>();

    }

    public List<C__Grooming> getMyGroomingList() {
        return myGroomingList;
    }

    public void setMyGroomingList(List<C__Grooming> myGroomingList) {
        this.myGroomingList = myGroomingList;
    }
}
