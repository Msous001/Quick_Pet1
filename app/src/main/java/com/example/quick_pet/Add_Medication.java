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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Medication extends AppCompatActivity {

    ImageView back_arrow, calendar_medication;
    EditText name, dates, reason;
    Button btn_add;
    private int mDate, mMonth, mYear;
    int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addMed);
        calendar_medication = (ImageView) findViewById(R.id.calendar_date_addMedication);

        name = (EditText) findViewById(R.id.et_addMedication_name);
        dates = (EditText) findViewById(R.id.et_addMedication_date);
        reason = (EditText) findViewById(R.id.et_addMedication_reason);

        btn_add = (Button) findViewById(R.id.next_btn_addMedication);

        calendar_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        back_arrow.setOnClickListener(view -> startActivity(new Intent(Add_Medication.this, List__Medication.class)));

        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null){
            String M_name = incomingIntent.getString("name");
            String M_date = incomingIntent.getString("date");
            String M_reason = incomingIntent.getString("reason");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(M_name)) {
                Toast.makeText(Add_Medication.this, "Please add a Name", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(M_date)) {
                M_date = "Not Defined";
            }
            if (TextUtils.isEmpty(M_reason)){
                M_reason = "";
            }
            name.setText(M_name);
            dates.setText(M_date);
            reason.setText(M_reason);
        }
        btn_add.setOnClickListener(view -> {
            String newName = name.getText().toString();
            String newDates = dates.getText().toString();
            String newReason = reason.getText().toString();

            if(TextUtils.isEmpty(newName)){
                Toast.makeText(Add_Medication.this, "Please add a Name", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(newDates)) {
                newDates = "Not Defined";
            }
            if (TextUtils.isEmpty(newReason)) {
                newReason = "";
            }

            Intent i = new Intent(view.getContext(), List__Medication.class);
            i.putExtra("edit", positionToEdit);
            i.putExtra("name", newName);
            i.putExtra("date", newDates);
            i.putExtra("reason", newReason);
            startActivity(i);
        });
    }
}