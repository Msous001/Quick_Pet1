package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Calendar;

public class Add_pet extends AppCompatActivity {
    EditText name, dateTxt;
    ImageView addImage, calendar;
    Spinner type, gender, colour, intact;
    Button nextbtn;
    private int mDate, mMonth, mYear;


    private static final String[] Dog_breed = new String []{"Affenpinscher", "Afghan Hound",
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
            "White Swiss Shepherd", "Xoloitzcuintle", "Yorkshire Terrier"

    };
    private static final String[] Cat_breed = new String []{"Abyssinian", "Balinese", "Bengal",
            "Birman", "British Shorthair", "Burmese", "Burmilla", "Chinchilla", "Cornish Rex",
            "Devon Rex", "Moggie", "Exotic Shorthair", "Japanese Bobtail", "Korat", "Maine Coon",
            "Manx", "Norwegian Forest", "Ocicat", "Oriental Shorthair", "Persian", "Ragdoll",
            "Russian Blue", "Scottish Fold", "Siamese", "Siberian Forest Cat", "Singapura", "Snowshoe",
            "Somali", "Sphynx", "Tiffanie", "Tonkinesse", "Turkish Van"
    };
    private static  final String[] pet_Type = new String[]{"","Dog", "Cat"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        name = (EditText) findViewById(R.id.editName);
        dateTxt = (EditText) findViewById(R.id.birthinput);

        addImage = (ImageView) findViewById(R.id.addImage);
        calendar = (ImageView) findViewById(R.id.calendar);

        type = (Spinner) findViewById(R.id.spinnerType);
        gender = (Spinner) findViewById(R.id.spinnerGender);
        colour = (Spinner) findViewById(R.id.spinnerColour);
        intact = (Spinner) findViewById(R.id.spinnerIntact);

        nextbtn = (Button) findViewById(R.id.next_btn);

        AutoCompleteTextView editT = findViewById(R.id.breed_input);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pet_Type);
        type.setAdapter(typeAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = pet_Type[position];
                if(position == 0){
                }
                if(position == 1){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_list_item_1, Dog_breed);
                    editT.setAdapter(adapter);
                }
                if(position == 2){
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Add_pet.this, android.R.layout.simple_list_item_1, Cat_breed);
                    editT.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mDate = cal.get(Calendar.DATE);
                mMonth = cal.get(Calendar.MONTH);
                mYear = cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_pet.this,
                        android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        dateTxt.setText(date+"-"+month+"-"+year);
                    }

                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

    }
}