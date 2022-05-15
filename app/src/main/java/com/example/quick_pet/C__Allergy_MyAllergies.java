package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Allergy_MyAllergies {
    List<C__Allergy> myAllergyList;

    public C__Allergy_MyAllergies(List<C__Allergy> myAllergyList) {
        this.myAllergyList = myAllergyList;
    }

    public C__Allergy_MyAllergies() {

    }

    public List<C__Allergy> getMyAllergyList() {
        return myAllergyList;
    }

    public void setMyAllergyList(List<C__Allergy> myAllergyList) {
        this.myAllergyList = myAllergyList;
    }
}
