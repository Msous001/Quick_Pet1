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
        C__Grooming G0 = new C__Grooming("Pet's Place","15 Nov", "3:00", "Croydon");
        myGroomingList.add(G0);
    }

    public List<C__Grooming> getMyGroomingList() {
        return myGroomingList;
    }

    public void setMyGroomingList(List<C__Grooming> myGroomingList) {
        this.myGroomingList = myGroomingList;
    }
}
