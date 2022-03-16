package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class List__Vet extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listVet;
    C__VetAdapter adapter;
    C__Vet_MyVets myVets;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vet);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTL);
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Vet.this, Main_menu.class));
            finish();
        });

        btn_add = (Button) findViewById(R.id.btn_list_vet);
        lv_listVet = (ListView) findViewById(R.id.listView_Vet);

        currentPetList = currentPetList = C__GlobalVariable.getCurrentPets();
        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }
        myVets = ((C__GlobalVariable) this.getApplication()).getMyVets();
        adapter = new C__VetAdapter(List__Vet.this, myVets);
        lv_listVet.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String direction = incomingMessages.getString("direction");
            String weight = incomingMessages.getString("weight");
            int positionEdited = incomingMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();
            C__Vet c = new C__Vet(name, date, direction, Float.parseFloat(weight));
            if (positionEdited > -1) {
                myVets.getMyVetsList().remove(positionEdited);
                //here i need to call the database to delete the item

            }
            myVets.getMyVetsList().add(c);
            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Veterinary Visits").push().setValue(c);

            Collections.sort(myVets.getMyVetsList());
            adapter.notifyDataSetChanged();
        }
        lv_listVet.setOnItemClickListener((adapterView, view, position, l) -> editVet(position));
        btn_add.setOnClickListener(view -> {
            startActivity(new Intent(List__Vet.this, Add_Vet.class));
            finish();
        });
    }

    private void editVet(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Vet.class);
        C__Vet v = myVets.getMyVetsList().get(position);
        i.putExtra("name", v.getName());
        i.putExtra("edit", position);
        i.putExtra("date", v.getDate());
        i.putExtra("direction", v.getDirection());
        i.putExtra("weight", v.getWeight());
        startActivity(i);
    }

}