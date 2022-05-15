package com.example.quick_pet;

public class C__Medication implements Comparable<C__Medication> {
    String id;
    String name;
    String date;
    String reason;


    public C__Medication() {
    }

    public C__Medication(String id, String name, String date, String reason) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "C__Medication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Medication c) {
        return this.date.compareTo(c.date);
    }
}
