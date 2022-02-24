package com.example.quick_pet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class List__Photos extends AppCompatActivity {

    Button btn_add;
    Uri uriPhotoList;
    GridView myGrid;
    C__Photos_MyPhotos myPhotos;
    C__PhotosAdapter adapter;
    public static final int IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        btn_add = (Button) findViewById(R.id.btn_list_Photos);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openimageform();
            }
        });
        myGrid = findViewById(R.id.gridView);
        myPhotos = ((C__GlobalVariable) this.getApplication()).getMyPhotos();
        adapter = new C__PhotosAdapter(List__Photos.this, myPhotos);
        myGrid.setAdapter(adapter);


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
        try{
            if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                uriPhotoList = data.getData();
                C__Photos c = new C__Photos(String.valueOf(uriPhotoList));
                myPhotos.getMyPhotoList().add(c);
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            Toast.makeText(List__Photos.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}