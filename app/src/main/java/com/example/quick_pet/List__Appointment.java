package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class List__Appointment extends AppCompatActivity {

    private static final String TAG = "Appointment";
    Button btn_addAppointment;
    List<C__Appointment> appointmentList;
    CircleImageView circleImageView;
    ImageView back_arrow;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment);
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        appointmentList = C__GlobalVariable.getAppointmentsList();
        appointmentList = new ArrayList<>();
        Log.d(TAG, "onCreate" + appointmentList.toString());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();

        back_arrow = (ImageView) findViewById(R.id.back_arrow_appointment_list);
        back_arrow.setOnClickListener(view -> {
            Intent a = new Intent(getApplicationContext(), Main_menu.class);
            startActivity(a);
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(appointmentList, List__Appointment.this);
        recyclerView.setAdapter(mAdapter);

        circleImageView = (CircleImageView) findViewById(R.id.circle_Image_pet_app_list);
        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            circleImageView.setImageURI(Uri.parse(c.getImageUrl()));
            pet_name = c.getName();
        }
        EventChangeListener();

        btn_addAppointment = (Button) findViewById(R.id.btn_addAppointment);
        btn_addAppointment.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Add_Appointment.class);
            startActivity(i);
            finish();
        });
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Appointment").whereEqualTo("ida", pet_name)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        appointmentList.add(snapshot.toObject(C__Appointment.class));
                    }
                    mAdapter.notifyDataSetChanged();
                });
    }
}