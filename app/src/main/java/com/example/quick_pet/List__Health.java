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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class List__Health extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listHealth;
    C__HealthAdapter adapter;
    C__Health_MyHealth myHealth;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Health";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_health);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myHealth = ((C__GlobalVariable) this.getApplication()).getMyHealth();
        myHealth.myHealthList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        back_arrow = (ImageView) findViewById(R.id.back_arrowTH);
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Health.this, Main_menu.class));
            finish();
        });

       // currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }

        btn_add = (Button) findViewById(R.id.btn_list_health);
        lv_listHealth = (ListView) findViewById(R.id.listView_Health);

        adapter = new C__HealthAdapter(List__Health.this, myHealth);
        lv_listHealth.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String effect = incomingMessages.getString("effects");
            String symptom = incomingMessages.getString("symptoms");
            String medication = incomingMessages.getString("medication");
            int positionEdited = incomingMessages.getInt("edit");

//            FirebaseAuth fAuth = FirebaseAuth.getInstance();
//            FirebaseUser firebaseUser = fAuth.getCurrentUser();
//
//            C__Health h = new C__Health(pet_name, name, effect, symptom, medication);
//            if (positionEdited > -1) {
//                myHealth.getMyHealthList().remove(positionEdited);
//                //here i need to call the database to delete the item
//
//            }
//            myHealth.getMyHealthList().add(h);
//            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
//            appReference.child("Health C").push().setValue(h);
//
//            adapter.notifyDataSetChanged();
        }
        lv_listHealth.setOnItemClickListener((adapterView, view, position, l) -> editHealth(position));
        EventChangeListener();

        btn_add.setOnClickListener(view -> {
            startActivity(new Intent(List__Health.this, Add_HealthCondition.class));
            finish();
        });
    }

    private void EventChangeListener() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Health-Condition").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    myHealth.getMyHealthList().add(snapshot.toObject(C__Health.class));

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void editHealth(int position) {
        Intent i = new Intent(getApplicationContext(), Add_HealthCondition.class);
        C__Health h = myHealth.getMyHealthList().get(position);
        i.putExtra("edit", position);
        i.putExtra("name", h.getName());
        i.putExtra("effects", h.getEffects());
        i.putExtra("symptoms", h.getSymptomsName());
        i.putExtra("medication", h.getMedicationName());
        startActivity(i);
    }


}