package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


//import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class List__Pet extends AppCompatActivity {

    Toolbar toolbar;
    Button btn_add;
    GridView myPetGrid;
    C__Pet_MyPets myPets;
    C__Pet_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pet);

        btn_add = (Button) findViewById(R.id.btn_list_Pet);
        myPetGrid = (GridView) findViewById(R.id.gridViewPet);
        myPets = ((C__GlobalVariable) this.getApplication()).getMyPets();
        adapter = new C__Pet_Adapter(List__Pet.this, myPets);
        myPetGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null){
            String P_image = incomingMessages.getString("p_image");
            int positionEdited = incomingMessages.getInt("edit");

        }
        myPetGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getApplicationContext(), Main_menu.class);
                startActivity(i);

            }
        });
        btn_add.setOnClickListener(view -> startActivity(new Intent(List__Pet.this, Add_pet.class)));


        // -- toolbar code on top right corner
        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeT:
                    Intent i = new Intent(List__Pet.this, List__Pet.class);
                    startActivity(i);
                    finish();
                    return true;
                case R.id.settings:
                    Toast.makeText(List__Pet.this, "test", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.photos:
                    Intent a = new Intent(List__Pet.this, List__Photos.class);
                    startActivity(a);
                    finish();
                case R.id.sounds:
                    Intent s = new Intent(List__Pet.this, List__Sounds.class);
                    startActivity(s);
                    finish();
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(List__Pet.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // to close activity

                    return true;
                case R.id.exit:
                    Toast.makeText(List__Pet.this, "Exit", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                    return true;
            }
            return false;
        });
    }


}
