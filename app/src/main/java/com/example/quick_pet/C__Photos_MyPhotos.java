package com.example.quick_pet;


import java.util.ArrayList;
import java.util.List;

public class C__Photos_MyPhotos {
    List<C__Photos> myPhotoList;

    public C__Photos_MyPhotos(List<C__Photos> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }

    public C__Photos_MyPhotos(){

        this.myPhotoList = new ArrayList<>();
//        C__Photos p = new C__Photos(String.valueOf(R.drawable.cat_face_circle));
//        C__Photos p0 = new C__Photos(String.valueOf(R.drawable.dogface_circle));
//        myPhotoList.add(p);
//        myPhotoList.add(p0);



    }

    public List<C__Photos> getMyPhotoList() {return myPhotoList;}
    public void setMyPhotoList(List<C__Photos> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }
}

