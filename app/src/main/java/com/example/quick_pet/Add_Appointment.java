package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Appointment extends AppCompatActivity {

    //variables
    Button btnNext;
    private EditText et_Name, et_date, et_time, et_direction, et_reminder;
    private Spinner type;
    ImageView back_arrow;
    CircleImageView circleImageView;
    List<C__Appointment> appointmentList;
    C__GlobalVariable myApplication = (C__GlobalVariable) this.getApplication();
    private C__CurrentPet_MyCurrentPet myCurrentPet;
    private static String pet_name;
    private int mDate, mMonth, mYear, mHour, mMinute;
    private int id;
    FirebaseFirestore db;
    private static String dbSalt;
    private static final String TAG = "Add App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        db = FirebaseFirestore.getInstance();

        //connecting to the global variable
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        appointmentList = C__GlobalVariable.getAppointmentsList();

        //matching th variables with the elements in xml file
        et_Name = (EditText) findViewById(R.id.editName);
        et_date = (EditText) findViewById(R.id.edit_date);
        et_time = (EditText) findViewById(R.id.edittime);
        et_direction = (EditText) findViewById(R.id.editDirection);
        et_reminder = (EditText) findViewById(R.id.editReminder);
        type = (Spinner) findViewById(R.id.spinnerType);
        circleImageView = (CircleImageView) findViewById(R.id.circleImagepet);

        //setting the calendar picker
        et_Name.setOnClickListener(v -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            // @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Appointment.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (view, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM" + "\n yyyy");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                et_date.setText(dateString);

            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        // setting the time picker
        et_time.setOnClickListener(view -> {
            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
                mHour = selectedHour;
                mMinute = selectedMinute;
                et_time.setText(String.format(Locale.getDefault(), "%02d:%02d", mHour, mMinute));
            };
            int style = AlertDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Appointment.this, style, onTimeSetListener, mHour, mMinute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();

        });
        // back button
        back_arrow = (ImageView) findViewById(R.id.back_arrow_addAppointment);
        // click listener for the button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), List__Appointment.class));
        });

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            circleImageView.setImageURI(Uri.parse(c.getImageUrl()));
            pet_name = c.getName();

        }

        //check if are any appointment on the array
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        C__Appointment appointment = null;
        if (id >= 0) {
            for (C__Appointment ap : appointmentList) {
                if (ap.getId() == id) {
                    appointment = ap;
                }
            }
            String Stype = appointment.getType().toString();
            ArrayAdapter myAdap = (ArrayAdapter) type.getAdapter();
            int spinnerPosition = myAdap.getPosition(Stype);

            type.setSelection(spinnerPosition);
            et_Name.setText(appointment.getName());
            et_date.setText(String.valueOf(appointment.getDate()));
            et_time.setText(String.valueOf(appointment.getTime()));
            et_direction.setText(appointment.getDirection());
            et_reminder.setText(String.valueOf(appointment.getReminder()));
        } else {

        }

        btnNext = (Button) findViewById(R.id.next_btn);
        // click listener for the button
        btnNext.setOnClickListener(view -> {
            try {
                if (id >= 0) {
                    //updating/ editing data
                    C__Appointment updateApp = new C__Appointment(id, pet_name, type.getSelectedItem().toString(), et_Name.getText().toString(), et_date.getText().toString(), et_time.getText().toString(), et_direction.getText().toString(), et_reminder.getText().toString());
                    appointmentList.set(id, updateApp);
                } else {
                    //adding new data
                    int nextId = C__GlobalVariable.getNextId();
                    C__Appointment newAppoint = new C__Appointment(nextId, pet_name, type.getSelectedItem().toString(), et_Name.getText().toString(), et_date.getText().toString(), et_time.getText().toString(), et_direction.getText().toString(), et_reminder.getText().toString());
                    //appointmentList.add(newAppoint);// adding the new appointment to the array
                    C__GlobalVariable.setNextId(nextId++);

                    if (et_Name.getText().toString().length() > 2) {
                        dbSalt = et_Name.getText().toString().substring(0, 2);

                    } else {
                        dbSalt = et_Name.getText().toString();
                    }
                    String separator = " ";
                    String dbDates;
                    int sep = et_Name.getText().toString().lastIndexOf(separator);
                    dbDates = et_Name.getText().toString().substring(0, sep);
                    dbSalt = dbSalt + dbDates;
                    FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = fAuth.getCurrentUser();


                    db.collection("Users").document(firebaseUser.getUid()).collection("Pets").document(pet_name).collection("Appointment")
                            .document("Ap-" + dbSalt)
                            .set(newAppoint).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Pet Added", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                startActivity(new Intent(Add_Appointment.this, List__Appointment.class));
                finish();
            } catch (Exception e) {
                Toast.makeText(Add_Appointment.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}