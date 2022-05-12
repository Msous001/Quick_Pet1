package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__CurrentPet_MyCurrentPet {
    List<C__CurrentPet> myCurrentPet;

    public C__CurrentPet_MyCurrentPet(List<C__CurrentPet> myCurrentPet) {
        this.myCurrentPet = myCurrentPet;
    }

    public C__CurrentPet_MyCurrentPet() {
        this.myCurrentPet = new ArrayList<>();
    }

    public List<C__CurrentPet> getMyCurrentPet() {
        return myCurrentPet;
    }

    public void setMyCurrentPet(List<C__CurrentPet> myCurrentPet) {
        this.myCurrentPet = myCurrentPet;
    }
}
