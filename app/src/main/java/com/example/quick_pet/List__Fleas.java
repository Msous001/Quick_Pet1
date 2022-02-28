package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class List__Fleas extends AppCompatActivity {

    Button btnadd;
    ImageView back_arrow, calendar_app_newFleas;
    TextView dates;
    private int mDate, mMonth, mYear;
    ListView lv_fleas;
    C__Fleas_MyFleas myFleas;
    C__FleasAdapter adapter;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fleas);

        dates = (TextView) findViewById(R.id.et_date_Fleas);
        calendar_app_newFleas = (ImageView) findViewById(R.id.calendar_Fleas);
        calendar_app_newFleas.setOnClickListener(view -> {
            final Calendar cal1 = Calendar.getInstance();
            mDate = cal1.get(Calendar.DATE);
            mMonth = cal1.get(Calendar.MONTH);
            mYear = cal1.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(List__Fleas.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (datePicker, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd"+"/"+"MM"+"/"+"yyyy");
                cal1.set(year, month, date);
                String dateString = sdf.format(cal1.getTime());
                dates.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.show();
        });
        currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }

        back_arrow = (ImageView) findViewById(R.id.back_arrowFl);
        lv_fleas = (ListView) findViewById(R.id.listView_Fleas);
        btnadd = (Button) findViewById(R.id.btn_add_Fleas);

        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Fleas.this, Main_menu.class));
            finish();
        });

        myFleas = ((C__GlobalVariable) this.getApplication()).getMyFleas();
        adapter = new C__FleasAdapter(List__Fleas.this, myFleas);
        lv_fleas.setAdapter(adapter);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        btnadd.setOnClickListener(view -> {
            String f_date = dates.getText().toString();
            if(TextUtils.isEmpty(f_date)){
                Toast.makeText(List__Fleas.this, "Please insert date", Toast.LENGTH_SHORT).show();
            }
            else {
                C__Fleas f = new C__Fleas(f_date);
                myFleas.getMyFleasList().add(f);
                DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
                appReference.child("Fleas").push().setValue(f_date);


                Collections.sort(myFleas.getMyFleasList());
                adapter.notifyDataSetChanged();
                dates.setText("");
            }
        });
        lv_fleas.setOnItemClickListener((adapterView, view, position, l) -> editFleas(position));
    }

    private void editFleas(int position) {
        myFleas.getMyFleasList().remove(position);
        adapter.notifyDataSetChanged();

    }
}