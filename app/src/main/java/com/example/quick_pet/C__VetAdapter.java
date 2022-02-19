package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class
C__VetAdapter extends BaseAdapter {
    Activity mActivity;
    C__Vet_MyVets myVets;

    public C__VetAdapter(Activity mActivity, C__Vet_MyVets myVets) {
        this.mActivity = mActivity;
        this.myVets = myVets;
    }

    @Override
    public int getCount() {
        return myVets.getMyVetsList().size();
    }

    @Override
    public C__Vet getItem(int i) {
        return myVets.getMyVetsList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneVetLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneVetLine = inflater.inflate(R.layout.one_line_vet, viewGroup,false);

        TextView tv_date = oneVetLine.findViewById(R.id.tv_vetVisit_date);
        TextView tv_name = oneVetLine.findViewById(R.id.tv_vetVisit_name);

        C__Vet c = this.getItem(position);
        tv_date.setText(c.getDate());
        tv_name.setText(c.getName());
        return oneVetLine;
    }
}
