package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Sounds_MySounds {
    List<C__Sounds> mySoundsList;

    public C__Sounds_MySounds(List<C__Sounds> mySoundsList) {
        this.mySoundsList = mySoundsList;
    }
    public C__Sounds_MySounds(){
        this.mySoundsList = new ArrayList<>();
        C__Sounds f1 = new C__Sounds(R.raw.toy1, "Toy 1", false);
        mySoundsList.add(f1);
        C__Sounds f2 = new C__Sounds(R.raw.toy2, "Toy 2", false);
        mySoundsList.add(f2);
        C__Sounds f3 = new C__Sounds(R.raw.toy3, "Toy 3", false);
        mySoundsList.add(f3);
        C__Sounds f4 = new C__Sounds(R.raw.toy_4, "Toy 4", false);
        mySoundsList.add(f4);
        C__Sounds f5 = new C__Sounds(R.raw.toy_5, "Toy 5", false);
        mySoundsList.add(f5);
        C__Sounds f6 = new C__Sounds(R.raw.toy6, "Toy 6", false);
        mySoundsList.add(f6);
        C__Sounds f7 = new C__Sounds(R.raw.toy7, "Toy 7", false);
        mySoundsList.add(f7);
    }

    public List<C__Sounds> getMySoundsList() {
        return mySoundsList;
    }

    public void setMySoundsList(List<C__Sounds> mySoundsList) {
        this.mySoundsList = mySoundsList;
    }
}
