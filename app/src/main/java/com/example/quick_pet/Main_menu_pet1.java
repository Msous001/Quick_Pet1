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

public class Main_menu_pet1 extends AppCompatActivity {

    Toolbar toolbar;
    Button appointment, medical, grooming, vaccination, fleas, deworming, sounds, photos;
    List<Uri> uriList;
    CircleImageView circleImagepet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_pet1);

        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);

        circleImagepet1 = (CircleImageView) findViewById(R.id.imagePet);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        Toast.makeText(Main_menu_pet1.this, "test", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(Main_menu_pet1.this, "bye", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;
                    case R.id.exit:
                        Toast.makeText(Main_menu_pet1.this, "Exit", Toast.LENGTH_SHORT).show();
                        System.exit(0);
                        return true;
                }
                return false;
            }
        });

        uriList = ((PhotoGallery) this.getApplication()).getUriList();

        if (uriList.isEmpty()) {

        } else {
            circleImagepet1.setImageURI(uriList.get(0));
        }

        appointment = (Button) findViewById(R.id.appointment);
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_menu_pet1.this, AppointmentList_Page_Pet1.class));

            }
        });

        medical = (Button) findViewById(R.id.medical);
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_menu_pet1.this, TableLayout.class));

            }
        });
        grooming = (Button) findViewById(R.id.grooming);
        grooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        vaccination = (Button) findViewById(R.id.vaccination);
        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        sounds = (Button) findViewById(R.id.sounds);
        sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        photos = (Button) findViewById(R.id.photos);
        photos.setOnClickListener(view -> {

        });
    }
}