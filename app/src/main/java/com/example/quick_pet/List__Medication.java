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

import java.util.Collections;
import java.util.List;

public class List__Medication extends AppCompatActivity {

    ImageView back_Arrow;
    ListView lv_medication;
    Button btn_ok;
    C__MedicationAdapter adapter;
    C__Medication_MyMedication myMedication;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medication);

        back_Arrow = (ImageView) findViewById(R.id.back_arrowMed);
        lv_medication = (ListView) findViewById(R.id.listView_Medication);
        btn_ok = (Button) findViewById(R.id.btn_list_medication);

        currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }
        myMedication = ((C__GlobalVariable) this.getApplication()).getMyMedication();
        adapter = new C__MedicationAdapter(List__Medication.this, myMedication);
        lv_medication.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String reason = incomingMessages.getString("reason");
            int positionEdited = incomingMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();

            C__Medication m = new C__Medication(name, date, reason);
            if (positionEdited > -1){
                myMedication.getMyMedicationList().remove(positionEdited);
                //here i need to call the database to delete this item

            }
            myMedication.getMyMedicationList().add(m);

            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Medication").push().setValue(m);

            Collections.sort(myMedication.getMyMedicationList());
            adapter.notifyDataSetChanged();
        }
        lv_medication.setOnItemClickListener((adapterView, view, position, l) -> editMedication(position));
        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Medication.this, Add_Medication.class));
            finish();
        });
        back_Arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Medication.this, Main_menu.class));
            finish();
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