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
            mProgressBar.setVisibility(View.VISIBLE);
            if (uriPhotoList != null) {
                // store the image address
                StorageReference ref = storageReference.child("Photo-" + UUID.randomUUID()
                        .toString() + "." + getFileExtension(uriPhotoList));
                ref.putFile(uriPhotoList).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl()
                        .addOnSuccessListener(uri -> {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
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


//    private FirebaseStorage storage;
//    private StorageReference storageReference;
//    private DatabaseReference RootRef;
//    // private DatabaseReference mDatabase;
//    FirebaseFirestore db;
//    //    FirebaseStorage storage;
////    StorageReference storageReference;
//    FirebaseAuth fAuth = FirebaseAuth.getInstance();
//    FirebaseUser firebaseUser = fAuth.getCurrentUser();
//    // private DatabaseReference mDatabase;
//    //public static final String STORAGE_PATH_UPLOADS = "Photos/";
//    //public static final String DATABASE_PATH_UPLOADS = "Photos";

//        StorageReference listRef = storageReference.child(firebaseUser.getUid()).child("Photos");
//        storageReference.listAll().addOnSuccessListener(listResult -> {
//            for (StorageReference file : listResult.getItems()) {
//                file.getDownloadUrl().addOnSuccessListener(uri -> {
//                    //Picasso.get().load().into(imageView);
//                    Log.i("Images", String.valueOf(file));
//                    C__Photos c = new C__Photos(String.valueOf(file));
//                    myPhotos.getMyPhotoList().add(c);
//                    adapter.notifyDataSetChanged();
//
//                });
//                adapter.notifyDataSetChanged();
//            }
//        });

//        storageReference = FirebaseStorage.getInstance().getReference()
//                .child("Users").child(firebaseUser.getUid()).child("Photos");
//        mDatabase = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference()
//                .child("Users").child(firebaseUser.getUid()).child("Photos");

//        db = FirebaseFirestore.getInstance();
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReferenceFromUrl("gs://quick-pet.appspot.com");


//            StorageReference filePath = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uriPhotoList));
//            filePath.putFile(uriPhotoList).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                }
//            });
//            StorageReference sRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uriPhotoList));
//            sRef.putFile(uriPhotoList).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getApplicationContext(), "Try Hard", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                    mProgressBar.setProgress((int) progress);
//                }
//            });
//        listRef.listAll().addOnSuccessListener(listResult -> {
//                    for (StorageReference file : listResult.getItems()) {
//                        file.getDownloadUrl().addOnSuccessListener(uri -> {
//                            C__Photos c = new C__Photos(String.valueOf(file));
//                            myPhotos.getMyPhotoList().add(c);
//                            adapter.notifyDataSetChanged();
//                        });
//                    }


//myPhotos.myPhotoList = new ArrayList<>();
//mDatabase = FirebaseDatabase.getInstance().getReference(gs://quick-pet.appspot.com/Users/7DvZF9miNMZV6Ro0eOQsOuDAC683/Photos/);
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnap : snapshot.getChildren()) {
//                    C__Photos c = postSnap.getValue(C__Photos.class);
//                    myPhotos.getMyPhotoList().add(c);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        StorageReference listRef =storageReference.child(firebaseUser.getUid()).child("Photos");
//        listRef.listAll().addOnSuccessListener(listResult -> {
//            for (StorageReference file : listResult.getItems()) {
//                file.getDownloadUrl().addOnSuccessListener(uri -> {
//                    C__Photos c = new C__Photos(String.valueOf(file));
//                    myPhotos.getMyPhotoList().add(c);
//                    adapter.notifyDataSetChanged();
//                });
//            }
//        });-------------------------------last
//        mDatabase = FirebaseDatabase.getInstance().getReference()
//                .child(firebaseUser.getUid()).child("Photos");
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot postSnap : snapshot.getChildren()){
//                    C__Photos c = postSnap.getValue(C__Photos.class);
//                    myPhotos.getMyPhotoList().add(c);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//Connecting to the database
//
//        db.collection("Users").document(firebaseUser.getUid()).collection("Photos")
//                .addSnapshotListener((value, error) -> {
//                    if (error != null) {
////
//                        Log.e("Firestore error", error.getMessage());
//                        return;
//                    }
//                    Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();
//
//                    for (DocumentChange dc : value.getDocumentChanges()) {
//                        if (dc.getType() == DocumentChange.Type.ADDED) {
//                            myPhotos.getMyPhotoList().add(dc.getDocument().toObject(C__Photos.class));
//                            Toast.makeText(getApplicationContext(), "Here ", Toast.LENGTH_SHORT).show();
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//    }


//
//    private void uploadPhoto() {
//        final String randomKey = UUID.randomUUID().toString();
//        StorageReference newRef = storageRef.child(firebaseUser.getUid()).child("images/" + randomKey + "." + getFileExtension(uriPhotoList));
//        mUploadTask = newRef.putFile(uriPhotoList)
//                .addOnSuccessListener(taskSnapshot -> {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgressBar.setProgress(0);
//                        }
//                    }, 500);
//                    Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_SHORT).show();
//                    C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
//                    //C__Photos c = new C__Photos(taskSnapshot.getTask().toString());
//                    //String uploadId = mDatabaseRef.push().getKey();
//                    //mDatabaseRef.child(uploadId).setValue(c);
//                    myPhotos.getMyPhotoList().add(c);
//                    adapter.notifyDataSetChanged();
//                }).addOnFailureListener(e -> {
//                    Log.e(TAG, e.getMessage());
//                    Toast.makeText(getApplicationContext(), "Failed Upload Image", Toast.LENGTH_SHORT).show();
//        }).addOnProgressListener(snapshot -> {
//            double progress = (100.0 + snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
//            mProgressBar.setProgress((int) progress);
//        });
//    }
//    private void downloadPhoto(){
//        StorageReference listRef =storageReference.child(firebaseUser.getUid()).child("Photos");
//        listRef.listAll().addOnSuccessListener(listResult -> {
//            for (StorageReference file : listResult.getItems()) {
//                file.getDownloadUrl().addOnSuccessListener(uri -> {
//                    C__Photos c = new C__Photos(String.valueOf(file));
//                    myPhotos.getMyPhotoList().add(c);
//                    adapter.notifyDataSetChanged();
//                });
//            }
//        })

//        StorageReference newRef = storageRef.child(firebaseUser.getUid()).child("images");
//        newRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//            }
//        });
//    }

//            sRef.child("URL").putFile(uriPhotoList).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                            db.collection("Users").document(firebaseUser.getUid()).collection("Photos")
//                                    .document("URL").set(uri).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(getApplicationContext(),"Yessssss", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    });
//                }
//            });

//
//C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
// mDatabase.setValue(path);
//                    String uploadId = mDatabase.push().getKey();
//                    mDatabase.child(uploadId).setValue(c);

//                  Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

//                    sRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            String path = task.getResult().toString();
//                            Log.i("URL" , path);
//                            C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
//                            db.collection("Users").document(firebaseUser.getUid()).collection("Photos")
//                                    .document("URL").set(path)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(getApplicationContext(),"Yessssss", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
//                    });
// -----last
//            StorageReference ref = storageReference.child(firebaseUser.getUid()).child("Photos/" + UUID.randomUUID().toString());
//
//            ref.putFile(uriPhotoList).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    Toast.makeText(List__Photos.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(List__Photos.this, "Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
//                }
//            });

//                C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
//                FirebaseAuth fAuth = FirebaseAuth.getInstance();
//                FirebaseUser firebaseUser = fAuth.getCurrentUser();
//                final String randomKey = UUID.randomUUID().toString();
//
//                db.collection("Users").document(firebaseUser.getUid()).collection("Photo").document(randomKey)
//                        .set(c).addOnSuccessListener(unused -> {
//                            Toast.makeText(List__Photos.this, "Photo Added", Toast.LENGTH_SHORT).show();
//                });
//                C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
//                myPhotos.getMyPhotoList().add(c);
//                adapter.notifyDataSetChanged();
//                if(mUploadTask != null && mUploadTask.isInProgress()){
//                    Toast.makeText(getApplicationContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
//                }else{
//                    mUploadClick();
//                }
//            }
//if(task.isSuccessful()){
//        Toast.makeText(getApplicationContext(), "Image ", Toast.LENGTH_SHORT).show();
//        String downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
//        RootRef.child("Users").child(firebaseUser.getUid()).child("Photos")
//        .setValue(downloadUrl)
//        .addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if(task.isSuccessful()){
//        Toast.makeText(List__Photos.this, "Lo lograste", Toast.LENGTH_SHORT).show();
//        }else{
//        Toast.makeText(List__Photos.this, "No lo lograste", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//        }
//        else{
//        Toast.makeText(List__Photos.this, "Error NewLock", Toast.LENGTH_SHORT).show();
//        }
//RootRef.child("Users").child(firebaseUser.getUid()).child("Photos")
//        .addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot snapshot) {
//        if(snapshot.exists()){
//        for (DataSnapshot postSnap : snapshot.getChildren()) {
//        String retrieveImage = postSnap.getValue().toString();
//        C__Photos c = new C__Photos(retrieveImage);
//        adapter.notifyDataSetChanged();
//        }
//
//        }
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//        });#
//StorageReference fileRef = mStorageRef.child(System.currentTimeMillis()
//                        + "." + getFileExtension(uriPhotoList));
//                fileRef.putFile(uriPhotoList).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        C__Photos c = new C__Photos(task.getResult().getStorage().getDownloadUrl().toString());
//                        mDatabaseRef.setValue(c);
//
//
//                    }
//                });
//            }
//   mDatabaseRef = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app/")
//                .getReference("Users/"+firebaseUser.getUid()+"/Photos");
//        mStorageRef = FirebaseStorage.getInstance().getReference("Users/"+firebaseUser.getUid()+"/Photos");
//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot postSnap: snapshot.getChildren()){
//                    C__Photos c = postSnap.getValue(C__Photos.class);
//                    myPhotos.getMyPhotoList().add(c);
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(List__Photos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });