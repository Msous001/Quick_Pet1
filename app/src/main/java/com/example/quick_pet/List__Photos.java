package com.example.quick_pet;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import java.util.UUID;

public class
List__Photos extends AppCompatActivity {

    Button btn_add;
    Uri uriPhotoList;
    GridView myGrid;
    C__Photos_MyPhotos myPhotos;
    C__PhotosAdapter adapter;
    ImageView backArrow;
    public static final int IMAGE_CODE = 1;
    private static final String TAG = "Photos";
    ProgressBar mProgressBar;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseFirestore db;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = fAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        btn_add = (Button) findViewById(R.id.btn_list_Photos);
        backArrow = (ImageView) findViewById(R.id.back_arrowPhotos);
        mProgressBar = findViewById(R.id.progressBarPhoto);
        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();


        storageReference = firebaseStorage.getReference().child("Users").child(firebaseUser.getUid())
                .child("Photos");

        backArrow.setOnClickListener(view -> {
            startActivity(new Intent(List__Photos.this, List__Pet.class));
            finish();
        });

        btn_add.setOnClickListener(view -> openimageform());
        myGrid = findViewById(R.id.gridView);
        myPhotos = ((C__GlobalVariable) this.getApplication()).getMyPhotos();
        myPhotos.myPhotoList = new ArrayList<>();
        adapter = new C__PhotosAdapter(List__Photos.this, myPhotos);
        myGrid.setAdapter(adapter);
        EventChangeListener();

    }

    private void openimageform() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                uriPhotoList = data.getData();
            }
            //mProgressBar.setVisibility(View.VISIBLE);
            if (uriPhotoList != null) {
                // store the image address
                StorageReference ref = storageReference.child("Photo-" + UUID.randomUUID()
                        .toString() + "." + getFileExtension(uriPhotoList));
                ref.putFile(uriPhotoList).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl()
                        .addOnSuccessListener(uri -> {

//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgressBar.setProgress(0);
//                                }
//                            }, 500);
                            C__Photos c = new C__Photos(uri.toString());
                            db.collection("Users").document(firebaseUser.getUid())
                                    .collection("Photos")
                                    .document("Photo-" + UUID.randomUUID())
                                    .set(c)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(List__Photos.this, "Photo Added", Toast.LENGTH_SHORT).show();
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                    });

//                            Toast.makeText(List__Photos.this, "URL" + uri.toString(), Toast.LENGTH_SHORT).show();

                        }).addOnFailureListener(e -> {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(List__Photos.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        })).addOnProgressListener(snapshot -> {
//                    double progress = (100.0 + snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                    mProgressBar.setProgress((int) progress);
                });
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(List__Photos.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void EventChangeListener() {
        db.collection("Users").document(firebaseUser.getUid()).collection("Photos")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            myPhotos.getMyPhotoList().add(dc.getDocument().toObject(C__Photos.class));
                        }
                        adapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });


    }
}

