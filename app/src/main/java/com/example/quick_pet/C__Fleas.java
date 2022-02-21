package com.example.quick_pet;

public class C__Fleas {
    private String dates;

    public C__Fleas(String dates) {this.dates = dates;}
    public String getDates() {return dates;}
    public void setDates(String dates) {this.dates = dates;}

    @Override
    public String toString() {
        return "C__Fleas{" +
                "dates='" + dates + '\'' +
                '}';
    }
}
