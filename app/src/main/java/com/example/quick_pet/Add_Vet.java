package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Vet extends AppCompatActivity {

    Button btnNext;
    ImageView back_arrow;
    private EditText name, dates, direction, weight;
    int positionToEdit = -1;
    private int mDate, mMonth, mYear;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Pet";
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vet);
        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();


        name = (EditText) findViewById(R.id.et_newVet_name);
        dates = (EditText) findViewById(R.id.et_newVet_date);
        direction = (EditText) findViewById(R.id.et_newVet_direction);
        weight = (EditText) findViewById(R.id.et_newVet_weight);
        back_arrow = (ImageView) findViewById(R.id.back_arro_new_vet_visit);

        btnNext = (Button) findViewById(R.id.next_btn_newVet);

        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Vet.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Vet.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_Vet.this, List__Vet.class));
            finish();
        });

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }
        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {

            String E_name = incomingIntent.getString("name");
            String E_date = incomingIntent.getString("date");
            String E_direction = incomingIntent.getString("direction");
            double E_weight = incomingIntent.getDouble("weight");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(E_name)) {
                E_name = "Not Defined";
            }
            if (TextUtils.isEmpty(E_date)) {
                E_date = "Not Defined";
            }
            if (TextUtils.isEmpty(E_direction)) {
                E_direction = "Not Defined";
            }
            if (TextUtils.isEmpty(String.valueOf(E_weight))) {
                E_weight = 0;
            }
            name.setText(E_name);
            dates.setText(E_date);
            direction.setText(E_direction);
            weight.setText(Double.toString(E_weight));
        }
        btnNext.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newDirection = direction.getText().toString();
            String newWeight = weight.getText().toString();

            if (TextUtils.isEmpty(newName)) {
                name.setError("Required");
                name.requestFocus();
            } else if (TextUtils.isEmpty(newDates)) {
                dates.setError("Required");
                dates.requestFocus();

            } else {
                if (TextUtils.isEmpty(newDirection)) {
                    newDirection = "Not Defined";
                }

                if (TextUtils.isEmpty((newWeight))) {
                    newWeight = "0.0";
                }

                C__Vet ca = new C__Vet(pet_name, newName, newDates, newDirection, Double.parseDouble(newWeight));

                if (newName.length() > 2) {
                    dbSalt = newName.substring(0, 2);
                } else {
                    dbSalt = newName;
                }
                String separator = " ";
                String dbDates;
                int sep = newDates.lastIndexOf(separator);
                dbDates = newDates.substring(0, sep);
                dbSalt = dbSalt + dbDates;
                //Connecting to the database
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = fAuth.getCurrentUser();

                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Veterinary").document("V-" + dbSalt)
                        .set(ca).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Vet Added", Toast.LENGTH_SHORT).show());
                Intent i = new Intent(view.getContext(), List__Vet.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("date", newDates);
                i.putExtra("direction", newDirection);
                i.putExtra("weight", newWeight);

                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}