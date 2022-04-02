package com.example.quick_pet;

public class C__Deworming implements Comparable<C__Deworming>{
    private String dates;
    private String id;

    public C__Deworming(String id, String dates) {
        this.id = id;
        this.dates = dates;}
    public C__Deworming(){

    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "C__Deworming{" +
                "dates='" + dates + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Deworming c) {
        return this.dates.compareTo(c.dates);
    }
}
