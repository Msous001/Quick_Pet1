package com.example.quick_pet;

public class C__Fleas implements Comparable<C__Fleas> {
    private String id;
    private String dates;

    public C__Fleas(String id, String dates) {
        this.id = id;
        this.dates = dates;
    }

    public C__Fleas() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "C__Fleas{" +
                "id='" + id + '\'' +
                ", dates='" + dates + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Fleas c) {
        return this.dates.compareTo(c.dates);
    }
}
