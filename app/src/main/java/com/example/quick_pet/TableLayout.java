package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class TableLayout extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    ImageView back_arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);

        tablayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Fragment_Vet(),"Vet");
        adapter.AddFragment(new Fragment_Allergies(),"Allergies");
        adapter.AddFragment(new Fragment_health(),"Health Condition");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

        back_arrow = (ImageView) findViewById(R.id.back_arrowTL);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TableLayout.this,Main_menu_pet1.class));
            }
        });



    }
}