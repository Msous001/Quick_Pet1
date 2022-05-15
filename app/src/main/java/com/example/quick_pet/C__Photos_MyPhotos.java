package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Photos_MyPhotos {
    List<C__Photos> myPhotoList;

    public C__Photos_MyPhotos(List<C__Photos> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }

    public C__Photos_MyPhotos() {
        this.myPhotoList = new ArrayList<>();
    }

    public List<C__Photos> getMyPhotoList() {
        return myPhotoList;
    }

    public void setMyPhotoList(List<C__Photos> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }
}

