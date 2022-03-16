package com.example.quick_pet;

public class C__User {


    public String name;

    public C__User(String name) {

        String separator = "@";
        int sep = name.lastIndexOf(separator);
        this.name = name.substring(0,sep);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
