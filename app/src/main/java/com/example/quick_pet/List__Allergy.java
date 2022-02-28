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

public class List__Allergy extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_allergy;
    Button btn_ok;
    C__AllergyAdapter adapter;
    C__Allergy_MyAllergies myAllergies;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_allergy);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTLA);
        lv_allergy = (ListView) findViewById(R.id.listView_Allergy);
        btn_ok = (Button) findViewById(R.id.btn_list_allergy);
        currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }

        myAllergies = ((C__GlobalVariable) this.getApplication()).getMyAllergies();
        adapter = new C__AllergyAdapter(List__Allergy.this, myAllergies);
        lv_allergy.setAdapter(adapter);

        Bundle incominMessages = getIntent().getExtras();
        if(incominMessages != null){
            String A_name = incominMessages.getString("name");
            String A_date = incominMessages.getString("date");
            String A_symptom = incominMessages.getString("symptom");
            String A_medication = incominMessages.getString("medication");
            int positionEdited = incominMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();
            C__Allergy a = new C__Allergy(A_name, A_date, A_symptom, A_medication);
            if(positionEdited >-1){
                myAllergies.getMyAllergyList().remove(positionEdited);
                //here i need to call the database to delete the item
            }
            myAllergies.getMyAllergyList().add(a);


            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Allergy").push().setValue(a);


            Collections.sort(myAllergies.getMyAllergyList());
            adapter.notifyDataSetChanged();
        }

        lv_allergy.setOnItemClickListener((adapterView, view, position, l) -> editAllergy(position));
        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Allergy.this, Add_Allergy.class));
            finish();
        });
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Allergy.this, Main_menu.class));
            finish();
        });
    }

    private void editAllergy(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Allergy.class);
        C__Allergy a = myAllergies.getMyAllergyList().get(position);
        i.putExtra("name", a.getName());
        i.putExtra("edit", position);
        i.putExtra("date", a.getDate());
        i.putExtra("symptom", a.getSymptomsName());
        i.putExtra("medication", a.getMedicationName());
        startActivity(i);
    }
}