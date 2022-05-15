package com.example.quick_pet;

import java.util.List;

public class C__Vet_MyVets {

    List<C__Vet> myVetsList;

    public C__Vet_MyVets(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }

    public C__Vet_MyVets() {
        // this.myVetsList = new ArrayList<>();
    }

    public List<C__Vet> getMyVetsList() {
        return myVetsList;
    }

    public void setMyVetsList(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }
}
