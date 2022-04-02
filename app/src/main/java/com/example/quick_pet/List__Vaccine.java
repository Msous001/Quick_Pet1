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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class List__Vaccine extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_vaccine;
    Button btn_ok;
    C__VaccineAdapter adapter;
    C__Vaccine_MyVaccine myVaccine;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vaccine);

        back_arrow = (ImageView) findViewById(R.id.back_arrowVa);
        lv_vaccine = (ListView) findViewById(R.id.listView_Vaccine);
        btn_ok = (Button) findViewById(R.id.btn_list_vaccine);

        //currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }
        myVaccine = ((C__GlobalVariable) this.getApplication()).getMyVaccine();
        myVaccine.myVaccineList = new ArrayList<>();
        adapter = new C__VaccineAdapter(List__Vaccine.this, myVaccine);
        lv_vaccine.setAdapter(adapter);

        Bundle incominMessages = getIntent().getExtras();
        if(incominMessages != null){
            String V_name = incominMessages.getString("name");
            String V_date = incominMessages.getString("date");
            String V_vetName = incominMessages.getString("vetName");
            int positionEdited = incominMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();
            C__Vaccine Va = new C__Vaccine(pet_name, V_name, V_date, V_vetName);
            if(positionEdited >-1){
                myVaccine.getMyVaccineList().remove(positionEdited);
                //here i need to call the database to delete the item

            }
            myVaccine.getMyVaccineList().add(Va);
            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Vaccines").push().setValue(Va);
            Collections.sort(myVaccine.getMyVaccineList());
            adapter.notifyDataSetChanged();
        }
        lv_vaccine.setOnItemClickListener((adapterView, view, position, l) -> editVaccine(position));
        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Vaccine.this, Add_Vaccine.class));
            finish();
        });
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Vaccine.this, Main_menu.class));
            finish();
        });
    }

    private void editVaccine(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Vaccine.class);
        C__Vaccine v = myVaccine.getMyVaccineList().get(position);
        i.putExtra("name", v.getVac_name());
        i.putExtra("date", v.getVac_date());
        i.putExtra("vetName", v.getVac_vetName());
        i.putExtra("edit", position);
        startActivity(i);

    }
}