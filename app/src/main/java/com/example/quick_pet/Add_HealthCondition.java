package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_HealthCondition extends AppCompatActivity {
    //variables
    Button btnNext;
    ImageView back_arrow;
    private EditText name, effects, symptom, medication;
    private int positionToEdit = -1;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Health Condition";
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health);

        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();


        //matching the variables with the elements in xml file
        name = (EditText) findViewById(R.id.et_addHealth_name);
        effects = (EditText) findViewById(R.id.et_addHealth_effects);
        symptom = (EditText) findViewById(R.id.et_addHealth_symptoms);
        medication = (EditText) findViewById(R.id.et_addHealth_medication);
        //back button
        back_arrow = (ImageView) findViewById(R.id.back_arrow_addHealth);
        // click listener for the button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_HealthCondition.this, List__Health.class));
            finish();
        });
        //matching the variables with the elements in xml file
        btnNext = (Button) findViewById(R.id.next_btn_addHealth);

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }
        //receive information for editing process
        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String H_name = incomingIntent.getString("name");
            String H_effects = incomingIntent.getString("effects");
            String H_symptoms = incomingIntent.getString("symptoms");
            String H_medication = incomingIntent.getString("medication");
            positionToEdit = incomingIntent.getInt("edit");

            //validation to avoid system crash
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
        // click listener for the button
        btnNext.setOnClickListener(view -> {
            //collecting data from user input
            String newName = name.getText().toString();
            String newEffect = effects.getText().toString();
            String newSymptom = symptom.getText().toString();
            String newMedication = medication.getText().toString();
            //validation to avoid system crash
            //validation to avoid system crash
            if (TextUtils.isEmpty(newName)) {
                name.setError("Required");
                name.requestFocus();
            } else if (TextUtils.isEmpty(newEffect)) {
                effects.setError("Required");
                effects.requestFocus();

            } else {
                if (TextUtils.isEmpty(newSymptom)) {
                    newSymptom = "Not Defined";
                }
                if (TextUtils.isEmpty(newMedication)) {
                    newMedication = "Not Defined";
                }
                C__Health ca = new C__Health(pet_name, newName, newEffect, newSymptom, newMedication);

                if (newName.length() > 2) {
                    dbSalt = newName.substring(0, 2);

                } else {
                    dbSalt = newName;
                }
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = fAuth.getCurrentUser();


                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Health-Condition").document("HC-" + dbSalt)
                        .set(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Heath-Condition Added", Toast.LENGTH_SHORT).show();

                    }
                });


                // I use Intents to transfer data from one Activity to another
                Intent i = new Intent(view.getContext(), List__Health.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("effects", newEffect);
                i.putExtra("symptoms", newSymptom);
                i.putExtra("medication", newMedication);
                startActivity(i);
                finish();
            }
        });


    }

}