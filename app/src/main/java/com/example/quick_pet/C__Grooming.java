package com.example.quick_pet;

public class C__Grooming implements Comparable<C__Grooming>{
    private String id;
    private String place;
    private String date;
    private String time;
    private String direction;

    public C__Grooming(String id, String place, String date, String time, String direction) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.time = time;
        this.direction = direction;
    }

    public C__Grooming(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
        return "C__Grooming{" +
                "id='" + id + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Grooming c) {
        return this.date.compareTo(c.date);
    }
}
