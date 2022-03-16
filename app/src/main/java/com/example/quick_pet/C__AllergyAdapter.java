package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__AllergyAdapter extends BaseAdapter {
    Activity mActivity;
    C__Allergy_MyAllergies myAllergies;

    public C__AllergyAdapter(Activity mActivity, C__Allergy_MyAllergies myAllergies) {
        this.mActivity = mActivity;
        this.myAllergies = myAllergies;
    }

    @Override
    public int getCount() {
        return myAllergies.getMyAllergyList().size();
    }

    @Override
    public C__Allergy getItem(int i) {
        return myAllergies.getMyAllergyList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneAllergyLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneAllergyLine = inflater.inflate(R.layout.one_line_allergies, viewGroup, false);

        TextView tv_name = oneAllergyLine.findViewById(R.id.tv_allergies_name);
        TextView tv_date = oneAllergyLine.findViewById(R.id.tv_allergies_date);

        C__Allergy a = this.getItem(position);
        tv_name.setText(a.getName());
        tv_date.setText(a.getDate());
        return oneAllergyLine;
    }
}
