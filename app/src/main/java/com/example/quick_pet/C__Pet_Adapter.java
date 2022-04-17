package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class C__Pet_Adapter extends BaseAdapter {
    Activity mActivity;
    C__Pet_MyPets myPets;
    public C__Pet_Adapter(Activity mActivity, C__Pet_MyPets myPets) {
        this.mActivity = mActivity;
        this.myPets = myPets;
    }
    @Override
    public int getCount() {return myPets.getMyPetList().size();}
    @Override
    public C__Pet getItem(int i) {return myPets.getMyPetList().get(i);}
    @Override
    public long getItemId(int i) {return 0;}
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View onePhotoLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onePhotoLine = inflater.inflate(R.layout.one_line_pet, viewGroup, false);
        CircleImageView iv = onePhotoLine.findViewById(R.id.circle_Image_Pet);

        C__Pet a = this.getItem(position);
        iv.setImageURI(Uri.parse(a.getImageUrl()));
        return onePhotoLine;

    }
}
