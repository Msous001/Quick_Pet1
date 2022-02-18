package com.example.quick_pet;

public class C__Pet {
    private String name;
    private String type;
    private String gender;
    private String breed;
    private String bod;
    private String colour;
    private String intact;
    private String imageUrl;

    public C__Pet(String name, String type, String gender, String breed, String bod, String colour, String intact, String imageUrl) {
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.breed = breed;
        this.bod = bod;
        this.colour = colour;
        this.intact = intact;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getIntact() {
        return intact;
    }

    public void setIntact(String intact) {
        this.intact = intact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "pet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", breed='" + breed + '\'' +
                ", bod='" + bod + '\'' +
                ", colour='" + colour + '\'' +
                ", intact='" + intact + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
