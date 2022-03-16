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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Vaccine extends AppCompatActivity {

    Button btnNext;
    ImageView back_arrow, calendar_app_newVaccine;
    EditText name, dates, vetName;
    int positionToEdit = -1;

    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine);

        name = (EditText) findViewById(R.id.et_addVaccine_name);
        dates = (EditText) findViewById(R.id.et_addVaccine_date);
        vetName = (EditText) findViewById(R.id.et_addvaccine_vetName);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addVaccine);
        back_arrow.setOnClickListener(view -> startActivity(new Intent(Add_Vaccine.this, List__Vaccine.class)));

        calendar_app_newVaccine = (ImageView) findViewById(R.id.calendar_date_addvaccine);
        calendar_app_newVaccine.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Vaccine.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });

        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null){
            String V_name = incomingIntent.getString("name");
            String V_date = incomingIntent.getString("date");
            String V_vetName = incomingIntent.getString("vetName");
            positionToEdit = incomingIntent.getInt("edit");

            if(TextUtils.isEmpty(V_name)){
                V_name = "Not Defined";
            }
            if(TextUtils.isEmpty(V_date)){
                V_date = "Not Defined";
            }
            if(TextUtils.isEmpty(V_vetName)){
                V_vetName = "Not Defined";
            }
            name.setText(V_name);
            dates.setText(V_date);
            vetName.setText(V_vetName);

        }
        btnNext = (Button) findViewById(R.id.next_btn_addVaccine);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = name.getText().toString();
                String newDates = dates.getText().toString();
                String newVetName = vetName.getText().toString();

                if (TextUtils.isEmpty(newName)) {newName = "Not Defined";}
                if (TextUtils.isEmpty(newDates)) {newDates = "Not Defined";}
                if (TextUtils.isEmpty(newVetName)){newVetName = "Not Defined";}

                Intent i = new Intent(view.getContext(), List__Vaccine.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("date", newDates);
                i.putExtra("vetName", newVetName);
                startActivity(i);
            }
        });
    }
}