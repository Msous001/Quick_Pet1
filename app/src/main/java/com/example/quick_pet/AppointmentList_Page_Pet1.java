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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentList_Page_Pet1 extends AppCompatActivity {

    private static final String TAG = "Appointment";
    Button btn_addAppointment;
    List<Appointment_class_pet1> appointmentList;
    CircleImageView circleImageView;
    PhotoGallery myApplication = (PhotoGallery) this.getApplication();
    List<Uri> uriList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list_page_pet1);

        appointmentList = myApplication.getAppointmentsList();
        Log.d(TAG, "onCreate"+ appointmentList.toString());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(appointmentList, AppointmentList_Page_Pet1.this);
        recyclerView.setAdapter(mAdapter);

        btn_addAppointment = (Button) findViewById(R.id.btn_addAppointment);
        btn_addAppointment.setOnClickListener(view -> startActivity(new Intent(AppointmentList_Page_Pet1.this, AddNewAppointment.class)));

        uriList = ((PhotoGallery) this.getApplication()).getUriList();

        circleImageView = (CircleImageView) findViewById(R.id.circle_Image_pet_app_list);
        circleImageView.setImageURI(uriList.get(0));
    }
}