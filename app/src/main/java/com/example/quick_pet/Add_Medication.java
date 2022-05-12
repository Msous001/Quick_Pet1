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

public class Add_Medication extends AppCompatActivity {

    //variables
    ImageView back_arrow;
    private EditText name, dates, reason;
    Button btn_add;
    private int mDate, mMonth, mYear;
    int positionToEdit = -1;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Health Condition";
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();


        name = (EditText) findViewById(R.id.et_addMedication_name);
        dates = (EditText) findViewById(R.id.et_addMedication_date);
        reason = (EditText) findViewById(R.id.et_addMedication_reason);

        //matching the variables with the elements in xml file
        back_arrow = (ImageView) findViewById(R.id.back_arrow_addMed);


        btn_add = (Button) findViewById(R.id.next_btn_addMedication);

        //setting the calendar picker
        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Medication.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        //click listener for the back button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_Medication.this, List__Medication.class));
            finish();
        });
        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }

        //receive information for editing process
        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String M_name = incomingIntent.getString("name");
            String M_date = incomingIntent.getString("date");
            String M_reason = incomingIntent.getString("reason");
            positionToEdit = incomingIntent.getInt("edit");

            //validation to avoid system crash
            if (TextUtils.isEmpty(M_name)) {
                Toast.makeText(Add_Medication.this, "Please add a Name", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(M_date)) {
                M_date = "Not Defined";
            }
            if (TextUtils.isEmpty(M_reason)) {
                M_reason = "";
            }
            name.setText(M_name);
            dates.setText(M_date);
            reason.setText(M_reason);
        }
        btn_add.setOnClickListener(view -> {
            //collecting information from user input
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newReason = reason.getText().toString();
            //validation to avoid system crash
            if (TextUtils.isEmpty(newName)) {
                name.setError("This field is required");
                name.requestFocus();

            } else if (TextUtils.isEmpty(newDates)) {
                dates.setError("This field is required");
                dates.requestFocus();
            } else {

                if (TextUtils.isEmpty(newReason)) {
                    newReason = "";
                }
                C__Medication ca = new C__Medication(pet_name, newName, newDates, newReason);

                if (newName.length() > 2) {
                    dbSalt = newName.substring(0, 2);

                } else {
                    dbSalt = newName;
                }
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = fAuth.getCurrentUser();

                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Medication").document("Mc-" + dbSalt)
                        .set(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Medication Added", Toast.LENGTH_SHORT).show();

                    }
                });
                // I use Intents to transfer data from one Activity to another
                Intent i = new Intent(view.getContext(), List__Medication.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("date", newDates);
                i.putExtra("reason", newReason);
                startActivity(i);
                finish();


            }
        });
    }
}