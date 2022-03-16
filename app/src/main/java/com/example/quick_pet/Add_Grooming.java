package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_Grooming extends AppCompatActivity {

    Button btnNext;
    EditText et_place, et_date, et_time, et_direction;
    ImageView calendar_date_app, image_time_picker, back_arrow;
    private int mDate, mMonth, mYear, mHour, mMinute;
    int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grooming);

        et_place = ((EditText) findViewById(R.id.et_grooming_place));
        et_date = ((EditText) findViewById(R.id.et_grooming_date));
        et_time = ((EditText) findViewById(R.id.et_grooming_time));
        et_direction = ((EditText) findViewById(R.id.et_grooming_direction));

        back_arrow = ((ImageView) findViewById(R.id.back_arrow_grooming));
        back_arrow.setOnClickListener(view -> startActivity(new Intent(Add_Grooming.this, List__Grooming.class)));

        calendar_date_app = ((ImageView)findViewById(R.id.calendar_date_grooming));
        calendar_date_app.setOnClickListener(v -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            // @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Grooming.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (view, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                et_date.setText(dateString);

            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });

        image_time_picker = ((ImageView) findViewById(R.id.calendar_time_grooming));
        image_time_picker.setOnClickListener(view -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
                mHour = selectedHour;
                mMinute = selectedMinute;
                et_time.setText(String.format(Locale.getDefault(), "%02d:%02d", mHour, mMinute));
            };
            int style = AlertDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Grooming.this, style, onTimeSetListener, mHour, mMinute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        });

        Bundle incomingIntent = getIntent().getExtras();
        if(incomingIntent != null){
            String G_place = incomingIntent.getString("place");
            String G_date = incomingIntent.getString("date");
            String G_time = incomingIntent.getString("time");
            String G_direction = incomingIntent.getString("direction");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(G_place)) {
                G_place = "Not Defined";
            }
            if (TextUtils.isEmpty(G_date)) {
                G_date = "Not Defined";
            }
            if (TextUtils.isEmpty(G_time)) {
                G_time = "Not Defined";
            }
            if (TextUtils.isEmpty(G_direction)) {
                G_direction = "Not Defined";
            }
            et_place.setText(G_place);
            et_date.setText(G_date);
            et_time.setText(G_time);
            et_direction.setText(G_direction);

        }
        btnNext = ((Button) findViewById(R.id.next_btn_grooming));
        btnNext.setOnClickListener(view -> {
            String newPlace = et_place.getText().toString();
            String newDates = et_date.getText().toString();
            String newTime = et_time.getText().toString();
            String newDirection = et_direction.getText().toString();

            if (TextUtils.isEmpty(newPlace)) {
                newPlace = "Not Defined";
            }
            if (TextUtils.isEmpty(newDates)) {
                newDates = "Not Defined";
            }
            if (TextUtils.isEmpty(newTime)) {
                newTime = "Not Defined";
            }
            if (TextUtils.isEmpty(newDirection)) {
                newDirection = "Not Defined";
            }
            Intent i = new Intent(view.getContext(), List__Grooming.class);
            i.putExtra("edit", positionToEdit);
            i.putExtra("place", newPlace);
            i.putExtra("date", newDates);
            i.putExtra("time", newTime);
            i.putExtra("direction", newDirection);
            startActivity(i);
        });
    }
}