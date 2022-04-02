package com.example.quick_pet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


//import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class List__Pet extends AppCompatActivity {

    Toolbar toolbar;
    Button btn_add;
    GridView myPetGrid;
    C__Pet_MyPets myPets;
    C__Pet_Adapter adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    C__CurrentPet_MyCurrentPet myCurrentPet;
    private static final String TAG = "List_Pet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pet);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data.....");
//        progressDialog.show();

        //myPetsList = new ArrayList<>();
        btn_add = (Button) findViewById(R.id.btn_list_Pet);
        myPetGrid = (GridView) findViewById(R.id.gridViewPet);
        myCurrentPet = ((C__GlobalVariable) this.getApplication()).getMyCurrentPet();
        myPets = ((C__GlobalVariable) this.getApplication()).getMyPets();
        myPets.myPetList = new ArrayList<>();

        adapter = new C__Pet_Adapter(List__Pet.this, myPets);
        myPetGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db = FirebaseFirestore.getInstance();
        EventChangeListener();
        myCurrentPet.getMyCurrentPet().clear();
        myPetGrid.setOnItemClickListener((adapterView, view, position, l) -> {
            editPet(position);
            C__CurrentPet ab = new C__CurrentPet(myPets.getMyPetList().get(position).getName(),myPets.getMyPetList().get(position).getImageUrl());
            myCurrentPet.getMyCurrentPet().add(ab);

        });
        btn_add.setOnClickListener(view -> {
            startActivity(new Intent(List__Pet.this, Add_pet.class));
            finish();
        });


        // -- toolbar code on top right corner
        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeT:
                    Intent i = new Intent(List__Pet.this, List__Pet.class);
                    startActivity(i);
                    finish();
                    return true;
                case R.id.settings:
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.photos:
                    Intent a = new Intent(getApplicationContext(), List__Photos.class);
                    startActivity(a);
                    finish();
                    return true;
                case R.id.sounds:
                    Intent s = new Intent(getApplicationContext(), List__Sounds.class);
                    startActivity(s);
                    finish();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // to close activity
                    //System.exit(0);
                    return true;
                case R.id.exit:
                    Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                    finish();
                    System.exit(0);
                    return true;
            }
            return false;
        });
    }

    private void editPet(int position) {
        Intent i = new Intent(getApplicationContext(), Main_menu.class);
        startActivity(i);
        finish();
    }

    private void EventChangeListener() {
        //Connecting to the database
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                myPets.getMyPetList().add(dc.getDocument().toObject(C__Pet.class));
                                Toast.makeText(getApplicationContext(), "Here ", Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
                        }
                    }
                });

    }


}
