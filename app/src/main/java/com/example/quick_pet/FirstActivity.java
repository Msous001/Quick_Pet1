package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


//import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class FirstActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView circleImageView;
    CircleImageView circleImagepet1;
    CircleImageView circleImagepet2;
    CircleImageView circleImagepet3;
    CircleImageView circleImagepet4;
    List<Uri> uriList;
    List<Pet> petList;
    PhotoGallery myApplication = (PhotoGallery) this.getApplication();
    private static final String TAG = "Pet";
    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        circleImageView = findViewById(R.id.circleImageView);
        circleImagepet1 = findViewById(R.id.pet1);
        circleImagepet2 = findViewById(R.id.pet2);
        circleImagepet3 = findViewById(R.id.pet3);
        circleImagepet4 = findViewById(R.id.pet4);

        imageUri = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.pata))
                .appendPath(getResources().getResourceTypeName(R.drawable.pata))
                .appendPath(getResources().getResourceEntryName(R.drawable.pata))
                .build();

        uriList = ((PhotoGallery) this.getApplication()).getUriList();
        petList = myApplication.getPetList();

        if (uriList.isEmpty()) {
                uriList.add(imageUri);

        } else {
            try {
                int plus = uriList.size();
                switch (plus) {
                    case 1:
                        circleImagepet1.setVisibility(View.VISIBLE);
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(FirstActivity.this, Main_menu_pet1.class));
                            }
                        });
                        break;
                    case 2:
                        circleImagepet1.setVisibility(View.VISIBLE);
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setVisibility(View.VISIBLE);
                        circleImagepet2.setImageURI(uriList.get(1));
                        break;
                    case 3:
                        circleImagepet1.setVisibility(View.VISIBLE);
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setVisibility(View.VISIBLE);
                        circleImagepet2.setImageURI(uriList.get(1));
                        circleImagepet3.setVisibility(View.VISIBLE);
                        circleImagepet3.setImageURI(uriList.get(2));
                        break;
                    case 4:
                        circleImagepet1.setVisibility(View.VISIBLE);
                        circleImagepet1.setImageURI(uriList.get(0));
                        circleImagepet2.setVisibility(View.VISIBLE);
                        circleImagepet2.setImageURI(uriList.get(1));
                        circleImagepet3.setVisibility(View.VISIBLE);
                        circleImagepet3.setImageURI(uriList.get(2));
                        circleImagepet4.setVisibility(View.VISIBLE);
                        circleImagepet4.setImageURI(uriList.get(3));
                        circleImageView.setVisibility(View.GONE);
                        break;

                }
            } catch (Exception e) {
                System.out.println(e);
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, Add_pet.class));
            }
        });

        // -- toolbar code on top right corner
        toolbar = (Toolbar) findViewById(R.id.materialToolbar);
        toolbar.inflateMenu(R.menu.top_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings:
                    Toast.makeText(FirstActivity.this, "test", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(FirstActivity.this, "bye", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                case R.id.exit:
                    Toast.makeText(FirstActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                    return true;
            }
            return false;
        });
    }


}
//        File file = new File("/app/src/main/res/drawable/cat_face_circle.png");
//        String abc = String.valueOf(file).replace("file://","");
//        imageUri = Uri.parse(abc);
//        imageUri= Uri.fromFile(new File( "res/drawable/cat_face_circle.png"));
//
//        uriList.add(imageUri);
//        Glide.with(this.context).load(presidentList.get(position).getImageUrl()).into(holder.iv_presPicture);
//        Glide.with(FirstActivity.this).load(p.getImageUrl()).into((circleImagepet1));
//        circleImagepet1.setImageURI(Uri.parse(p.getImageUrl()));
