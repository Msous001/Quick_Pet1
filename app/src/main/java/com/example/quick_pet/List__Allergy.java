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
import java.util.Collections;
import java.util.List;

public class List__Allergy extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_allergy;
    Button btn_ok;
    C__AllergyAdapter adapter;
    C__Allergy_MyAllergies myAllergies;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    FirebaseFirestore db;
    private static String  pet_name;
    private static String dbSalt;
    private static final String TAG = "List_Allergy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_allergy);

        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myAllergies= ((C__GlobalVariable) this.getApplication()).getMyAllergies();
        myAllergies.myAllergyList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        btn_ok = (Button) findViewById(R.id.btn_list_allergy);
        lv_allergy = (ListView) findViewById(R.id.listView_Allergy);
        adapter = new C__AllergyAdapter(List__Allergy.this, myAllergies);
        lv_allergy.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        for(C__CurrentPet c : myCurrentPet.getMyCurrentPet()){
            pet_name = c.getName();
        }

        back_arrow = (ImageView) findViewById(R.id.back_arrowTLA);




        Bundle incominMessages = getIntent().getExtras();
        if(incominMessages != null){
            String A_name = incominMessages.getString("name");
            String A_date = incominMessages.getString("date");
            String A_symptom = incominMessages.getString("symptom");
            String A_medication = incominMessages.getString("medication");
            int positionEdited = incominMessages.getInt("edit");

//            FirebaseAuth fAuth = FirebaseAuth.getInstance();
//            FirebaseUser firebaseUser = fAuth.getCurrentUser();
//            C__Allergy a = new C__Allergy(pet_name, A_name, A_date, A_symptom, A_medication);
//            if(positionEdited >-1){
//                myAllergies.getMyAllergyList().remove(positionEdited);
//                //here i need to call the database to delete the item
//            }
//            myAllergies.getMyAllergyList().add(a);


//            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
////            appReference.child("Allergy").push().setValue(a);
//            appReference.child("Allergy").child("Allergy "+ A_name).setValue(a);
//
//            Collections.sort(myAllergies.getMyAllergyList());
//            adapter.notifyDataSetChanged();
//        }
            if ( A_name.length() > 2){
                dbSalt = A_name.substring(0,2);
            }else{
                dbSalt = A_name;
            }
            String separator = " ";
            String dbDates;
            int sep = A_date.lastIndexOf(separator);
            dbDates= A_date.substring(0,sep);
            dbSalt = dbSalt + dbDates;


        }
        lv_allergy.setOnItemClickListener((adapterView, view, position, l) -> editAllergy(position));
        EventChangeListener();

        btn_ok.setOnClickListener(view -> {
            startActivity(new Intent(List__Allergy.this, Add_Allergy.class));
            finish();
        });
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Allergy.this, Main_menu.class));
            finish();
        });
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .document(pet_name).collection("Allergy").whereEqualTo("id", pet_name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    myAllergies.getMyAllergyList().add(snapshot.toObject(C__Allergy.class));

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void editAllergy(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Allergy.class);
        C__Allergy a = myAllergies.getMyAllergyList().get(position);
        i.putExtra("name", a.getName());
        i.putExtra("edit", position);
        i.putExtra("date", a.getDate());
        i.putExtra("symptom", a.getSymptomsName());
        i.putExtra("medication", a.getMedicationName());
        startActivity(i);
    }
}