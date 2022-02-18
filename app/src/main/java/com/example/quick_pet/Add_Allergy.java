package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Allergy extends AppCompatActivity {


    Button btnNext;
    ImageView back_arrow, calendar_app_newVet;
    EditText name, dates, symptom, medication;
    int positionToEdit = -1;

    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allergy);

        name = (EditText) findViewById(R.id.et_allergy_name);
        dates = (EditText) findViewById(R.id.et_allergy_date);
        symptom = (EditText) findViewById(R.id.et_allergy_symptoms);
        medication = (EditText) findViewById(R.id.et_allergy_medication);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_allergy);
        back_arrow.setOnClickListener(view -> finish());

        calendar_app_newVet = (ImageView) findViewById(R.id.calendar_date_allergy);
        calendar_app_newVet.setOnClickListener(view -> {
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

        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String A_name = incomingIntent.getString("name");
            String A_date = incomingIntent.getString("date");
            String A_symptom = incomingIntent.getString("symptom");
            String A_medication = incomingIntent.getString("medication");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(A_name)) {
                A_name = "Not Defined";
            }
            if (TextUtils.isEmpty(A_date)) {
                A_date = "Not Defined";
            }
            if (TextUtils.isEmpty(A_symptom)) {
                A_symptom = "Not Defined";
            }
            if (TextUtils.isEmpty(A_medication)) {
                A_medication = "Not Defined";
            }
            name.setText(A_name);
            dates.setText(A_date);
            symptom.setText(A_symptom);
            medication.setText(A_medication);
        }
        btnNext = (Button) findViewById(R.id.next_btn_allergy);
        btnNext.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newSymptom = symptom.getText().toString();
            String newMedication = medication.getText().toString();

            if (TextUtils.isEmpty(newName)) {
                newName = "Not Defined";
            }
            if (TextUtils.isEmpty(newDates)) {
                newDates = "Not Defined";
            }
            if (TextUtils.isEmpty(newSymptom)) {
                newSymptom = "Not Defined";
            }
            if (TextUtils.isEmpty(newMedication)) {
                newMedication = "Not Defined";
            }

            Intent i = new Intent(view.getContext(), List__Allergy.class);
            i.putExtra("edit", positionToEdit);
            i.putExtra("name", newName);
            i.putExtra("date", newDates);
            i.putExtra("symptom", newSymptom);
            i.putExtra("medication", newMedication);

            startActivity(i);
        });

    }
}