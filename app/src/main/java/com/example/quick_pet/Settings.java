package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    Switch aSwitch;
    ImageView back_arrow;
    Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = findViewById(R.id.switch1);
        back_arrow = (ImageView)findViewById(R.id.back_arrowSettings);
        btnNotification = (Button)findViewById(R.id.btn_see_notification);
        // back button
        back_arrow.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this, Main_menu.class));
            finish();
        });
        // switch to make dark mode
        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        btnNotification.setOnClickListener(view -> {
            startActivity(new Intent(Settings.this, List__Notifications.class));
            finish();
        });
    }
}