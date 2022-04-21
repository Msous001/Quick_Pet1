package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

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
    ListView lv_listApp;
    C__Appointment_MyApp myApps;
    C__ApointmentAdapter adapter;
    CircleImageView circleImageView;
    ImageView back_arrow;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myApps = ((C__GlobalVariable) this.getApplication()).getMyApps();
        myApps.myAppList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        back_arrow = (ImageView) findViewById(R.id.back_arrow_appointment_list);
        back_arrow.setOnClickListener(view -> {
            Intent a = new Intent(List__Appointment.this, Main_menu.class);
            startActivity(a);
        });

        lv_listApp = (ListView)findViewById(R.id.recyclerView);
        adapter = new C__ApointmentAdapter(List__Appointment.this, myApps);
        lv_listApp.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String type = incomingMessages.getString("a_type");
            String name = incomingMessages.getString("a_name");
            String date = incomingMessages.getString("a_date");
            String time = incomingMessages.getString("a_time");
            String direction = incomingMessages.getString("a_direction");
            String reminder = incomingMessages.getString("a_reminder");
            int positionEdited = incomingMessages.getInt("edit");
        }

        circleImageView = (CircleImageView) findViewById(R.id.circle_Image_pet_app_list);
        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            circleImageView.setImageURI(Uri.parse(c.getImageUrl()));
            pet_name = c.getName();
        }

        EventChangeListener();
        lv_listApp.setOnItemClickListener((adapterView, view, position, l) -> editApp(position));

        btn_addAppointment = (Button) findViewById(R.id.btn_addAppointment);
        btn_addAppointment.setOnClickListener(view -> {
            startActivity(new Intent(List__Appointment.this, Add_Appointment.class));
            finish();
        });
    }

    private void editApp(int position) {
        Intent i = new Intent(List__Appointment.this, Add_Appointment.class);
        C__Appointment ap = myApps.getMyAppList().get(position);
        i.putExtra("edit", position);
        i.putExtra("a_type", ap.getType());
        i.putExtra("a_name", ap.getName());
        i.putExtra("a_date", ap.getDate());
        i.putExtra("a_time", ap.getTime());
        i.putExtra("a_direction", ap.getDirection());
        i.putExtra("a_reminder", ap.getReminder());
        startActivity(i);
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Appointment").whereEqualTo("ida", pet_name)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        myApps.getMyAppList().add(snapshot.toObject(C__Appointment.class));
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}