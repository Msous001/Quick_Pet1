package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Fleas_MyFleas {
    List<C__Fleas> myFleasList;

    public C__Fleas_MyFleas(List<C__Fleas> myFleasList) {
        this.myFleasList = myFleasList;
    }

    public C__Fleas_MyFleas(){
        this.myFleasList = new ArrayList<>();

    }

    public List<C__Fleas> getMyFleasList() {
        return myFleasList;
    }
    public void setMyFleasList(List<C__Fleas> myFleasList) {
        this.myFleasList = myFleasList;
    }
}
