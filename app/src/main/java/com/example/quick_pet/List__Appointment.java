package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class List__Appointment extends AppCompatActivity {

    private static final String TAG = "Appointment";
    Button btn_addAppointment;
    List<C__Appointment> appointmentList;
    CircleImageView circleImageView;
    ImageView back_arrow;
    C__GlobalVariable myApplication = (C__GlobalVariable) this.getApplication();
    List<Uri> uriList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment);

        appointmentList = myApplication.getAppointmentsList();
        Log.d(TAG, "onCreate"+ appointmentList.toString());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(appointmentList, List__Appointment.this);
        recyclerView.setAdapter(mAdapter);
        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            pic = incomingMessages.getString("picture");
        }

        circleImageView = (CircleImageView) findViewById(R.id.circle_Image_pet_app_list);
        circleImageView.setImageURI(Uri.parse(pic));

        btn_addAppointment = (Button) findViewById(R.id.btn_addAppointment);
        btn_addAppointment.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Add_Appointment.class);
            i.putExtra("picture", pic);
            startActivity(i);
        });
        back_arrow = (ImageView)findViewById(R.id.back_arrow_appointment_list);
        back_arrow.setOnClickListener(view -> {
            Intent a = new Intent(List__Appointment.this, Main_menu.class);
            a.putExtra("picture", pic);
            startActivity(a);
        });

    }
}