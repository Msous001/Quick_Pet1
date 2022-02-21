package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Deworming_MyDeworming {
    List<C__Deworming> myDewormingList;

    public C__Deworming_MyDeworming(List<C__Deworming> myDewormingList) {
        this.myDewormingList = myDewormingList;
    }
    public C__Deworming_MyDeworming(){
        this.myDewormingList = new ArrayList<>();
        C__Deworming D0 = new C__Deworming("10/02/2020");
        myDewormingList.add(D0);
    }

    public List<C__Deworming> getMyDewormingList() {
        return myDewormingList;
    }

    public void setMyDewormingList(List<C__Deworming> myDewormingList) {
        this.myDewormingList = myDewormingList;
    }

}
