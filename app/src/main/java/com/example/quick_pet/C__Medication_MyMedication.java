package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Medication_MyMedication {
    List<C__Medication> myMedicationList;

    public C__Medication_MyMedication(List<C__Medication> myMedicationList) {
        this.myMedicationList = myMedicationList;
    }
    public C__Medication_MyMedication(){
        this.myMedicationList = new ArrayList<>();
        C__Medication M0 = new C__Medication("RFF", "Jan 2020", "Injury");
        myMedicationList.add(M0);
    }


    public List<C__Medication> getMyMedicationList() {return myMedicationList;}
    public void setMyMedicationList(List<C__Medication> myMedicationList) {
        this.myMedicationList = myMedicationList;
    }





}
