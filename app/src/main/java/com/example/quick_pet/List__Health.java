package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class List__Health extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listHealth;
    C__HealthAdapter adapter;
    C__Health_MyHealth myHealth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_health);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTH);
        back_arrow.setOnClickListener(view -> startActivity(new Intent(List__Health.this, Main_menu.class)));

        btn_add = (Button) findViewById(R.id.btn_list_health);
        lv_listHealth = (ListView) findViewById(R.id.listView_Health);

        myHealth = ((C__GlobalVariable) this.getApplication()).getMyHealth();
        adapter = new C__HealthAdapter(List__Health.this, myHealth);
        lv_listHealth.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if (incomingMessages != null) {
            String name = incomingMessages.getString("name");
            String effect = incomingMessages.getString("effects");
            String symptom = incomingMessages.getString("symptoms");
            String medication = incomingMessages.getString("medication");
            int positionEdited = incomingMessages.getInt("edit");

            C__Health h = new C__Health(name, effect, symptom, medication);
            if (positionEdited > -1) {
                myHealth.getMyHealthList().remove(positionEdited);
            }
            myHealth.getMyHealthList().add(h);
            adapter.notifyDataSetChanged();
        }
        lv_listHealth.setOnItemClickListener((adapterView, view, position, l) -> editHealth(position));
        btn_add.setOnClickListener(view -> startActivity(new Intent(List__Health.this, Add_HealthCondition.class)));
    }

    private void editHealth(int position) {
        Intent i = new Intent(getApplicationContext(), Add_HealthCondition.class);
        C__Health h = myHealth.getMyHealthList().get(position);
        i.putExtra("edit", position);
        i.putExtra("name", h.getName());
        i.putExtra("effects", h.getEffects());
        i.putExtra("symptoms", h.getSymptomsName());
        i.putExtra("medication", h.getMedicationName());
        startActivity(i);
    }


}