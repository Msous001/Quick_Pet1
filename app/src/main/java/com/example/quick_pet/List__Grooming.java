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

import java.util.Collections;
import java.util.List;

public class List__Grooming extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listGrooming;
    C__Grooming_MyGrooming myGrooming;
    C__GroomingAdapter adapter;
    List<C__CurrentPet> currentPetList;
    private static String  pet_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grooming);

        back_arrow = ((ImageView) findViewById(R.id.back_arrowGr));
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Grooming.this, Main_menu.class));
            finish();
        });

        btn_add = ((Button) findViewById(R.id.btn_list_grooming));
        lv_listGrooming = ((ListView) findViewById(R.id.listView_Grooming));

        currentPetList = C__GlobalVariable.getCurrentPets();

        for(C__CurrentPet c : currentPetList){
            pet_name = c.getName();
        }
        myGrooming= ((C__GlobalVariable) this.getApplication()).getMyGrooming();
        adapter = new C__GroomingAdapter(List__Grooming.this, myGrooming);
        lv_listGrooming.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String G_place = incomingMessages.getString("place");
            String G_dates = incomingMessages.getString("date");
            String G_time = incomingMessages.getString("time");
            String G_direction = incomingMessages.getString("direction");
            int positionEdited = incomingMessages.getInt("edit");

            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();

            C__Grooming g = new C__Grooming(G_place, G_dates, G_time, G_direction);
            if(positionEdited >-1){
                myGrooming.getMyGroomingList().remove(positionEdited);
                //here i need to call the database to delete the item

            }
            myGrooming.getMyGroomingList().add(g);

            DatabaseReference appReference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet "+ pet_name);
            appReference.child("Grooming").push().setValue(g);

            Collections.sort(myGrooming.myGroomingList);
            adapter.notifyDataSetChanged();
            
        }
        lv_listGrooming.setOnItemClickListener((adapterView, view, position, l) -> editGrooming(position));
        btn_add.setOnClickListener(view -> startActivity(new Intent(List__Grooming.this, Add_Grooming.class)));
    }

    private void editGrooming(int position) {
        Intent i = new Intent(getApplicationContext(),Add_Grooming.class);
        C__Grooming g = myGrooming.getMyGroomingList().get(position);
        i.putExtra("place", g.getPlace());
        i.putExtra("date", g.getDate());
        i.putExtra("time", g.getTime());
        i.putExtra("direction", g.getDirection());
        i.putExtra("edit", position);
        startActivity(i);

    }
}