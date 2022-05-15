package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class List__Vaccine extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_vaccine;
    Button btn_ok;
    C__VaccineAdapter adapter;
    C__Vaccine_MyVaccine myVaccine;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Vaccine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vaccine);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myVaccine = ((C__GlobalVariable) this.getApplication()).getMyVaccine();
        myVaccine.myVaccineList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        back_arrow = (ImageView) findViewById(R.id.back_arrowVa);
        lv_vaccine = (ListView) findViewById(R.id.listView_Vaccine);
        btn_ok = (Button) findViewById(R.id.btn_list_vaccine);

        for (C__CurrentPet c : myCurrentPet.getMyCurrentPet()) {
            pet_name = c.getName();
        }

        adapter = new C__VaccineAdapter(List__Vaccine.this, myVaccine);
        lv_vaccine.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle incominMessages = getIntent().getExtras();
        if (incominMessages != null) {
            String V_name = incominMessages.getString("name");
            String V_date = incominMessages.getString("date");
            String V_vetName = incominMessages.getString("vetName");
            int positionEdited = incominMessages.getInt("edit");
        }
        lv_vaccine.setOnItemClickListener((adapterView, view, position, l) -> editVaccine(position));
        EventChangeListener();

        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Vaccine.this, Add_Vaccine.class));
            finish();
        });
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Vaccine.this, Main_menu.class));
            finish();
        });
    }

    private void EventChangeListener() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Vaccines").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    myVaccine.getMyVaccineList().add(snapshot.toObject(C__Vaccine.class));

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void editVaccine(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Vaccine.class);
        C__Vaccine v = myVaccine.getMyVaccineList().get(position);
        i.putExtra("name", v.getVac_name());
        i.putExtra("date", v.getVac_date());
        i.putExtra("vetName", v.getVac_vetName());
        i.putExtra("edit", position);
        startActivity(i);

    }
}