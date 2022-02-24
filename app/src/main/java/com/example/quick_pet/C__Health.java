package com.example.quick_pet;

public class C__Health {
    private String Name;
    private String effects;
    private String symptomsName;
    private String medicationName;

    public C__Health(String name, String effects, String symptomsName, String medicationName) {
        this. Name = name;
        this.effects = effects;
        this.symptomsName = symptomsName;
        this.medicationName = medicationName;
    }

    public String getName() {return Name;}
    public void setName(String name) {Name = name;}

    public String getEffects() {return effects;}
    public void setEffects(String effects) {this.effects = effects;}

    public String getSymptomsName() {return symptomsName;}
    public void setSymptomsName(String symptomsName) {this.symptomsName = symptomsName;}

    public String getMedicationName() {return medicationName;}
    public void setMedicationName(String medicationName) {this.medicationName = medicationName;}

    @Override
    public String toString() {
        return "C__Health{" +
                "Name='" + Name + '\'' +
                ", effects='" + effects + '\'' +
                ", symptomsName='" + symptomsName + '\'' +
                ", medicationName='" + medicationName + '\'' +
                '}';
    }


}
