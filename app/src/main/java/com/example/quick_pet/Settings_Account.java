package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Settings_Account extends AppCompatActivity {

    TextView name;
    ListView lv_Pet_Account;
    ImageView back_Btn;
    C__Pet_MyPets myPets;
    C__Pet_Adapter_Account adapter_account;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    private static final String TAG = "Pet_Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);
        back_Btn = (ImageView) findViewById(R.id.back_arrow_account);
        lv_Pet_Account = (ListView) findViewById(R.id.listView_PetAccount);
        name = (TextView)findViewById(R.id.tv_email_show);
        myPets = ((C__GlobalVariable) this.getApplication()).getMyPets();
        myPets.myPetList = new ArrayList<>();
        adapter_account = new C__Pet_Adapter_Account(Settings_Account.this, myPets);
        lv_Pet_Account.setAdapter(adapter_account);
        adapter_account.notifyDataSetChanged();
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        EventChangeListener();
        lv_Pet_Account.setOnItemClickListener((adapterView, view, position, l) -> edtiPetAccount(position));
        name.setText(firebaseUser.getEmail());
        back_Btn.setOnClickListener(view -> {
            startActivity(new Intent(Settings_Account.this, Settings.class));
            finish();
        });
    }

    private void edtiPetAccount(int position) {
    }

    private void EventChangeListener() {
        db.collection("Users").document(firebaseUser.getUid()).collection("Pets")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
//
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }
                    myPets.myPetList = new ArrayList<>();
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            myPets.getMyPetList().add(dc.getDocument().toObject(C__Pet.class));
                        }
                        adapter_account.notifyDataSetChanged();
                    }
                });
    }
}