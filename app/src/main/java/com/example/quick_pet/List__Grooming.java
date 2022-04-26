package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class List__Grooming extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listGrooming;
    C__Grooming_MyGrooming myGrooming;
    C__GroomingAdapter adapter;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    private static String  pet_name;
    FirebaseFirestore db;
    private static String dbSalt;
    private static final String TAG = "List_Grooming";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grooming);
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myGrooming= ((C__GlobalVariable) this.getApplication()).getMyGrooming();
        myGrooming.myGroomingList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        back_arrow = ((ImageView) findViewById(R.id.back_arrowGr));
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Grooming.this, Main_menu.class));
            finish();
        });

        btn_add = ((Button) findViewById(R.id.btn_list_grooming));
        lv_listGrooming = ((ListView) findViewById(R.id.listView_Grooming));
        adapter = new C__GroomingAdapter(List__Grooming.this, myGrooming);
        lv_listGrooming.setAdapter(adapter);
        Collections.sort(myGrooming.myGroomingList);
        adapter.notifyDataSetChanged();
        //currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }
        lv_listGrooming.setOnItemClickListener((adapterView, view, position, l) -> editGrooming(position));
        EventChangeListener();

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null) {
            String G_place = incomingMessages.getString("place");
            String G_dates = incomingMessages.getString("date");
            String G_time = incomingMessages.getString("time");
            String G_direction = incomingMessages.getString("direction");
            int positionEdited = incomingMessages.getInt("edit");

            if (G_place.length() > 2) {
                dbSalt = G_place.substring(0, 2);
            } else {
                dbSalt = G_place;
            }
            String separator = " ";
            String dbDates;
            int sep = G_dates.lastIndexOf(separator);
            dbDates = G_dates.substring(0, sep);
            dbSalt = dbSalt + dbDates;
        }
         btn_add.setOnClickListener(view -> {
            startActivity(new Intent(List__Grooming.this, Add_Grooming.class));
            finish();
        });
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Grooming").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        myGrooming.getMyGroomingList().add(snapshot.toObject(C__Grooming.class));

                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void editGrooming(int position) {
        Intent i = new Intent(getApplicationContext(),Add_Grooming.class);
        C__Grooming g = myGrooming.getMyGroomingList().get(position);
        i.putExtra("place", g.getPlace());
        i.putExtra("date", g.getDate());
        i.putExtra("time", g.getTime());
        i.putExtra("direction", g.getDirection());
        i.putExtra("edit", position);
        startActivity(i);

    }
}