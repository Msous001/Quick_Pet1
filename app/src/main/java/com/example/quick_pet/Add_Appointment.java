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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Appointment extends AppCompatActivity {

    Button btnNext;
    EditText et_Name, et_date, et_time, et_direction, et_reminder;
    Spinner type;
    ImageView calendar_date_app, image_time_picker, back_arrow;
    CircleImageView circleImageView;
    List<C__Appointment> appointmentList;
    List<C__CurrentPet> currentPetList;
    C__GlobalVariable myApplication = (C__GlobalVariable) this.getApplication();
    C__Pet_MyPets myPetList;
    private static String  pet_name;
    private static String pic;


    private int mDate, mMonth, mYear, mHour, mMinute;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        appointmentList = C__GlobalVariable.getAppointmentsList();
        currentPetList = C__GlobalVariable.getCurrentPets();
        myPetList =  ((C__GlobalVariable) this.getApplication()).getMyPets();


        et_Name = (EditText) findViewById(R.id.editName);
        et_date = (EditText) findViewById(R.id.edit_date);
        et_time = (EditText) findViewById(R.id.edittime);
        et_direction = (EditText) findViewById(R.id.editDirection);
        et_reminder = (EditText) findViewById(R.id.editReminder);
        type = (Spinner) findViewById(R.id.spinnerType);

        circleImageView = (CircleImageView) findViewById(R.id.circleImagepet);
        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
            circleImageView.setImageURI(Uri.parse(c.getImageUrl()));
        }

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addAppointment);
        back_arrow.setOnClickListener(view -> startActivity(new Intent(Add_Appointment.this, List__Appointment.class)));

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
        btnNext.setOnClickListener(view -> {
            try {
                if (id >= 0) {
                    C__Appointment updateApp = new C__Appointment(id, type.getSelectedItem().toString(), et_Name.getText().toString(), et_date.getText().toString(), et_time.getText().toString(), et_direction.getText().toString(), et_reminder.getText().toString());
                    appointmentList.set(id, updateApp);
                } else {
                    int nextId = myApplication.getNextId();
                    C__Appointment newAppoint = new C__Appointment(nextId, type.getSelectedItem().toString(), et_Name.getText().toString(), et_date.getText().toString(), et_time.getText().toString(), et_direction.getText().toString(), et_reminder.getText().toString());

                    appointmentList.add(newAppoint);
                    myApplication.setNextId(nextId++);
                    FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = fAuth.getCurrentUser();

                    DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
                    appReference.child("Appointment").push().setValue(newAppoint);
                }
                //

                startActivity(new Intent(Add_Appointment.this, List__Appointment.class));
            } catch (Exception e) {
                Toast.makeText(Add_Appointment.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        calendar_date_app = (ImageView) findViewById(R.id.calendar_app_date);
        calendar_date_app.setOnClickListener(v -> {
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

        image_time_picker = (ImageView) findViewById(R.id.imageViewTime);
        image_time_picker.setOnClickListener(view -> {
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
    }
}