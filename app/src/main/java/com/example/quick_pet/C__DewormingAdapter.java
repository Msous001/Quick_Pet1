package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__DewormingAdapter extends BaseAdapter {

    Activity myActivity;
    C__Deworming_MyDeworming myDeworming;

    public C__DewormingAdapter(Activity myActivity, C__Deworming_MyDeworming myDeworming) {
        this.myActivity = myActivity;
        this.myDeworming = myDeworming;
    }

    @Override
    public int getCount() {
        return myDeworming.getMyDewormingList().size();
    }

    @Override
    public C__Deworming getItem(int i) {
        return myDeworming.getMyDewormingList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneDewormingLine;
        LayoutInflater inflater = ((LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        oneDewormingLine = inflater.inflate(R.layout.one_line_deworming, viewGroup, false);
        TextView tv_date = oneDewormingLine.findViewById(R.id.tv_deworming_date);

        C__Deworming d = this.getItem(position);
        tv_date.setText(d.getDates());
        return oneDewormingLine;
    }
}
