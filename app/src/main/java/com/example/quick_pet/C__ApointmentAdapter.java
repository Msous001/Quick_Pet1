package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__ApointmentAdapter extends BaseAdapter {

    Activity mActivity;
    C__Appointment_MyApp myApp;

    public C__ApointmentAdapter(Activity mActivity, C__Appointment_MyApp myApp) {
        this.mActivity = mActivity;
        this.myApp = myApp;
    }

    @Override
    public int getCount() {
        return myApp.getMyAppList().size();
    }

    @Override
    public C__Appointment getItem(int i) {
        return myApp.getMyAppList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneAppLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneAppLine = inflater.inflate(R.layout.one_line_appointment, viewGroup, false);

        TextView tv_date = oneAppLine.findViewById(R.id.tv_date);
        TextView tv_time = oneAppLine.findViewById(R.id.tv_time);
        TextView tv_placeName = oneAppLine.findViewById(R.id.tv_placeName);
        TextView tv_type = oneAppLine.findViewById(R.id.tv_type);

        C__Appointment ap = this.getItem(position);
        tv_date.setText(ap.getDate());
        tv_time.setText(ap.getTime());
        tv_placeName.setText(ap.getName());
        tv_type.setText(ap.getType());
        return oneAppLine;
    }
}
