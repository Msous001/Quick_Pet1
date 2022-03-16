package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Vaccine_MyVaccine {
    List<C__Vaccine> myVaccineList;

    public C__Vaccine_MyVaccine(List<C__Vaccine> myVaccineList) {
        this.myVaccineList = myVaccineList;
    }
    public C__Vaccine_MyVaccine(){
        this.myVaccineList = new ArrayList<>();

    }

    public List<C__Vaccine> getMyVaccineList() {return myVaccineList;}
    public void setMyVaccineList(List<C__Vaccine> myVaccineList) {
        this.myVaccineList = myVaccineList;
    }
}
