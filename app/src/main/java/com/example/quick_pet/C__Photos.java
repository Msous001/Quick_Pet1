package com.example.quick_pet;


public class C__Photos {
    private String picPicture;

    public C__Photos(String picPicture) {
        this.picPicture = picPicture;
    }

    public String getPicPicture() {
        return picPicture;
    }

    public void setPicPicture(String picPicture) {
        this.picPicture = picPicture;
    }

    @Override
    public String toString() {
        return "C__Photos{" +
                "picPicture='" + picPicture + '\'' +
                '}';
    }
}
