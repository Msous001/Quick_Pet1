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

public class Add_Surgery extends AppCompatActivity {

    Button btnNext;
    ImageView back_arrow;
    EditText name, dates, med1, med2, notes;
    int positionToEdit = -1;
    private int mDate, mMonth, mYear;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Surgery";
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_surgery);

        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();

        name = (EditText) findViewById(R.id.et_addSurgery_name);
        dates = (EditText) findViewById(R.id.et_addSurgery_date);
        med1 = (EditText) findViewById(R.id.et_addSurgery_med1);
        med2 = (EditText) findViewById(R.id.et_addSurgery_med2);
        notes = (EditText) findViewById(R.id.et_addSurgery_note);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addSurgery);
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_Surgery.this, Main_menu.class));
            finish();
        });

        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Surgery.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });

        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }
        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null){
            String S_name = incomingIntent.getString("name");
            String S_date = incomingIntent.getString("date");
            String S_med1 = incomingIntent.getString("med1");
            String S_med2 = incomingIntent.getString("med2");
            String S_note = incomingIntent.getString("note");
            positionToEdit = incomingIntent.getInt("edit");

            if(TextUtils.isEmpty(S_name)){S_name = "Not Defined";}
            if(TextUtils.isEmpty(S_date)){S_date = "Not Defined";}
            if(TextUtils.isEmpty(S_med1)){S_med1 = "Not Defined";}
            if(TextUtils.isEmpty(S_med2)){S_med2 = " ";}
            if(TextUtils.isEmpty(S_note)){S_note = " ";}

            name.setText(S_name);
            dates.setText(S_date);
            med1.setText(S_med1);
            med2.setText(S_med2);
            notes.setText(S_note);
        }
        btnNext = (Button) findViewById(R.id.next_btn_addSurgery);
        btnNext.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newMed1 = med1.getText().toString();
            String newMed2 = med2.getText().toString();
            String newNote = notes.getText().toString();

            if(TextUtils.isEmpty(newName)) {
                name.setError("This field is required");
                name.requestFocus();
            }
                else if (TextUtils.isEmpty(newDates)) {
                    dates.setError("This field is required");
                    dates.requestFocus();
                }
                else{
                if (TextUtils.isEmpty(newMed1)) {
                    newMed1 = "Not Defined";
                }
                if (TextUtils.isEmpty(newMed2)) {
                    newMed2 = "";
                }
                if (TextUtils.isEmpty(newNote)) {
                    newNote = "";
                }

                C__Surgery ca = new C__Surgery(pet_name, newName,newDates,newMed1,newMed2,newNote);
                if(newName.length() > 2){
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
                        .document(pet_name).collection("Surgery").document("S-"+dbSalt)
                        .set(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Surgery Added", Toast.LENGTH_SHORT).show();

                    }
                });


                Intent i = new Intent(view.getContext(), List__Surgery.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("date", newDates);
                i.putExtra("med1", newMed1);
                i.putExtra("med2", newMed2);
                i.putExtra("note", newNote);
                startActivity(i);

            }
        });

    }
}