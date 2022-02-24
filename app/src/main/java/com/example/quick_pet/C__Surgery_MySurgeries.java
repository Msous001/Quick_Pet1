package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Surgery_MySurgeries {
    List<C__Surgery> mySurgeryList;

    public C__Surgery_MySurgeries(List<C__Surgery> mySurgeryList) {
        this.mySurgeryList = mySurgeryList;
    }
    public C__Surgery_MySurgeries(){
        this.mySurgeryList = new ArrayList<>();
        C__Surgery S0 = new C__Surgery("Fracture repair", "May 2020","", "", "Clean every 2 hours");
        mySurgeryList.add(S0);
    }
    public List<C__Surgery> getMySurgeryList() {return mySurgeryList;}
    public void setMySurgeryList(List<C__Surgery> mySurgeryList) {
        this.mySurgeryList = mySurgeryList;
    }

}
