package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class List__Grooming extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_add;
    ListView lv_listGrooming;
    C__Grooming_MyGrooming myGrooming;
    C__GroomingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grooming);

        back_arrow = ((ImageView) findViewById(R.id.back_arrowGr));
        back_arrow.setOnClickListener(view -> startActivity(new Intent(List__Grooming.this, Main_menu.class)));
        btn_add = ((Button) findViewById(R.id.btn_list_grooming));
        lv_listGrooming = ((ListView) findViewById(R.id.listView_Grooming));

        myGrooming= ((C__PhotoGallery) this.getApplication()).getMyGrooming();
        adapter = new C__GroomingAdapter(List__Grooming.this, myGrooming);
        lv_listGrooming.setAdapter(adapter);

        Bundle incomingMessages = getIntent().getExtras();
        if(incomingMessages != null){
            String G_place = incomingMessages.getString("place");
            String G_dates = incomingMessages.getString("date");
            String G_time = incomingMessages.getString("time");
            String G_direction = incomingMessages.getString("direction");
            int positionEdited = incomingMessages.getInt("edit");
            
            C__Grooming g = new C__Grooming(G_place, G_dates, G_time, G_direction);
            if(positionEdited >-1){
                myGrooming.getMyGroomingList().remove(positionEdited);
            }
            myGrooming.getMyGroomingList().add(g);
            adapter.notifyDataSetChanged();
            
        }
        lv_listGrooming.setOnItemClickListener((adapterView, view, position, l) -> editGrooming(position));
        btn_add.setOnClickListener(view -> startActivity(new Intent(List__Grooming.this, Add_Grooming.class)));
    }

    private void editGrooming(int position) {
        Intent i = new Intent(getApplicationContext(),Add_Grooming.class);
        C__Grooming g = myGrooming.getMyGroomingList().get(position);
        i.putExtra("place", g.getPlace());
        i.putExtra("date", g.getDate());
        i.putExtra("time", g.getTime());
        i.putExtra("direction", g.getDirection());
        i.putExtra("edit", position);
        startActivity(i);

    }
}