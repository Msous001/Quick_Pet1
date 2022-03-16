package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_menu extends AppCompatActivity {

    Toolbar toolbar;
    Button appointment, medical,  grooming, vaccination, fleas, deworming, surgery, medication,
            allergy, health ;
    CircleImageView circleImagepet1;
    List<C__CurrentPet> currentPetList;
    C__Pet_MyPets myPetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        circleImagepet1 = (CircleImageView) findViewById(R.id.imagePet);
        myPetList =  ((C__GlobalVariable) this.getApplication()).getMyPets();
        currentPetList = C__GlobalVariable.getCurrentPets();

        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);
        MenuItem backHome = toolbar.getMenu().findItem(R.id.homeT);
        backHome.setVisible(true);
        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.homeT:
                    Intent i = new Intent(Main_menu.this, List__Pet.class);
                    startActivity(i);
                    finish();
                    return true;
                case R.id.settings:
                    Toast.makeText(Main_menu.this, "test", Toast.LENGTH_SHORT).show();

                case R.id.photos:
                    Intent a = new Intent(Main_menu.this, List__Photos.class);
                    startActivity(a);
                    finish();
                    return true;
                case R.id.sounds:
                    Intent s = new Intent(Main_menu.this, List__Sounds.class);
                    startActivity(s);
                    finish();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
//                    Intent intent = new Intent(Main_menu.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                            | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    System.exit(0);
                    finish(); // to close activity
                    return true;
                case R.id.exit:
                    Toast.makeText(Main_menu.this, "Exit", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                    finish();
                    return true;
            }
            return false;
        });

        for(C__CurrentPet c : currentPetList){
            circleImagepet1.setImageURI(Uri.parse(c.getImageUrl()));

        }
        appointment = (Button) findViewById(R.id.appointment);
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), List__Appointment.class);
                startActivity(i);
            }
        });
        medical = (Button) findViewById(R.id.medical);
        medical.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Vet.class));
            finish();
        });

        grooming = (Button) findViewById(R.id.grooming);
        grooming.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Grooming.class));
            finish();
        });

        vaccination = (Button) findViewById(R.id.vaccination);
        vaccination.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Vaccine.class));
            finish();
        });

        fleas = (Button) findViewById(R.id.fleas);
        fleas.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Fleas.class));
            finish();
        });

        deworming = (Button) findViewById(R.id.deworming);
        deworming.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Deworming.class));
            finish();
        });

        surgery = (Button) findViewById(R.id.surgeryMenu);
        surgery.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Surgery.class));
            finish();
        });

        medication = (Button) findViewById(R.id.medication_Menu);
        medication.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Medication.class));
            finish();
        });

        allergy = (Button) findViewById(R.id.allergyMenu);
        allergy.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Allergy.class));
            finish();
        });

        health = (Button) findViewById(R.id.Health_Menu);
        health.setOnClickListener(view -> {
            startActivity(new Intent(Main_menu.this, List__Health.class));
            finish();
        });

    }

}