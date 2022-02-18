package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Add_HealthCondition extends AppCompatActivity {

    Button btnNext;
    ImageView back_arrow;
    EditText name, effects, symptom, medication;
    int positionToEdit = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health);

        name = (EditText) findViewById(R.id.et_addHealth_name);
        effects = (EditText) findViewById(R.id.et_addHealth_effects);
        symptom = (EditText) findViewById(R.id.et_addHealth_symptoms);
        medication = (EditText) findViewById(R.id.et_addHealth_medication);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addHealth);
        back_arrow.setOnClickListener(view -> finish());

        btnNext = (Button) findViewById(R.id.next_btn_addHealth);

        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String H_name = incomingIntent.getString("name");
            String H_effects = incomingIntent.getString("effects");
            String H_symptoms = incomingIntent.getString("symptoms");
            String H_medication = incomingIntent.getString("medication");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(H_name)) {
                H_name = "Not Defined";
            }
            if (TextUtils.isEmpty(H_effects)) {
                H_effects = "Not Defined";
            }
            if (TextUtils.isEmpty(H_symptoms)) {
                H_symptoms = "Not Defined";
            }
            if (TextUtils.isEmpty(H_medication)) {
                H_medication = "Not Defined";
            }

            name.setText(H_name);
            effects.setText(H_effects);
            symptom.setText(H_symptoms);
            medication.setText(H_medication);
        }
        btnNext.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newEffect = effects.getText().toString();
            String newSymptom = symptom.getText().toString();
            String newMedication = medication.getText().toString();

            if (TextUtils.isEmpty(newName)) {
                newName = "Not Defined";
            }
            if (TextUtils.isEmpty(newEffect)) {
                newEffect = "Not Defined";
            }
            if (TextUtils.isEmpty(newSymptom)) {
                newSymptom = "Not Defined";
            }
            if (TextUtils.isEmpty(newMedication)) {
                newMedication = "Not Defined";
            }

            Intent i = new Intent(view.getContext(), List__Health.class);
            i.putExtra("edit", positionToEdit);
            i.putExtra("name", newName);
            i.putExtra("effects", newEffect);
            i.putExtra("symptoms", newSymptom);
            i.putExtra("medication", newMedication);
            startActivity(i);
        });


    }

}