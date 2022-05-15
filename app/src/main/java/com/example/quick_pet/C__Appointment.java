package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Appointment {
    //private int id;
    private String ida;
    private String type;
    private String Name;
    private String date;
    private String time;
    private String direction;

    public C__Appointment(String ida, String type, String name, String date, String time, String direction) {

        //this.id = id;
        this.ida = ida;
        this.type = type;
        this.Name = name;
        this.date = date;
        this.time = time;
        this.direction = direction;
    }

    public C__Appointment() {
    }

    public String getIda() {
        return ida;
    }

    public void setIda(String ida) {
        this.ida = ida;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    @Override
    public String toString() {
        return "C__Appointment{" +
                ", ida='" + ida + '\'' +
                ", type='" + type + '\'' +
                ", Name='" + Name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
