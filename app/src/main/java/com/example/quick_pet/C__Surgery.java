package com.example.quick_pet;

public class C__Surgery implements Comparable<C__Surgery>{
    private String name;
    private String date;
    private String medication;
    private String addmed1;
    private String addnote;

    public C__Surgery(String name, String date, String medication, String addmed1, String addnote) {
        this.name = name;
        this.date = date;
        this.medication = medication;
        this.addmed1 = addmed1;
        this.addnote = addnote;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getMedication() {return medication;}
    public void setMedication(String medication) {this.medication = medication;}

    public String getAddmed1() {return addmed1;}
    public void setAddmed1(String addmed1) {this.addmed1 = addmed1;}

    public String getAddnote() {return addnote;}
    public void setAddnote(String addnote) {this.addnote = addnote;}

    @Override
    public String toString() {
        return "C__Surgery{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", medication='" + medication + '\'' +
                ", addmed1='" + addmed1 + '\'' +
                ", addnote='" + addnote + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Surgery c) {
        return this.date.compareTo(c.date);
    }
}
