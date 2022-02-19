package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_menu extends AppCompatActivity {

    Toolbar toolbar;
    Button appointment, medical,  grooming, vaccination, fleas, deworming, surgery, medication, allergy, health ;
    List<Uri> uriList;
    CircleImageView circleImagepet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        circleImagepet1 = (CircleImageView) findViewById(R.id.imagePet);

        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);
        MenuItem backHome = toolbar.getMenu().findItem(R.id.homeT);
        backHome.setVisible(true);
        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.homeT:
                    Intent i = new Intent(Main_menu.this, FirstActivity.class);
                    startActivity(i);
                    return true;
                case R.id.settings:
                    Toast.makeText(Main_menu.this, "test", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Main_menu.this, "bye", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                case R.id.exit:
                    Toast.makeText(Main_menu.this, "Exit", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                    return true;
            }
            return false;

        });


        uriList = ((C__PhotoGallery) this.getApplication()).getUriList();

        if (uriList.isEmpty()) {

        } else {
            circleImagepet1.setImageURI(uriList.get(0));
        }

        appointment = (Button) findViewById(R.id.appointment);
        appointment.setOnClickListener(view -> startActivity(new Intent(Main_menu.this, List__Appointment.class)));

        medical = (Button) findViewById(R.id.medical);
        medical.setOnClickListener(view -> startActivity(new Intent(Main_menu.this, List__Vet.class)));

        grooming = (Button) findViewById(R.id.grooming);
        grooming.setOnClickListener(view -> startActivity(new Intent(Main_menu.this, List__Grooming.class)));

        vaccination = (Button) findViewById(R.id.vaccination);
        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_menu.this, List__Vaccine.class));

            }
        });
        fleas = (Button) findViewById(R.id.fleas);
        fleas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        deworming = (Button) findViewById(R.id.deworming);
        deworming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        surgery = (Button) findViewById(R.id.surgeryMenu);
        surgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        medication = (Button) findViewById(R.id.medication_Menu);
        medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        allergy = (Button) findViewById(R.id.allergyMenu);
        allergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_menu.this, List__Allergy.class));

            }
        });
        health = (Button) findViewById(R.id.Health_Menu);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_menu.this, List__Health.class));
            }
        });



    }

}