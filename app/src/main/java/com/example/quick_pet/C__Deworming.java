package com.example.quick_pet;

public class C__Deworming implements Comparable<C__Deworming>{
    private String dates;

    public C__Deworming(String dates) {this.dates = dates;}
    public String getDates() {return dates;}
    public void setDates(String dates) {this.dates = dates;}

    @Override
    public String toString() {
        return "C__Deworming{" +
                "dates='" + dates + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Deworming c) {
        return this.dates.compareTo(c.dates);
    }
}
