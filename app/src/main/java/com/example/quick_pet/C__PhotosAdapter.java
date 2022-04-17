package com.example.quick_pet;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class C__PhotosAdapter extends BaseAdapter {
    Activity mActivity;
    C__Photos_MyPhotos myPhotos;

    public C__PhotosAdapter(Activity mActivity, C__Photos_MyPhotos myPhotos) {
        this.mActivity = mActivity;
        this.myPhotos = myPhotos;
    }


    @Override
    public int getCount() {
        return myPhotos.getMyPhotoList().size();
    }

    @Override
    public C__Photos getItem(int i) {
        return myPhotos.getMyPhotoList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View onePhotoLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onePhotoLine = inflater.inflate(R.layout.one_line_photos, viewGroup, false);
        ImageView iv = onePhotoLine.findViewById(R.id.iv_photos);

        C__Photos p = this.getItem(position);
        Picasso.get().load(p.getPicPicture()).into(iv);
       // iv.setImageURI(Uri.parse(p.getPicPicture()));
        return onePhotoLine;
    }
}
