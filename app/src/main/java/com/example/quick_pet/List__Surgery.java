package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class List__Surgery extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_surgery;
    C__Surgery_MySurgeries mySurgeries;
    C__SurgeryAdapter adapter;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_surgery);

        back_arrow = (ImageView) findViewById(R.id.back_arrowSg);
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Surgery.this, Main_menu.class));
            finish();
        });
        //currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }

        btn_add = (Button) findViewById(R.id.btn_list_surgery);
        lv_surgery = (ListView) findViewById(R.id.listView_Surgery);

        mySurgeries = ((C__GlobalVariable) this.getApplication()).getMySurgeries();
        mySurgeries.mySurgeryList = new ArrayList<>();
        adapter = new C__SurgeryAdapter(List__Surgery.this, mySurgeries);
        lv_surgery.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String med1 = incomingMessages.getString("med1");
            String med2 = incomingMessages.getString("med2");
            String note = incomingMessages.getString("note");
            int positionEdited = incomingMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();
            C__Surgery s = new C__Surgery(pet_name, name, date, med1, med2, note);
            if(positionEdited >-1){
                mySurgeries.getMySurgeryList().remove(positionEdited);
                //here i need to call the database to delete the item

            }
            mySurgeries.getMySurgeryList().add(s);
            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Surgery").push().setValue(s);
            Collections.sort(mySurgeries.getMySurgeryList());
            adapter.notifyDataSetChanged();
        }
        lv_surgery.setOnItemClickListener((adapterView, view, position, l) -> editSurgery(position));
        btn_add.setOnClickListener(view -> {
            startActivity(new Intent(List__Surgery.this, Add_Surgery.class));
            finish();
        });
    }

    private void editSurgery(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Surgery.class);
        C__Surgery s = mySurgeries.getMySurgeryList().get(position);
        i.putExtra("edit", position);
        i.putExtra("name", s.getName());
        i.putExtra("date",s.getDate());
        i.putExtra("med1", s.getAddmed1());
        i.putExtra("med2", s.getAddmed2());
        i.putExtra("note", s.getAddnote());
        startActivity(i);

    }
}