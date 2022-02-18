package com.example.quick_pet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class Add_pet extends AppCompatActivity {
    private static final String TAG = "Pet";
    EditText name, dateTxt;
    ImageView calendar, back_arrow;
    Spinner type, gender, colour, intact;
    Button nextbtn;
    CircleImageView circleImageView;
    public static final int IMAGE_CODE = 1;
    Uri imageUri, imageUri2;
    AutoCompleteTextView editT;
    C__PhotoGallery myApplication = (C__PhotoGallery) this.getApplication();

    List<Uri> uriList;
    List<C__Pet> petList;

    Boolean press = false;


    private int mDate, mMonth, mYear;

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

        name = (EditText) findViewById(R.id.editName);
        dateTxt = (EditText) findViewById(R.id.birthinput);

        back_arrow = (ImageView)findViewById(R.id.back_arrow_addPet);
        back_arrow.setOnClickListener(view -> finish());

        petList = myApplication.getPetList();

        type = (Spinner) findViewById(R.id.spinnerType);
        gender = (Spinner) findViewById(R.id.spinnerGender);
        colour = (Spinner) findViewById(R.id.spinnerColour);
        intact = (Spinner) findViewById(R.id.spinnerIntact);

        imageUri2 = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.pata))
                .appendPath(getResources().getResourceTypeName(R.drawable.pata))
                .appendPath(getResources().getResourceEntryName(R.drawable.pata))
                .build();

        circleImageView = (CircleImageView) findViewById(R.id.circleImageCamera);
        circleImageView.setOnClickListener(view -> {
            press = true;
            openimageform();
        });
        // -- next button code to navigate
        nextbtn = (Button) findViewById(R.id.next_btn);
        nextbtn.setOnClickListener(v -> {
            startActivity(new Intent(Add_pet.this, FirstActivity.class));
            String Stype = type.getSelectedItem().toString();
            String Sgender = gender.getSelectedItem().toString();
            String Scolour = colour.getSelectedItem().toString();
            String Sintact = intact.getSelectedItem().toString();
            if(TextUtils.isEmpty(name.getText().toString())){
                name.setText("Max");
            }
            if(TextUtils.isEmpty(Stype)){
                Stype = "Not Defined";
            }
            if(TextUtils.isEmpty(Sgender)){
                Sgender = "Not Defined";
            }
            if(TextUtils.isEmpty(Scolour)){
                Scolour = "Not Defined";

            }
            if(TextUtils.isEmpty(Sintact)){
                Sintact = "Not Defined";
            }
            if(press == false){
                uriList = ((C__PhotoGallery) this.getApplication()).getUriList();
                uriList.add(imageUri2);
            }

            C__Pet newpet = new C__Pet(name.getText().toString(), Stype, Sgender, editT.toString(), dateTxt.toString(), Scolour, Sintact, imageUri2.toString());


        });

        editT = findViewById(R.id.breed_input);
        // -- to open the correct array of names from two sources depending of the select item
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pet_Type);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = pet_Type[position];
                if(position == 1) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_list_item_1, Dog_breed);
                    editT.setAdapter(adapter);
                }
                if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_list_item_1, Cat_breed);
                    editT.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //-- date picker from a calendar and set to with the format dd-mmm-yyyy
        calendar = (ImageView) findViewById(R.id.calendar);
        calendar.setOnClickListener(v -> {
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
            datePickerDialog.updateDate(mYear,mMonth, mDate);
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
                uriList = ((C__PhotoGallery) this.getApplication()).getUriList();
                uriList.add(imageUri);
            }
        } catch (Exception e) {
            Toast.makeText(Add_pet.this, "ERROR" + e, Toast.LENGTH_SHORT).show();
        }

    }

}