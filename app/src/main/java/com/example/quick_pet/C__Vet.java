package com.example.quick_pet;

public class C__Vet implements Comparable<C__Vet>{
    private String Name;
    private String date;
    private String direction;
    private int weight;

    public C__Vet(String name, String date, String direction, int weight) {
        Name = name;
        this.date = date;
        this.direction = direction;
        this.weight = weight;
    }


    public String getName() {return Name;}
    public void setName(String name) {Name = name;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getDirection() {return direction;}
    public void setDirection(String direction) {this.direction = direction;}

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "VetVisit_Class{" +
                ", Name='" + Name + '\'' +
                ", date='" + date + '\'' +
                ", direction='" + direction + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Vet o) {
        return this.date.compareTo(o.date);
    }
}
