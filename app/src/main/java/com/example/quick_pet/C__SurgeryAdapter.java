package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__SurgeryAdapter extends BaseAdapter {
    Activity mActivity;
    C__Surgery_MySurgeries mySurgeries;

    public C__SurgeryAdapter(Activity mActivity, C__Surgery_MySurgeries mySurgeries) {
        this.mActivity = mActivity;
        this.mySurgeries = mySurgeries;
    }

    @Override
    public int getCount() {
        return mySurgeries.getMySurgeryList().size();
    }

    @Override
    public C__Surgery getItem(int i) {
        return mySurgeries.getMySurgeryList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneSurgeryLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneSurgeryLine = inflater.inflate(R.layout.one_line_surgery, viewGroup, false);

        TextView tv_name = oneSurgeryLine.findViewById(R.id.tv_surgery_name);
        TextView tv_date = oneSurgeryLine.findViewById(R.id.tv_surgery_date);

        C__Surgery c = this.getItem(position);
        tv_name.setText(c.getName());
        tv_date.setText(c.getDate());
        return oneSurgeryLine;
    }
}
