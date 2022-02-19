package com.example.quick_pet;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C__PhotoGallery extends Application {

    List<Uri> uriList = new ArrayList<Uri>();
    private C__Vet_MyVets myVets = new C__Vet_MyVets();
    private C__Allergy_MyAllergies myAllergies = new C__Allergy_MyAllergies();
    private C__Health_MyHealth myHealth = new C__Health_MyHealth();
    private C__Grooming_MyGrooming myGrooming = new C__Grooming_MyGrooming();
    private C__Vaccine_MyVaccine myVaccine = new C__Vaccine_MyVaccine();

    private static List<C__Appointment> appointmentsList = new ArrayList<C__Appointment>();
    private static int nextId =1;

    public C__PhotoGallery(){
        fillAppointement();
        fillpet();
    }

    private static List<C__Pet> petList = new ArrayList<C__Pet>();
    private void fillpet() {
        C__Pet p0 = new C__Pet("Max","Dog","Male","Pomeranian",
                "15/06/2020","Gold","Intact",
                "https://www.google.com/search?q=dog+icon&rlz=1C1CHBF_en-GBGB989GB989&tbm=isch&source=iu&ictx=1&vet=1&fir=bqCEl38HjaoZmM%252C6w0T_ZyRfi8bTM%252C_%253BpMl24mqnarDTPM%252ClK1Dh_Za5KjE2M%252C_%253BqxHTsiX84lC-DM%252C-GH-4OYsSbKoHM%252C_%253BA3tYy0CbPw8lJM%252C39jfOUEg-QS5xM%252C_%253BGT45qp9cUkjsgM%252C5TkwdKrpYqccAM%252C_%253BBndchPOAEe65nM%252C9mn0uJ4Y5SF46M%252C_%253BSzV6Q0HnOU5I3M%252CT3ctoYtg3fQ1QM%252C_%253BFij1W3hAiLkSiM%252CQXOKLV8OhCURdM%252C_%253BU4xe-tNv02IMeM%252CboHk0N1PbXc7aM%252C_%253B73kGw2YnrRAjjM%252CIB1GCa2ZKa3jIM%252C_%253BerchFKJzHDrk6M%252C6fxJmciu9pmfUM%252C_%253BjOcr79W-dNgHbM%252CcFsi3ijjVPUvkM%252C_%253BGjfrKBJCh5VlyM%252CCAZg3e3cJwejAM%252C_%253B84CCwcF_axHaLM%252CrkxTGzrJQbWHKM%252C_%253Bc1sZGId8qO1E0M%252CxpNiu0P7PeDbMM%252C_%253BLNTdQ0LazWx_IM%252C2Hy0TrQY8PEp2M%252C_&usg=AI4_-kTLgnmDFViCKuirrgMgPwWmOVFHoQ&sa=X&ved=2ahUKEwjUv8C93t71AhU0kFwKHXpFBcoQ9QF6BAgREAE#imgrc=erchFKJzHDrk6M");
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
        C__PhotoGallery.nextId = nextId;
    }

    public static List<C__Appointment> getAppointmentsList() {
        return appointmentsList;
    }
    public static void setAppointmentsList(List<C__Appointment> appointmentsList) {
        C__PhotoGallery.appointmentsList = appointmentsList;
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
        C__PhotoGallery.petList = petList;
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
}
