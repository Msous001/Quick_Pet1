package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Health_MyHealth {
    List<C__Health> myHealthList;

    public C__Health_MyHealth(List<C__Health> myHealthList) {
        this.myHealthList = myHealthList;
    }
    public  C__Health_MyHealth(){
        this.myHealthList = new ArrayList<>();

    }

    public List<C__Health> getMyHealthList() {return myHealthList;}
    public void setMyHealthList(List<C__Health> myHealthList) {
        this.myHealthList = myHealthList;
    }
}
