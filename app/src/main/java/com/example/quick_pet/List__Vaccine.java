package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class List__Vaccine extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_vaccine;
    Button btn_ok;
    C__VaccineAdapter adapter;
    C__Vaccine_MyVaccine myVaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vaccine);

        back_arrow = (ImageView) findViewById(R.id.back_arrowVa);
        lv_vaccine = (ListView) findViewById(R.id.listView_Vaccine);
        btn_ok = (Button) findViewById(R.id.btn_list_vaccine);

        myVaccine = ((C__PhotoGallery) this.getApplication()).getMyVaccine();
        adapter = new C__VaccineAdapter(List__Vaccine.this, myVaccine);
        lv_vaccine.setAdapter(adapter);

        Bundle incominMessages = getIntent().getExtras();
        if(incominMessages != null){
            String V_name = incominMessages.getString("name");
            String V_date = incominMessages.getString("date");
            String V_vetName = incominMessages.getString("vetName");
            int positionEdited = incominMessages.getInt("edit");

            C__Vaccine Va = new C__Vaccine(V_name, V_date, V_vetName);
            if(positionEdited >-1){
                myVaccine.getMyVaccineList().remove(positionEdited);
            }
            myVaccine.getMyVaccineList().add(Va);
            adapter.notifyDataSetChanged();
        }
        lv_vaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editVaccine(position);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(List__Vaccine.this, Add_Vaccine.class));
            }
        });
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List__Vaccine.this, Main_menu.class));
            }
        });
    }

    private void editVaccine(int position) {
        Intent i = new Intent(getApplicationContext(), Add_Vaccine.class);
        C__Vaccine v = myVaccine.getMyVaccineList().get(position);
        i.putExtra("name", v.getVac_name());
        i.putExtra("date", v.getVac_date());
        i.putExtra("vetName", v.getVac_vetName());
        i.putExtra("edit", position);
        startActivity(i);

    }
}