package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__CurrentPet {
    String name;
    private String imageUrl;

    public C__CurrentPet(String name, String image) {

        this.name = name;
        this.imageUrl = image;
        List<C__CurrentPet> currentPet = new ArrayList<C__CurrentPet>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
