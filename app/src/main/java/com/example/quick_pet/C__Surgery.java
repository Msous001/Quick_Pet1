package com.example.quick_pet;

public class C__Surgery implements Comparable<C__Surgery> {
    private String id;
    private String name;
    private String date;
    private String addmed1;
    private String addmed2;
    private String addnote;


    public C__Surgery() {
    }

    public C__Surgery(String id, String name, String date, String addmed1, String addmed2, String addnote) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.addmed1 = addmed1;
        this.addmed2 = addmed2;
        this.addnote = addnote;
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

    public String getAddmed1() {
        return addmed1;
    }

    public void setAddmed1(String addmed1) {
        this.addmed1 = addmed1;
    }

    public String getAddmed2() {
        return addmed2;
    }

    public void setAddmed2(String addmed2) {
        this.addmed2 = addmed2;
    }

    public String getAddnote() {
        return addnote;
    }

    public void setAddnote(String addnote) {
        this.addnote = addnote;
    }

    @Override
    public String toString() {
        return "C__Surgery{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", addmed1='" + addmed1 + '\'' +
                ", addmed2='" + addmed2 + '\'' +
                ", addnote='" + addnote + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Surgery c) {
        return this.date.compareTo(c.date);
    }
}
