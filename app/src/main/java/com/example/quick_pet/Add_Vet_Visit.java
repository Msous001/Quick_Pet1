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

public class Add_Vet_Visit extends AppCompatActivity {

    Button btnNext;
    ImageView back_arrow, calendar_app_newVet;
    EditText name, dates, direction, weight;
    int positionToEdit = -1;

    private int mDate, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vet);


        name = (EditText) findViewById(R.id.et_newVet_name);
        dates = (EditText) findViewById(R.id.et_newVet_date);
        direction = (EditText) findViewById(R.id.et_newVet_direction);
        weight = (EditText) findViewById(R.id.et_newVet_weight);
        back_arrow = (ImageView) findViewById(R.id.back_arro_new_vet_visit);

        btnNext = (Button) findViewById(R.id.next_btn_newVet);

        calendar_app_newVet = findViewById(R.id.calendar_date_newVet);
        calendar_app_newVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal1 = Calendar.getInstance();
                mDate = cal1.get(Calendar.DATE);
                mMonth = cal1.get(Calendar.MONTH);
                mYear = cal1.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Vet_Visit.this,
                        android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM YYYY");
                    cal1.set(year, month, date);
                    String dateString = sdf.format(cal1.getTime());
                    dates.setText(dateString);
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
        back_arrow.setOnClickListener(view -> finish());

        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {

            String E_name = incomingIntent.getString("name");
            String E_date = incomingIntent.getString("date");
            String E_direction = incomingIntent.getString("direction");
            int E_weight = incomingIntent.getInt("weight");
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
            weight.setText(Integer.toString(E_weight));
        }
        btnNext.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newDirection = direction.getText().toString();
            int newWeight = Integer.parseInt(weight.getText().toString());

            if (TextUtils.isEmpty(newName)) {
                newName = "Not Defined";
            }
            if (TextUtils.isEmpty(newDates)) {
                newDates = "Not Defined";
            }
            if (TextUtils.isEmpty(newDirection)) {
                newDirection = "Not Defined";

            }
            if (TextUtils.isEmpty(String.valueOf(newWeight))) {
                newWeight = 0;
            }

            Intent i = new Intent(view.getContext(), List__Vet.class);
            i.putExtra("edit", positionToEdit);
            i.putExtra("name", newName);
            i.putExtra("date", newDates);
            i.putExtra("direction", newDirection);
            i.putExtra("weight", newWeight);

            startActivity(i);
        });

    }
}