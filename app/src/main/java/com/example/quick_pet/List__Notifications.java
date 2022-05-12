package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class List__Notifications extends AppCompatActivity {

    Button btnadd;
    ImageView back_arrow;
    EditText dates, name, time;
    private int mDate, mMonth, mYear, mHour, mMinute;
    public int currentDate, currentMonth, currentYear, currentHour, currentMinute;
    ListView lv_Norification;
    LinearLayout layout_Notification;
    C__Notification_MyNot myNot;
    C__NotificationAdapter adapter;
    private static final String TAG = "Notifications";
    private static String dbSalt;
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = fAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notifications);

        FloatingActionButton fab = findViewById(R.id.fab);
        db = FirebaseFirestore.getInstance();
        myNot = ((C__GlobalVariable) this.getApplication()).getMyNot();

        dates = (EditText) findViewById(R.id.et_notification_date);
        name = (EditText) findViewById(R.id.et_notific_name);
        time = (EditText) findViewById(R.id.et_notification_time);
        back_arrow = (ImageView) findViewById(R.id.back_arrow_notification);
        btnadd = (Button) findViewById(R.id.next_btn_notification);
        lv_Norification = (ListView) findViewById(R.id.listView_Notifications);
        layout_Notification = (LinearLayout) findViewById(R.id.linear_Notification);
        myNot.myNotList = new ArrayList<>();
        dates.setOnClickListener(v -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            // @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(List__Notifications.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (view, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM" + "\n yyyy");
                cal1.set(year, month, date);
                currentYear = year;
                currentMonth = month;
                currentDate = date;
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);

            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });


        time.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(Calendar.HOUR_OF_DAY);
            mMinute = calendar.get(Calendar.MINUTE);
            calendar.set(0, 0, 0, mHour, mMinute);

            TimePickerDialog timePickerDialog = new TimePickerDialog(List__Notifications.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourD, int minuteD) {
                    time.setText(String.format(Locale.getDefault(), "%02d:%02d ", hourD, minuteD, true));
                    currentHour = hourD;
                    currentMinute = minuteD;
                }
            }, 12, 0, true);

            timePickerDialog.updateTime(mHour, mMinute);
            timePickerDialog.setTitle("Select time");
            timePickerDialog.show();

        });


        adapter = new C__NotificationAdapter(List__Notifications.this, myNot);
        lv_Norification.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Notifications.this, List__Pet.class));
            finish();
        });

        EventChangeListener();
        lv_Norification.setOnItemClickListener((adapterView, view, position, l) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(List__Notifications.this);
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                editNotification(position);
                dialog.dismiss();
            });
            alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            alert.show();
        });
        btnadd.setOnClickListener(view -> {
            String n_date = dates.getText().toString();
            String n_name = name.getText().toString().trim();
            String n_time = time.getText().toString();

            if (TextUtils.isEmpty(n_date)) {
                dates.setError("This field is required");
                Toast.makeText(List__Notifications.this, "Please insert date", Toast.LENGTH_SHORT).show();
            } else {
                if (n_name.length() > 2) {
                    dbSalt = n_name.substring(0, 2);
                } else {
                    dbSalt = n_name;
                }

                dates.setText("");
                name.setText("");
                time.setText("");
                setNotification();
                layout_Notification.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
            C__Notifications n = new C__Notifications(n_date, n_name, n_time);

            db.collection("Users").document(firebaseUser.getUid()).collection("Notifications")
                    .document(dbSalt + n_date).set(n).addOnSuccessListener(unused -> {
                EventChangeListener();
                Toast.makeText(getApplicationContext(), "Notification Added", Toast.LENGTH_SHORT).show();
            });
        });
        createNotivicationChannel();


        fab.setOnClickListener(view -> {
            layout_Notification.setVisibility(View.VISIBLE);
            name.requestFocus();
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            fab.setVisibility(View.GONE);
        });
    }

    private void createNotivicationChannel() {
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


    private void setNotification() {
        Intent intent = new Intent(List__Notifications.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(List__Notifications.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar final_calendar = Calendar.getInstance();
        final_calendar.set(currentYear, currentMonth, currentDate, currentHour, currentMinute, 0);
        long startTime = final_calendar.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);

    }


    private void EventChangeListener() {
        db.collection("Users").document(firebaseUser.getUid()).collection("Notifications")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
//
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }

                    myNot.myNotList = new ArrayList<>();
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            myNot.getMyNotList().add(dc.getDocument().toObject(C__Notifications.class));
                            //Toast.makeText(getApplicationContext(), "Here ", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void editNotification(int position) {
        C__Notifications n = myNot.getMyNotList().get(position);
        String g_date = n.getDate();
        db.collection("Users").document(firebaseUser.getUid()).collection("Notifications")
                .document(g_date).delete()
                .addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                    EventChangeListener();
                }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }


}