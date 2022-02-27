package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__HealthAdapter extends BaseAdapter {
    Activity mActivity;
    C__Health_MyHealth myHealth;

    public C__HealthAdapter(Activity mActivity, C__Health_MyHealth myHealth) {
        this.mActivity = mActivity;
        this.myHealth = myHealth;
    }

    @Override
    public int getCount() {
        return myHealth.getMyHealthList().size();
    }

    @Override
    public C__Health getItem(int i) {
        return myHealth.getMyHealthList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneHealthLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneHealthLine = inflater.inflate(R.layout.one_line_health, viewGroup, false);
        TextView tv_name = oneHealthLine.findViewById(R.id.tv_health_name);
        C__Health h = this.getItem(position);
        tv_name.setText(h.getName());

        return oneHealthLine ;
    }
}
