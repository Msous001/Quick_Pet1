package com.example.quick_pet;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C__GlobalVariable extends Application {

    List<Uri> uriList = new ArrayList<Uri>();
    private C__Vet_MyVets myVets = new C__Vet_MyVets();
    private C__Allergy_MyAllergies myAllergies = new C__Allergy_MyAllergies();
    private C__Health_MyHealth myHealth = new C__Health_MyHealth();
    private C__Grooming_MyGrooming myGrooming = new C__Grooming_MyGrooming();
    private C__Vaccine_MyVaccine myVaccine = new C__Vaccine_MyVaccine();
    private C__Fleas_MyFleas myFleas = new C__Fleas_MyFleas();
    private C__Deworming_MyDeworming myDeworming = new C__Deworming_MyDeworming();
    private C__Surgery_MySurgeries mySurgeries = new C__Surgery_MySurgeries();
    private C__Medication_MyMedication myMedication = new C__Medication_MyMedication();
    private C__Photos_MyPhotos myPhotos = new C__Photos_MyPhotos();

    private static List<C__Appointment> appointmentsList = new ArrayList<C__Appointment>();
    private static int nextId =1;

    public C__GlobalVariable(){
        fillAppointement();
        fillpet();
    }

    private static List<C__Pet> petList = new ArrayList<C__Pet>();
    private void fillpet() {
        C__Pet p0 = new C__Pet("Max","Dog","Male","Pomeranian",
                "15/06/2020","Gold","Intact",String.valueOf(R.drawable.cat_face_circle));
        petList.addAll(Arrays.asList(new C__Pet[]{p0}));
    }

    private void fillAppointement(){
        C__Appointment Ap0 = new C__Appointment(0,"Veterinarian","Pet's Place",
                "30 May \n 2020","12:03","Croydon", "29/05");
        appointmentsList.add(Ap0);
    }

    public static int getNextId() {
        return nextId;
    }
    public static void setNextId(int nextId) {
        C__GlobalVariable.nextId = nextId;
    }

    public static List<C__Appointment> getAppointmentsList() {
        return appointmentsList;
    }
    public static void setAppointmentsList(List<C__Appointment> appointmentsList) {
        C__GlobalVariable.appointmentsList = appointmentsList;
    }

    public List<Uri> getUriList() {
        return uriList;
    }
    public void setUriList(List<Uri> uriList) {
        this.uriList = uriList;
    }

    public static List<C__Pet> getPetList() {
        return petList;
    }
    public static void setPetList(List<C__Pet> petList) {
        C__GlobalVariable.petList = petList;
    }

    public C__Vet_MyVets getMyVets() {return myVets;}
    public void setMyVets(C__Vet_MyVets myVets) {this.myVets = myVets;}

    public C__Allergy_MyAllergies getMyAllergies() {return myAllergies;}
    public void setMyAllergies(C__Allergy_MyAllergies myAllergies) {this.myAllergies = myAllergies;}

    public C__Health_MyHealth getMyHealth() {return myHealth;}
    public void setMyHealth(C__Health_MyHealth myHealth) {this.myHealth = myHealth;}

    public C__Grooming_MyGrooming getMyGrooming() {return myGrooming;}
    public void setMyGrooming(C__Grooming_MyGrooming myGrooming) {this.myGrooming = myGrooming;}

    public C__Vaccine_MyVaccine getMyVaccine() {return myVaccine;}
    public void setMyVaccine(C__Vaccine_MyVaccine myVaccine) {this.myVaccine = myVaccine;}

    public C__Fleas_MyFleas getMyFleas() {return myFleas;}
    public void setMyFleas(C__Fleas_MyFleas myFleas) {this.myFleas = myFleas;}

    public C__Deworming_MyDeworming getMyDeworming() {return myDeworming;}
    public void setMyDeworming(C__Deworming_MyDeworming myDeworming) {this.myDeworming = myDeworming;}

    public C__Surgery_MySurgeries getMySurgeries() {return mySurgeries;}
    public void setMySurgeries(C__Surgery_MySurgeries mySurgeries) {this.mySurgeries = mySurgeries;}

    public C__Medication_MyMedication getMyMedication() {return myMedication;}
    public void setMyMedication(C__Medication_MyMedication myMedication) {this.myMedication = myMedication;}

    public C__Photos_MyPhotos getMyPhotos() {return myPhotos;}
    public void setMyPhotos(C__Photos_MyPhotos myPhotos) {this.myPhotos = myPhotos;}
}
