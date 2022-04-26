package com.example.quick_pet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class C__NotificationAdapter extends BaseAdapter
{
    Activity mActivity;
    C__Notification_MyNot myNot;

    public C__NotificationAdapter(Activity mActivity, C__Notification_MyNot myNot) {
        this.mActivity = mActivity;
        this.myNot = myNot;
    }

    @Override
    public int getCount() { return myNot.getMyNotList().size();}

    @Override
    public C__Notifications getItem(int i) { return myNot.getMyNotList().get(i);}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View oneNotificationLine;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneNotificationLine = inflater.inflate(R.layout.one_line_notification, viewGroup, false);

        TextView tv_date = oneNotificationLine.findViewById(R.id.notif_date);
        TextView tv_name = oneNotificationLine.findViewById(R.id.notif_Name);
        TextView tv_time = oneNotificationLine.findViewById(R.id.notif_time);

        C__Notifications n = this.getItem(position);
        tv_date.setText(n.getDate());
        tv_name.setText(n.getName());
        tv_time.setText(n.getTime());
        return oneNotificationLine;
    }
}
