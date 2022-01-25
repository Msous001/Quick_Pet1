package com.example.quick_pet;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallery extends Application {

    List<Uri> uriList = new ArrayList<>();

    public PhotoGallery(){
        this.uriList = new ArrayList<>();
    }
    public List<Uri> getUriList() {
        return uriList;
    }

    public void setUriList(List<Uri> uriList) {
        this.uriList = uriList;
    }




}
