package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Collections;

public class List__Medication extends AppCompatActivity {

    ImageView back_Arrow;
    ListView lv_medication;
    Button btn_ok;
    C__MedicationAdapter adapter;
    C__Medication_MyMedication myMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medication);

        back_Arrow = (ImageView) findViewById(R.id.back_arrowMed);
        lv_medication = (ListView) findViewById(R.id.listView_Medication);
        btn_ok = (Button) findViewById(R.id.btn_list_medication);

        myMedication = ((C__GlobalVariable) this.getApplication()).getMyMedication();
        adapter = new C__MedicationAdapter(List__Medication.this, myMedication);
        lv_medication.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String reason = incomingMessages.getString("reason");
            int positionEdited = incomingMessages.getInt("edit");

            C__Medication m = new C__Medication(name, date, reason);
            if (positionEdited > -1){
                myMedication.getMyMedicationList().remove(positionEdited);
            }
            myMedication.getMyMedicationList().add(m);
            Collections.sort(myMedication.getMyMedicationList());
            adapter.notifyDataSetChanged();
        }
        lv_medication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editMedication(position);
            }
        });
        btn_ok.setOnClickListener(view -> startActivity(new Intent(List__Medication.this, Add_Medication.class)));
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