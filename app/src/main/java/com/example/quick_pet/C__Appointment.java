package com.example.quick_pet;

import java.util.ArrayList;
import java.util.List;

public class C__Appointment {
    private int id;
    private String ida;
    private String type;
    private String Name;
    private String date;
    private String time;
    private String direction;
    private String reminder;

    public C__Appointment(int id, String ida, String type, String name, String date, String time, String direction, String reminder) {
        this.ida = ida;
        this.id = id;
        this.type = type;
        this.Name = name;
        this.date = date;
        this.time = time;
        this.direction = direction;
        this.reminder = reminder;
        List<C__Appointment> appointmentList = new ArrayList<>();
    }

    public C__Appointment(){}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getReminder() {
        return reminder;
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "C__Appointment{" +
                "id=" + id +
                ", ida='" + ida + '\'' +
                ", type='" + type + '\'' +
                ", Name='" + Name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", direction='" + direction + '\'' +
                ", reminder='" + reminder + '\'' +
                '}';
    }
}
