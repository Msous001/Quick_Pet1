package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__Pet_Adapter_Account extends BaseAdapter {
    Activity mActivity;
    C__Pet_MyPets myPets;

    public C__Pet_Adapter_Account(Activity mActivity, C__Pet_MyPets myPets) {
        this.mActivity = mActivity;
        this.myPets = myPets;
    }

    @Override
    public int getCount() {
        return myPets.getMyPetList().size();
    }

    @Override
    public C__Pet getItem(int i) {
        return myPets.getMyPetList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View onePetName;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onePetName = inflater.inflate(R.layout.one_line_pet_account, viewGroup, false);
        TextView name = onePetName.findViewById(R.id.tv_pet_names);

        C__Pet a = this.getItem(position);
        name.setText(a.getName());
        return onePetName;
    }
}
