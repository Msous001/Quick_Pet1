package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class List__Fleas extends AppCompatActivity {

    Button btnadd;
    ImageView back_arrow;
    EditText dates;
    private int mDate, mMonth, mYear;
    ListView lv_fleas;
    C__Fleas_MyFleas myFleas;
    C__FleasAdapter adapter;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Fleas";
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = fAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fleas);

        myFleas = ((C__GlobalVariable) this.getApplication()).getMyFleas();
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();

        db = FirebaseFirestore.getInstance();

        dates = (EditText) findViewById(R.id.et_date_Fleas);
        dates.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(List__Fleas.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + " " + "MMM" + " " +"dd" );
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });

        back_arrow = (ImageView) findViewById(R.id.back_arrowFl);
        lv_fleas = (ListView) findViewById(R.id.listView_Fleas);
        btnadd = (Button) findViewById(R.id.btn_add_Fleas);

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }
        adapter = new C__FleasAdapter(List__Fleas.this, myFleas);
        lv_fleas.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Fleas.this, Main_menu.class));
            finish();
        });

        EventChangeListener();
        lv_fleas.setOnItemClickListener((adapterView, view, position, l) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(List__Fleas.this);
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                editFleas(position);
                dialog.dismiss();
            });
            alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            alert.show();
        });


        btnadd.setOnClickListener(view -> {
            String f_date = dates.getText().toString();
            dbSalt = f_date;
            if (TextUtils.isEmpty(f_date)) {
                dates.setError("This field is required");
                Toast.makeText(List__Fleas.this, "Please insert date", Toast.LENGTH_SHORT).show();
            } else {
                C__Fleas f = new C__Fleas(pet_name, f_date);

                db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                        .document(pet_name).collection("Fleas And Ticks").document(dbSalt)
                        .set(f).addOnSuccessListener(unused -> {
                    EventChangeListener();
                    Toast.makeText(getApplicationContext(), "Fleas Added", Toast.LENGTH_SHORT).show();
                });
                dates.setText("");
            }
        });
    }

    private void EventChangeListener() {
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Fleas And Ticks").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            myFleas.myFleasList = new ArrayList<>();
            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                myFleas.getMyFleasList().add(snapshot.toObject(C__Fleas.class));
                Collections.sort(myFleas.getMyFleasList());
                adapter.notifyDataSetChanged();
            }
            Collections.sort(myFleas.getMyFleasList());
            adapter.notifyDataSetChanged();
        });
    }

    private void editFleas(int position) {
        C__Fleas f = myFleas.getMyFleasList().get(position);
        String g_date = f.getDates();
        String separator = "/";
        String dbDates;
        int sep = g_date.lastIndexOf(separator);
        dbDates = g_date.substring(0, sep);
        dbSalt = dbDates;
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Fleas And Ticks").document(dbSalt).delete()
                .addOnCompleteListener(task -> {
                    Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                    EventChangeListener();
                }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }
}