package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Optional;

import de.hdodenhof.circleimageview.CircleImageView;


public class FirstActivity extends AppCompatActivity {

    //Toolbar toolbar = (Toolbar) findViewById(R.id.materialToolbar);

    CircleImageView circleImageView;
    CircleImageView circleImagepet1;
    CircleImageView circleImagepet2;
    CircleImageView circleImagepet3;
    CircleImageView circleImagepet4;

    List<Uri> uriList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        circleImageView = findViewById(R.id.circleImageView);
        circleImagepet1 = findViewById(R.id.pet1);
        circleImagepet2 = findViewById(R.id.pet2);
        circleImagepet3 = findViewById(R.id.pet3);
        circleImagepet4 = findViewById(R.id.pet4);

        uriList = ((PhotoGallery) this.getApplication()).getUriList();
        if (uriList.isEmpty()) {

        } else {
            try {
                int plus = uriList.size();
                switch (plus) {
                    case 1:
                        circleImagepet1.setImageURI(uriList.get(0));
                    case 2:
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setImageURI(uriList.get(1));
                    case 3:
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setImageURI(uriList.get(1));
                        circleImagepet3.setImageURI(uriList.get(2));
                    case 4:
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setImageURI(uriList.get(1));
                        circleImagepet3.setImageURI(uriList.get(2));
                        circleImagepet4.setImageURI(uriList.get(3));

                }
            }
            catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

            }





        }


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, Add_pet.class));
            }
        });
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:

                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FirstActivity.this, MainActivity.class));
                finish();
                return true;
            case R.id.exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}