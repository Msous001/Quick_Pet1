package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class C__SoundsAdapter extends BaseAdapter {

    Activity myActivity;
    C__Sounds_MySounds mySounds;

    public C__SoundsAdapter(Activity myActivity, C__Sounds_MySounds mySounds) {
        this.myActivity = myActivity;
        this.mySounds = mySounds;
    }
    @Override
    public int getCount() {
        return mySounds.getMySoundsList().size();
    }

    @Override
    public C__Sounds getItem(int i) {
        return mySounds.getMySoundsList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        C__Sounds c = this.getItem(position);
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(myActivity).inflate(R.layout.one_line_sounds, viewGroup,false);
            holder = new ViewHolder();
            holder.trackImage = view.findViewById(R.id.image_sound);
            holder.title = view.findViewById(R.id.tv_sound_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(c.getName());
        if(c.isPlaying()){
            holder.trackImage.setImageResource(R.drawable.volume_up);
        }else{
            holder.trackImage.setImageResource(R.drawable.volume);
        }

        return view;
    }

    private class ViewHolder{
        ImageView trackImage;
        TextView title;
    }
}
