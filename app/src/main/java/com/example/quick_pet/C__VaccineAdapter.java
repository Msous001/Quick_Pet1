package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__VaccineAdapter extends BaseAdapter {
    Activity mActivity;
    C__Vaccine_MyVaccine myVaccine;

    public C__VaccineAdapter(Activity mActivity, C__Vaccine_MyVaccine myVaccine) {
        this.mActivity = mActivity;
        this.myVaccine = myVaccine;
    }

    @Override
    public int getCount() {return myVaccine.getMyVaccineList().size();}

    @Override
    public C__Vaccine getItem(int i) {return myVaccine.getMyVaccineList().get(i);}

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneVaccineLine;
        LayoutInflater inflater = ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        oneVaccineLine = inflater.inflate(R.layout.one_line_vaccine, viewGroup, false);

        TextView tv_date = oneVaccineLine.findViewById(R.id.tv_vetVaccine_date);
        TextView tv_name = oneVaccineLine.findViewById(R.id.tv_vetVaccine_name);

        C__Vaccine Va = this.getItem(position);
        tv_date.setText(Va.getVac_date());
        tv_name.setText(Va.getVac_name());
        return oneVaccineLine;
    }
}
