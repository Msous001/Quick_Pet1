package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class C__MedicationAdapter extends BaseAdapter {
    Activity myActivity;
    C__Medication_MyMedication myMedication;

    public C__MedicationAdapter(Activity myActivity, C__Medication_MyMedication myMedication) {
        this.myActivity = myActivity;
        this.myMedication = myMedication;
    }
    @Override
    public int getCount() {return myMedication.getMyMedicationList().size();}

    @Override
    public C__Medication getItem(int i) {return myMedication.getMyMedicationList().get(i);}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View OneMedLine;
        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        OneMedLine = inflater.inflate(R.layout.one_line_medication, viewGroup, false);

        TextView tv_date = OneMedLine.findViewById(R.id.tv_oneMed_date);
        TextView tv_name = OneMedLine.findViewById(R.id.tv_Med_name);
        TextView tv_reason = OneMedLine.findViewById(R.id.tv_Med_reason);

        C__Medication m = this.getItem(position);
        tv_date.setText(m.getDate());
        tv_name.setText(m.getName());
        tv_reason.setText(m.getReason());
        return OneMedLine;
    }
}
