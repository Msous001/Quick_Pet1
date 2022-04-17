package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Add_Allergy extends AppCompatActivity {

    //variables
    Button btnNext;
    ImageView back_arrow;
    private EditText name, dates, symptom, medication;
    int positionToEdit = -1;
    private int mDate, mMonth, mYear;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Allergy";
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allergy);

        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();

        //matching the variables with the elements in xml file
        name = (EditText) findViewById(R.id.et_allergy_name);
        dates = (EditText) findViewById(R.id.et_allergy_date);
        symptom = (EditText) findViewById(R.id.et_allergy_symptoms);
        medication = (EditText) findViewById(R.id.et_allergy_medication);

        // back button
        back_arrow = (ImageView) findViewById(R.id.back_arrow_allergy);
        // click listener for the button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_Allergy.this, List__Allergy.class));
            finish();
        });

        //setting the calendar picker
        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Allergy.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");
                        cal1.set(year, month, date);
                        String dateString = sdf.format(cal1.getTime());
                        dates.setText(dateString);
                    }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }

        //receive information for editing process
        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String A_name = incomingIntent.getString("name");
            String A_date = incomingIntent.getString("date");
            String A_symptom = incomingIntent.getString("symptom");
            String A_medication = incomingIntent.getString("medication");
            positionToEdit = incomingIntent.getInt("edit");
            //validation to avoid system crash
            if (TextUtils.isEmpty(A_name)) {A_name = "Not Defined";}
            if (TextUtils.isEmpty(A_date)) {A_date = "Not Defined";}
            if (TextUtils.isEmpty(A_symptom)) {A_symptom = "Not Defined";}
            if (TextUtils.isEmpty(A_medication)) {A_medication = "Not Defined";}
            name.setText(A_name);
            dates.setText(A_date);
            symptom.setText(A_symptom);
            medication.setText(A_medication);
        }
        btnNext = (Button) findViewById(R.id.next_btn_allergy);
        // click listener for the button
        btnNext.setOnClickListener(view -> {
            //collecting information from user input
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newSymptom = symptom.getText().toString();
            String newMedication = medication.getText().toString();

            //validation to avoid system crash
            if (TextUtils.isEmpty(newName)) {
                name.setError("Required");
                name.requestFocus();
            } else if (TextUtils.isEmpty(newDates)) {
                dates.setError("Required");
                dates.requestFocus();

            } else {
                if (TextUtils.isEmpty(newSymptom)) {
                    newSymptom = "Not Defined";
                }
                if (TextUtils.isEmpty(newMedication)) {
                    newMedication = "Not Defined";
                }
                C__Allergy ca = new C__Allergy(pet_name, newName, newDates, newSymptom, newMedication);


                if ( newName.length() > 2){
                    dbSalt = newName.substring(0,2);
                }else{
                    dbSalt = newName;
                }
                String separator = " ";
                String dbDates;
                int sep = newDates.lastIndexOf(separator);
                dbDates= newDates.substring(0,sep);
                dbSalt = dbSalt + dbDates;
                //Connecting to the database
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = fAuth.getCurrentUser();


                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Allergy").document("A-"+dbSalt)
                        .set(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Allergy Added", Toast.LENGTH_SHORT).show();

                    }
                });
                // I use Intents to transfer data from one Activity to another
                Intent i = new Intent(view.getContext(), List__Allergy.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("date", newDates);
                i.putExtra("symptom", newSymptom);
                i.putExtra("medication", newMedication);
                startActivity(i);
            }

        });

    }
}