package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class List__Medication extends AppCompatActivity {

    ImageView back_Arrow;
    ListView lv_medication;
    Button btn_ok;
    C__MedicationAdapter adapter;
    C__Medication_MyMedication myMedication;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Health";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medication);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myMedication = ((C__GlobalVariable) this.getApplication()).getMyMedication();
        myMedication.myMedicationList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        back_Arrow = (ImageView) findViewById(R.id.back_arrowMed);
        lv_medication = (ListView) findViewById(R.id.listView_Medication);
        btn_ok = (Button) findViewById(R.id.btn_list_medication);

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }

        adapter = new C__MedicationAdapter(List__Medication.this, myMedication);
        lv_medication.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String reason = incomingMessages.getString("reason");
            int positionEdited = incomingMessages.getInt("edit");
        }
        lv_medication.setOnItemClickListener((adapterView, view, position, l) -> editMedication(position));
        EventChangeListener();

        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Medication.this, Add_Medication.class));
            finish();
        });
        back_Arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Medication.this, Main_menu.class));
            finish();
        });
    }

    private void EventChangeListener() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Medication").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    myMedication.getMyMedicationList().add(snapshot.toObject(C__Medication.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void editMedication(int position) {

        Intent i = new Intent(getApplicationContext(), Add_Medication.class);
        C__Medication m = myMedication.getMyMedicationList().get(position);
        i.putExtra("name", m.getName());
        i.putExtra("date", m.getDate());
        i.putExtra("reason", m.getReason());
        i.putExtra("edit", position);
        startActivity(i);
    }
}

