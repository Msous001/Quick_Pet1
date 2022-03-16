package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__GroomingAdapter extends BaseAdapter {
    Activity mActivity;
    C__Grooming_MyGrooming myGrooming;

    public C__GroomingAdapter(Activity mActivity, C__Grooming_MyGrooming myGrooming) {
        this.mActivity = mActivity;
        this.myGrooming = myGrooming;
    }

    @Override
    public int getCount() {
        return myGrooming.getMyGroomingList().size();
    }

    @Override
    public C__Grooming getItem(int i) {
        return myGrooming.getMyGroomingList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneGroomingLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneGroomingLine = inflater.inflate(R.layout.one_line_grooming, viewGroup, false);

        TextView tv_place = oneGroomingLine.findViewById(R.id.tv_grooming_name);
        TextView tv_date = oneGroomingLine.findViewById(R.id.tv_grooming_date);

        C__Grooming g = this.getItem(position);
        tv_place.setText(g.getPlace());
        tv_date.setText(g.getDate());
        return oneGroomingLine;
    }
}
