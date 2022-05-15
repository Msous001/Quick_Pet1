package com.example.quick_pet;

public class C__Vet implements Comparable<C__Vet> {
    private String id;
    private String Name;
    private String date;
    private String direction;
    private double weight;

    public C__Vet(String id, String name, String date, String direction, double weight) {
        this.id = id;
        this.Name = name;
        this.date = date;
        this.direction = direction;
        this.weight = weight;
    }

    public C__Vet() {
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "C__Vet{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", date='" + date + '\'' +
                ", direction='" + direction + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(C__Vet o) {
        return this.date.compareTo(o.date);
    }
}
