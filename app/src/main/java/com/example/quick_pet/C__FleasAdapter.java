package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__FleasAdapter extends BaseAdapter {

    Activity myActivity;
    C__Fleas_MyFleas myFleas;

    public C__FleasAdapter(Activity myActivity, C__Fleas_MyFleas myFleas) {
        this.myActivity = myActivity;
        this.myFleas = myFleas;
    }

    @Override
    public int getCount() {
        return myFleas.getMyFleasList().size();
    }

    @Override
    public C__Fleas getItem(int i) {
        return myFleas.getMyFleasList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneFleasLine;
        LayoutInflater inflater = ((LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        oneFleasLine = inflater.inflate(R.layout.one_line_fleas, viewGroup, false);
        TextView tv_date = oneFleasLine.findViewById(R.id.tv_fleas_date);

        C__Fleas f = this.getItem(position);
        tv_date.setText(f.getDates());
        return oneFleasLine;
    }
}
