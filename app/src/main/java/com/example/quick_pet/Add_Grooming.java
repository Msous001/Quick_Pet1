package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_Grooming extends AppCompatActivity {

    //variables
    Button btnNext;
    private EditText et_place, et_date, et_time, et_direction;
    ImageView back_arrow;
    private int mDate, mMonth, mYear, mHour, mMinute;
    int positionToEdit = -1;
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "Add Groomiming";
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grooming);

        db = FirebaseFirestore.getInstance();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();

        //matching the variables with the elements in xml file
        et_place = ((EditText) findViewById(R.id.et_grooming_place));
        et_date = ((EditText) findViewById(R.id.et_grooming_date));
        et_time = ((EditText) findViewById(R.id.et_grooming_time));
        et_direction = ((EditText) findViewById(R.id.et_grooming_direction));

        //back button
        back_arrow = ((ImageView) findViewById(R.id.back_arrow_grooming));
        // click listener for the button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Add_Grooming.this, List__Grooming.class));
            finish();
        });
        //setting the calendar picker
        et_date.setOnClickListener(v -> {
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
        // setting the time picker
        et_time.setOnClickListener(view -> {
//            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
//                mHour = selectedHour;
//                mMinute = selectedMinute;
//                et_time.setText(String.format(Locale.getDefault(), "%02d:%02d", mHour, mMinute));
//            };
//            int style = AlertDialog.THEME_HOLO_DARK;
//            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Grooming.this, style, onTimeSetListener, mHour, mMinute, true);
//            timePickerDialog.setTitle("Select Time");
//            timePickerDialog.show();
            Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            calendar.set(0,0,0, mHour, mMinute);

            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Grooming.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourD, int minuteD) {
                    et_time.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourD, minuteD, true));
                }
            }, 12, 0, true);

            timePickerDialog.updateTime(mHour, mMinute);
            timePickerDialog.setTitle("Select time");
            timePickerDialog.show();
        });

        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }
        //receive information for editing process
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
        // click listener for the button
        btnNext.setOnClickListener(view -> {
            //collecting information from user input
            String newPlace = et_place.getText().toString();
            String newDates = et_date.getText().toString();
            String newTime = et_time.getText().toString();
            String newDirection = et_direction.getText().toString();
            //validataion to avoid system crash
            if (TextUtils.isEmpty(newPlace)) {
                et_place.setError("Required");
                et_place.requestFocus();
            }
            else if (TextUtils.isEmpty(newDates)) {
                et_date.setError("Required");
                et_date.requestFocus();
            }else {
                if (TextUtils.isEmpty(newTime)) {
                    newTime = "Not Defined";
                }
                if (TextUtils.isEmpty(newDirection)) {
                    newDirection = "Not Defined";
                }

                C__Grooming ca = new C__Grooming(pet_name, newPlace, newDates, newTime,newDirection);
                if(newPlace.length() > 2){
                    dbSalt = newPlace.substring(0,2);
                }else{
                    dbSalt = newPlace;
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
                        .document(pet_name).collection("Grooming").document("G-"+dbSalt)
                        .set(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Gromming Added", Toast.LENGTH_SHORT).show();

                    }
                });
                // I use Intents to transfer data from one Activity to another
                Intent i = new Intent(view.getContext(), List__Grooming.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("place", newPlace);
                i.putExtra("date", newDates);
                i.putExtra("time", newTime);
                i.putExtra("direction", newDirection);
                startActivity(i);
            }
        });

    }
}