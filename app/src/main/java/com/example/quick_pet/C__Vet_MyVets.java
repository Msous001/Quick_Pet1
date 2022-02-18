package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Vet_MyVets {
    List<C__Vet> myVetsList;

    public C__Vet_MyVets(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }
    public C__Vet_MyVets(){
        this.myVetsList = new ArrayList<>();
        C__Vet V0 = new C__Vet("Pet Care", "Jan 2021",  "Croydon",5);
        myVetsList.add(V0);
    }

    public List<C__Vet> getMyVetsList() {
        return myVetsList;
    }
    public void setMyVetsList(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }
}
