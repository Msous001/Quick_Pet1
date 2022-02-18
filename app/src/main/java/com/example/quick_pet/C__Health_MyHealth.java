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
        C__Health H0 = new C__Health("Obesity", "Strin on joints and back", "Join disease", "N/A");
        myHealthList.add(H0);
    }

    public List<C__Health> getMyHealthList() {return myHealthList;}
    public void setMyHealthList(List<C__Health> myHealthList) {
        this.myHealthList = myHealthList;
    }
}
