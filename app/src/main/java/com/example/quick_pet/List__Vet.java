package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class List__Vet extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listVet;
    C__VetAdapter adapter;
    C__Vet_MyVets myVets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vet);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTL);
        back_arrow.setOnClickListener(view -> startActivity(new Intent(List__Vet.this, Main_menu.class)));

        btn_add = (Button) findViewById(R.id.btn_list_vet);
        lv_listVet = (ListView) findViewById(R.id.listView_Vet);

        myVets = ((C__PhotoGallery) this.getApplication()).getMyVets();
        adapter = new C__VetAdapter(List__Vet.this, myVets);
        lv_listVet.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String date = incomingMessages.getString("date");
            String direction = incomingMessages.getString("direction");
            int weight = incomingMessages.getInt("weight");
            int positionEdited = incomingMessages.getInt("edit");

            C__Vet c = new C__Vet(name, date, direction, weight);
            if (positionEdited > -1) {
                myVets.getMyVetsList().remove(positionEdited);
            }
            myVets.getMyVetsList().add(c);
            adapter.notifyDataSetChanged();
        }
        lv_listVet.setOnItemClickListener((adapterView, view, position, l) -> editVet(position));
        btn_add.setOnClickListener(view -> startActivity(new Intent(List__Vet.this, Add_Vet_Visit.class)));
    }

    private void editVet(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Vet_Visit.class);
        C__Vet v = myVets.getMyVetsList().get(position);
        i.putExtra("name", v.getName());
        i.putExtra("edit", position);
        i.putExtra("date", v.getDate());
        i.putExtra("direction", v.getDirection());
        i.putExtra("weight", v.getWeight());
        startActivity(i);
    }

}