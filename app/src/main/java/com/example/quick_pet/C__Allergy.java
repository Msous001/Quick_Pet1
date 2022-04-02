package com.example.quick_pet;

public class C__Allergy implements Comparable<C__Allergy>{
    private String id;
    private String Name;
    private String date;
    private String symptomsName;
    private String medicationName;

    public C__Allergy(String id, String name, String date, String symptomsName, String medicationName) {
        this.id = id;
        this.Name = name;
        this.date = date;
        this.symptomsName = symptomsName;
        this.medicationName = medicationName;
    }
    public C__Allergy(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSymptomsName() {
        return symptomsName;
    }

    public void setSymptomsName(String symptomsName) {
        this.symptomsName = symptomsName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @Override
    public String toString() {
        return "C__Allergy{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", date='" + date + '\'' +
                ", symptomsName='" + symptomsName + '\'' +
                ", medicationName='" + medicationName + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Allergy a) {
        return this.date.compareTo(a.date);
    }
}
