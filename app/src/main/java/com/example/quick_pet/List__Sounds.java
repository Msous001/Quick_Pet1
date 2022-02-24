package com.example.quick_pet;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class List__Sounds extends AppCompatActivity {

    ImageView back_arrow;
    ListView lv_sound;
    C__Sounds_MySounds mySounds;
    C__SoundsAdapter adapter;
    MediaPlayer sound_player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sounds);

        back_arrow = (ImageView) findViewById(R.id.back_arrowSound);
        back_arrow.setOnClickListener(view -> finish());

        lv_sound = (ListView)findViewById(R.id.listView_Sound);

        mySounds = ((C__GlobalVariable) this.getApplication()).getMySounds();
        adapter = new C__SoundsAdapter(List__Sounds.this, mySounds);
        lv_sound.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lv_sound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                C__Sounds s = mySounds.getMySoundsList().get(position);
                if(sound_player != null){
                    if(sound_player.isPlaying()){
                        sound_player.stop();
                        sound_player.reset();
                        s.setPlaying(false);
                    }
                }
                try{
                    sound_player = MediaPlayer.create(List__Sounds.this, s.getId());
                    if(sound_player.isPlaying()){
                        sound_player.stop();
                        sound_player.reset();
                        s.setPlaying(false);
                    }
                    else{
                        sound_player.start();
                        s.setPlaying(true);
                    }

                }catch(Exception e){
                    Log.e("Exception", e.getMessage());
                }

            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sound_player.isPlaying()) {
            sound_player.stop();
            sound_player.reset();
        }
    }
}