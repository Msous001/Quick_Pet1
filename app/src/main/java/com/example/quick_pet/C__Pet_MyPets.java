package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Pet_MyPets {
    List<C__Pet> myPetList;

    public C__Pet_MyPets(List<C__Pet> myPetList) { this.myPetList = myPetList;}
    public C__Pet_MyPets(){ this.myPetList = new ArrayList<>();}
    public List<C__Pet> getMyPetList() {return myPetList;}
    public void setMyPetList(List<C__Pet> myPetList) {this.myPetList = myPetList;}
}
