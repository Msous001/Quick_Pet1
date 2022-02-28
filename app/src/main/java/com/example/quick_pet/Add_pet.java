package com.example.quick_pet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class Add_pet extends AppCompatActivity {


    EditText name, dateTxt;
    ImageView calendar, back_arrow;
    Spinner type, gender, colour, intact;
    Button nextbtn;
    CircleImageView circleImageView;
    public static final int IMAGE_CODE = 1;
    Uri imageUri, imageUriDog, imageUriCat, imageUriSelected;
    AutoCompleteTextView editT;
    private static Boolean press = true;
    private int mDate, mMonth, mYear;
    C__Pet_MyPets myPets;
    TextView selection;
    List<C__CurrentPet> currentPet;



    private static final String[] Dog_breed = new String[]{"Affenpinscher", "Afghan Hound",
            "Airedale Terrier", "Akita", "Alaskan Malamute", "Anatolian Shepherd", "Australian Cattle",
            "Australian Shepherd", "Australian Silky", "Australian Terrier", "Azawakh", "Barbet",
            "Basenji", "Basset Bleu De Gascogne", "Basset Fauve De Bretagne", "Basset",
            "Bavarian Mountain Hound", "Beagle", "Bearded Collie", "Beauceron", "Bedlington Terrier",
            "Belgian Shepherd", "Bergamasco", "Bernese Mountain", "Bichon Frise", "Black Tan Coonhound",
            "Bloodhound", "Bolognese", "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier",
            "Bouvier Des Flandres", "Boxer", "Bracco Italiano", "Braque Auvergne", "Briard", "Brittany",
            "Bull Terrier", "Bulldog", "Bullmastiff", "Cairn Terrier", "Canaan", "Canadian Eskimo Dog",
            "Catalan Sheepdog", "Cavalier Spaniel", "Cesky Terrier", "Chihuahua", "Chinese Crested",
            "Chow Chow", "Cirneco Dell", "Collie", "Coton de Tulear", "Dachshund", "Dalmatian",
            "Dandie Dinmont", "Deerhound", "Dobermann", "Dogue de Bordeaux", "English Setter",
            "English Toy Terrier", "Entlebucher Mountain", "Estrela Mountain", "Eurasier",
            "Finnish Lapphund", "Finnish Spitz", "Fox Terrier", "Foxhound", "French Bulldog",
            "German Longhaired", "German Pinscher", "German Shepherd", "German Shorthaired",
            "German Spitz", "German Wirehaired", "Gian Schnauzer", "Glen Of Imaal", "Gordon Setter",
            "Grand Bleu De Gascogne", "Great Dane", "Great Swiss Mountain", "Greenland", "Greyhound",
            "Griffon Bruxellois", "Griffon Fauve De Bretagne", "Hamiltonstovare", "Harrier",
            "Havanese", "Hovawart", "Hungarian Kuvasz", "Hungarian Puli", "Hungarian Pumi",
            "Hungarian Vizsla", "Hungarian Wire Haired Vizsla", "Ibizan Hound", "Irish Red White Setter",
            "Irish Setter", "Irish Terrier", "Irish Wolfhound", "Italian Greyhound", "Italian Spinone",
            "Jack Russell Terrier", "Japanese Akita Inu", "Japanese Chin", "Japanese Shiba Inu",
            "Japanese Spitz", "Keeshond", "Kerry Blue Terrier", "King Charles Spaniel", "Komondor",
            "Kooikerhondje", "Korean Jindo", "Korthals Griffon", "Lagotto Romagnolo", "Lakeland Terrier",
            "Lancashire Heeler", "Large Munsterlander", "Leonberger", "Lhasa Apso", "Lowchen", "Maltese",
            "Manchester Terrier", "Maremma Sheepdog", "Mastiff", "Pinscher", "Schnauzer",
            "Neapolitan Mastiff", "Newfoundland", "Norfolk Terrier", "Norwegian Buhund",
            "Norwegian Elkhound", "Norwich Terrier", "Old English Sheepdog", "Otterhound", "Papillon",
            "Parson Russell Terrier", "Pekingese", "Pharaoh Hound", "Picardy Sheepdog", "Pointer",
            "Polish Lowland Sheepdog", "Pomeranian", "Poodle", "Podengo", "Pointer", "Pug", "Pyrenean",
            "Retriever", "Retriever Golden", "Retriever Labrador", "Rhodesian", "Rottweiler",
            "Russian Black Terrier", "Russian Toy", "Saluki", "Samoyed", "Schipperke", "Schnauzer",
            "Scottish Terrier", "Sealyham Terrier", "Shar Pei", "Shetland Sheepdog", "Shih Tzu",
            "Siberian Husky", "Skye Terrier", "Sloughi", "Slovakian Rough Haired", "Small Munsterlander",
            "Soft Coated Wheaten Terrier", "Spaniel", "Spaniel Cocker", "Spaniel Water", "Spaniel Clumber",
            "Spaniel Springer", "Spanish Water", "St Bernard", "Staffordshire Bull Terrier",
            "Swedish Lapphund", "Swedish Vallhund", "Tibetan", "Tibetan Mastiff", "Tibetan Spaniel",
            "Turkish Kangal", "Weimaraner", "Welsh Corgi", "Welsh Terrier", "West Highland", "Whippet",
            "White Swiss Shepherd", "Xoloitzcuintle", "Yorkshire Terrier", "Not Defined"

    };
    private static final String[] Cat_breed = new String[]{"Abyssinian", "Balinese", "Bengal",
            "Birman", "British Shorthair", "Burmese", "Burmilla", "Chinchilla", "Cornish Rex",
            "Devon Rex", "Moggie", "Exotic Shorthair", "Japanese Bobtail", "Korat", "Maine Coon",
            "Manx", "Norwegian Forest", "Ocicat", "Oriental Shorthair", "Persian", "Ragdoll",
            "Russian Blue", "Scottish Fold", "Siamese", "Siberian Forest Cat", "Singapura", "Snowshoe",
            "Somali", "Sphynx", "Tiffanie", "Tonkinesse", "Turkish Van", "Not Defined"
    };
    private static final String[] pet_Type = new String[]{"", "Dog", "Cat"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);


        currentPet = C__GlobalVariable.getCurrentPets();

        name = (EditText) findViewById(R.id.editName);
        dateTxt = (EditText) findViewById(R.id.birthinput);

        back_arrow = (ImageView) findViewById(R.id.back_arrow_addPet);
        back_arrow.setOnClickListener(view -> startActivity(new Intent(Add_pet.this, List__Pet.class)));

        type = (Spinner) findViewById(R.id.spinnerType);
        gender = (Spinner) findViewById(R.id.spinnerGender);
        colour = (Spinner) findViewById(R.id.spinnerColour);
        intact = (Spinner) findViewById(R.id.spinnerIntact);
        selection = (TextView)findViewById(R.id.adapter);

        imageUriDog = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.dog_1))
                .appendPath(getResources().getResourceTypeName(R.drawable.dog_1))
                .appendPath(getResources().getResourceEntryName(R.drawable.dog_1))
                .build();
        imageUriCat = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.cat1))
                .appendPath(getResources().getResourceTypeName(R.drawable.cat1))
                .appendPath(getResources().getResourceEntryName(R.drawable.cat1))
                .build();

        circleImageView = (CircleImageView) findViewById(R.id.circleImageCamera);
        circleImageView.setOnClickListener(view -> {
            press = false;
            openimageform();
        });
        editT = findViewById(R.id.breed_input);
        // -- to open the correct array of names from two sources depending of the select item
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pet_Type);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = pet_Type[position];
                if (position == 1) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_spinner_dropdown_item, Dog_breed);
                    editT.setAdapter(adapter);
                    editT.getText();
                    //selection = (String) editT.getAdapter().getItem(position);
                }
                if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_spinner_dropdown_item, Cat_breed);
                    editT.setAdapter(adapter2);
                    editT.getText();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // -- next button code to navigate
        myPets = ((C__GlobalVariable) this.getApplication()).getMyPets();

        nextbtn = (Button) findViewById(R.id.next_btn);
        nextbtn.setOnClickListener(v -> {

            String Sname = name.getText().toString();
            if (TextUtils.isEmpty(Sname)) {
                Sname = "My pet";
            }
            String Stype = type.getSelectedItem().toString();
            if (TextUtils.isEmpty(Stype)) {
                Stype = "Dog";
            }
            String Sgender = gender.getSelectedItem().toString();
            String Sbreed = editT.getText().toString();
            String Sbod = dateTxt.getText().toString();
            String Scolour = colour.getSelectedItem().toString();
            String Sintact = intact.getSelectedItem().toString();

            if (TextUtils.isEmpty(Sgender)) {
                Sgender = "Not Defined";
            }
            if (TextUtils.isEmpty(Sbod)) {
                Sbod = "Not Defined";
            }
            if (TextUtils.isEmpty(Scolour)) {
                Scolour = "Not Defined";
            }
            if (TextUtils.isEmpty(Sintact)) {
                Sintact = "Not Defined";
            }
            if (imageUriSelected == null) {
                if (press) {
                    if (Stype.equals("Dog")) {
                        imageUriSelected = imageUriDog;
                    } else if (Stype.equals("Cat")) {
                        imageUriSelected = imageUriCat;
                    }
                } else {
                    imageUriSelected = imageUri;
                }
            }

            C__Pet newpet = new C__Pet(Sname, Stype, Sgender, Sbreed, Sbod, Scolour, Sintact, imageUriSelected.toString());
            myPets.getMyPetList().add(newpet);
            C__CurrentPet c = new C__CurrentPet(Sname, imageUriSelected.toString());
            currentPet.add(c);


            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = fAuth.getCurrentUser();

            DatabaseReference pet_Reference = FirebaseDatabase.getInstance("https://quick-pet-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("User").child(firebaseUser.getUid()).child("Pet " +Sname);
            pet_Reference.push().setValue(newpet);

            Intent i = new Intent(v.getContext(), List__Pet.class);
            i.putExtra("p_image", imageUriSelected);
            startActivity(i);

        });


        //-- date picker from a calendar and set to with the format dd-mmm-yyyy
        calendar = (ImageView) findViewById(R.id.calendar);
        dateTxt.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            mDate = cal.get(Calendar.DATE);
            mMonth = cal.get(Calendar.MONTH);
            mYear = cal.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(Add_pet.this,
                    android.R.style.Theme_DeviceDefault_Dialog, (view, year, month, date) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                cal.set(year, month, date);
                String dateString = sdf.format(cal.getTime());
                dateTxt.setText(dateString);
            }, mYear, mMonth, mDate);
            datePickerDialog.updateDate(mYear, mMonth, mDate);
            datePickerDialog.show();
        });
    }

    // to open gallery
    private void openimageform() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);
    }

    // to select photo from gallery

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                circleImageView.setImageURI(imageUri);
            }
        } catch (Exception e) {
            Toast.makeText(Add_pet.this, "ERROR" + e, Toast.LENGTH_SHORT).show();
        }
    }
}
