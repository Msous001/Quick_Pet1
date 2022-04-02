package com.example.quick_pet;

public class C__Vaccine implements Comparable<C__Vaccine> {
    private String id;
    private String Vac_name;
    private String Vac_date;
    private String Vac_vetName;


    public C__Vaccine(){}

    public C__Vaccine(String id, String vac_name, String vac_date, String vac_vetName) {
        this.id = id;
        Vac_name = vac_name;
        Vac_date = vac_date;
        Vac_vetName = vac_vetName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVac_name() {
        return Vac_name;
    }

    public void setVac_name(String vac_name) {
        Vac_name = vac_name;
    }

    public String getVac_date() {
        return Vac_date;
    }

    public void setVac_date(String vac_date) {
        Vac_date = vac_date;
    }

    public String getVac_vetName() {
        return Vac_vetName;
    }

    public void setVac_vetName(String vac_vetName) {
        Vac_vetName = vac_vetName;
    }

    @Override
    public String toString() {
        return "C__Vaccine{" +
                "id='" + id + '\'' +
                ", Vac_name='" + Vac_name + '\'' +
                ", Vac_date='" + Vac_date + '\'' +
                ", Vac_vetName='" + Vac_vetName + '\'' +
                '}';
    }

    @Override
    public int compareTo(C__Vaccine c) {
        return this.Vac_date.compareTo(c.Vac_date);
    }
}
