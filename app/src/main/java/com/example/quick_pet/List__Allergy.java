package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Collections;

public class List__Allergy extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_allergy;
    Button btn_ok;
    C__AllergyAdapter adapter;
    C__Allergy_MyAllergies myAllergies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_allergy);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTLA);
        lv_allergy = (ListView) findViewById(R.id.listView_Allergy);
        btn_ok = (Button) findViewById(R.id.btn_list_allergy);

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

            C__Allergy a = new C__Allergy(A_name, A_date, A_symptom, A_medication);
            if(positionEdited >-1){
                myAllergies.getMyAllergyList().remove(positionEdited);
            }
            myAllergies.getMyAllergyList().add(a);
            Collections.sort(myAllergies.getMyAllergyList());
            adapter.notifyDataSetChanged();
        }

        lv_allergy.setOnItemClickListener((adapterView, view, position, l) -> editAllergy(position));
        btn_ok.setOnClickListener(view -> startActivity(new Intent(List__Allergy.this, Add_Allergy.class)));
        back_arrow.setOnClickListener(view -> startActivity(new Intent(List__Allergy.this, Main_menu.class)));
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