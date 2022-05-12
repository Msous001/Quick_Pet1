package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class List__Vet extends AppCompatActivity {

    ImageView back_arrow;

    ListView lv_listVet;
    C__VetAdapter adapter;
    C__Vet_MyVets myVets;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    Button btn_add;
    private static String pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Vet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vet);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myVets = ((C__GlobalVariable) this.getApplication()).getMyVets();
        myVets.myVetsList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        btn_add = (Button) findViewById(R.id.btn_list_vet);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Click");
                startActivity(new Intent(List__Vet.this, Add_Vet.class));
            }
        });
        back_arrow = (ImageView) findViewById(R.id.back_arrowTL);
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Vet.this, Main_menu.class));
            finish();
        });

        lv_listVet = (ListView) findViewById(R.id.listView_Vet);
        adapter = new C__VetAdapter(List__Vet.this, myVets);
        lv_listVet.setAdapter(adapter);
        Collections.sort(myVets.getMyVetsList());
        adapter.notifyDataSetChanged();

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }

        Bundle incomingMessages = getIntent().getExtras();
        EventChangeListener();
        lv_listVet.setOnItemClickListener((adapterView, view, position, l) -> editVet(position));

        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String direction = incomingMessages.getString("direction");
            String weight = incomingMessages.getString("weight");
            int positionEdited = incomingMessages.getInt("edit");

            String separator = " ";
            String dbDates;
            int sep = date.lastIndexOf(separator);
            dbDates = date.substring(0, sep);
            dbSalt = dbSalt + dbDates;

        }
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Veterinary").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                    myVets.getMyVetsList().add(snapshot.toObject(C__Vet.class));
                }
                adapter.notifyDataSetChanged();
            }
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