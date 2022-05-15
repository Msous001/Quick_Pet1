package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_Appointment extends AppCompatActivity {

    //variables
    Button btnNext;
    private EditText et_Name, et_date, et_time, et_direction;
    private Spinner type;
    ImageView back_arrow;
    CircleImageView circleImageView;
    int positionToEdit = -1;
    private static String pet_name;
    private int mDate, mMonth, mYear, mHour, mMinute;
    public int currentDate, currentMonth, currentYear, currentHour, currentMinute;

    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = fAuth.getCurrentUser();
    private static String dbSalt;
    private static final String TAG = "Add App";
    C__Notification_MyNot myNot;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        db = FirebaseFirestore.getInstance();


        //connecting to the global variable
        C__CurrentPet_MyCurrentPet myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myNot = ((C__GlobalVariable) this.getApplication()).getMyNot();


        //matching th variables with the elements in xml file
        et_Name = (EditText) findViewById(R.id.editName);
        et_date = (EditText) findViewById(R.id.edit_date);
        et_time = (EditText) findViewById(R.id.edittime);
        et_direction = (EditText) findViewById(R.id.editDirection);
        type = (Spinner) findViewById(R.id.spinnerType);
        circleImageView = (CircleImageView) findViewById(R.id.circleImagepet);
        aSwitch = (Switch) findViewById(R.id.switch2);

        //setting the calendar picker
        et_date.setOnClickListener(v -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            // @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Appointment.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (view, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM" + "\n yyyy");
                cal1.set(year, month, date);
                currentYear = year;
                currentMonth = month;
                currentDate = date;
                String dateString = sdf.format(cal1.getTime());
                et_date.setText(dateString);

            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        // setting the time picker
        et_time.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            calendar.set(0, 0, 0, mHour, mMinute);

            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourD, int minuteD) {
                    et_time.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourD, minuteD, true));
                    currentHour = hourD;
                    currentMinute = minuteD;
                }
            }, 12, 0, true);

            timePickerDialog.updateTime(mHour, mMinute);
            timePickerDialog.setTitle("Select time");
            timePickerDialog.show();

        });
        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                aSwitch.setText(R.string.yes);
                String n_Name = et_Name.getText().toString();
                String n_Dates = et_date.getText().toString();
                String n_Time = et_time.getText().toString();
                C__Notifications n = new C__Notifications(n_Dates, n_Name, n_Time);
                if (n_Name.length() > 2) {
                    dbSalt = n_Name.substring(0, 2);
                } else {
                    dbSalt = n_Name;
                }
                db.collection("Users").document(firebaseUser.getUid()).collection("Notifications")
                        .document(dbSalt + n_Dates).set(n).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Notification Added", Toast.LENGTH_SHORT).show();
                    setNotification();
                });

                createNotificationChannel();
            } else {
                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();

            }
        });


        // back button
        back_arrow = (ImageView) findViewById(R.id.back_arrow_addAppointment);
        // click listener for the button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), List__Appointment.class));
            finish();
        });

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            circleImageView.setImageURI(Uri.parse(c.getImageUrl()));
            pet_name = c.getName();
        }

        //check if are any appointment on the array
        Bundle incomingIntent = getIntent().getExtras();
        if (incomingIntent != null) {
            String A_type = incomingIntent.getString("a_type");
            String A_name = incomingIntent.getString("a_name");
            String A_date = incomingIntent.getString("a_date");
            String A_time = incomingIntent.getString("a_time");
            String A_direction = incomingIntent.getString("a_direction");
            positionToEdit = incomingIntent.getInt("edit");

            if (TextUtils.isEmpty(A_name)) {
                A_name = "";
            }
            if (TextUtils.isEmpty(A_date)) {
                A_date = "";
            }
            if (TextUtils.isEmpty(A_time)) {
                A_time = "";
            }
            if (TextUtils.isEmpty(A_direction)) {
                A_direction = "";
            }

            et_Name.setText(A_name);
            et_date.setText(A_date);
            et_time.setText(A_time);
            et_direction.setText(A_direction);
        }


        btnNext = (Button) findViewById(R.id.next_btn);
        // click listener for the button
        btnNext.setOnClickListener(view -> {
            String newType = type.getSelectedItem().toString();
            String newName = et_Name.getText().toString();
            String newDates = et_date.getText().toString();
            String newTime = et_time.getText().toString();
            String newDirection = et_direction.getText().toString();

            if (TextUtils.isEmpty(newName)) {
                et_Name.setError("Required");
                et_Name.requestFocus();
            } else if (TextUtils.isEmpty(newDates)) {
                et_date.setError("Required");
                et_date.requestFocus();
            } else {
                if (TextUtils.isEmpty(newTime)) {
                    newTime = "Not Defined";
                }
                if (TextUtils.isEmpty(newDirection)) {
                    newDirection = "Not Defined";
                }
                C__Appointment ap = new C__Appointment(pet_name, newType, newName, newDates, newTime, newDirection);
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
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = fAuth.getCurrentUser();

                assert firebaseUser != null;
                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Appointment")
                        .document("Ap-" + UUID.randomUUID())
                        .set(ap).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(),
                        "APP Added", Toast.LENGTH_SHORT).show());


                //startActivity(new Intent(Add_Appointment.this, List__Appointment.class));
                Intent i = new Intent(view.getContext(), List__Appointment.class);
                i.putExtra("edit", positionToEdit);
                i.putExtra("a_type", newType);
                i.putExtra("a_name", newName);
                i.putExtra("a_date", newDates);
                i.putExtra("a_time", newTime);
                i.putExtra("a_direction", newDirection);
                startActivity(i);
                finish();
            }
//
        });

    }

    private void setNotification() {
        Intent intent = new Intent(Add_Appointment.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Add_Appointment.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar final_calendar = Calendar.getInstance();
        final_calendar.set(currentYear, currentMonth, currentDate, currentHour, currentMinute, 0);
        long startTime = final_calendar.getTimeInMillis();


        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
    }

    private void createNotificationChannel() {
        // Build.Version.SDK_INT = returns the api level of the device
        // Build.Version_codes.0 = api level to compile against the app/build gradle
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Quick Pet";
            String description = "Channel for Quick pet Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("quick_pet", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

//        et_reminder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                mHour = calendar.get(Calendar.HOUR_OF_DAY);
//                mMinute = calendar.get(Calendar.MINUTE);
//                calendar.set(0,0,0, mHour, mMinute);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourAD, int minuteAD) {
//                        et_reminder.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourAD, minuteAD, true));
//                        mmMinute = minuteAD - mMinute;
//                    }
//                }, 12, 0, true);
//
//                timePickerDialog.updateTime(mHour, mMinute);
//                timePickerDialog.setTitle("Select time");
//                timePickerDialog.show();
//            }
//        });
//        et_time.setOnClickListener(view -> {
//            Calendar calendar = Calendar.getInstance();
//            TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
//                mHour = selectedHour;
//                mMinute = selectedMinute;
//                et_time.setText(String.format(Locale.getDefault(), "%02d:%02d", mHour, mMinute));
//            };
//            int style = AlertDialog.THEME_HOLO_DARK;
//            TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Appointment.this,
//                    style, onTimeSetListener, mHour, mMinute, true);
//            timePickerDialog.setTitle("Select Time");
//            timePickerDialog.show();
//        });