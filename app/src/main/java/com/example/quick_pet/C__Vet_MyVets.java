package com.example.quick_pet;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class C__Vet_MyVets {

    List<C__Vet> myVetsList;

    public C__Vet_MyVets(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }
    public C__Vet_MyVets(){
       // this.myVetsList = new ArrayList<>();

    }

    public List<C__Vet> getMyVetsList() {
        return myVetsList;
    }
    public void setMyVetsList(List<C__Vet> myVetsList) {
        this.myVetsList = myVetsList;
    }
}
