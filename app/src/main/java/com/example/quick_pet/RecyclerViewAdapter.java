package com.example.quick_pet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class RecyclerViewAdapter {
}

//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
//
//    private List<C__Appointment> appointmentList;
//    Context context;
//
//    public RecyclerViewAdapter(List<C__Appointment> appointmentList, Context context) {
//        this.appointmentList = appointmentList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_appointment, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.tv_type.setText(appointmentList.get(position).getType());
//        holder.tv_date.setText(appointmentList.get(position).getDate());
//        holder.tv_time.setText(appointmentList.get(position).getTime());
//        holder.tv_placeName.setText(appointmentList.get(position).getName());
//        holder.parentLayout.setOnClickListener(view -> {
//            Intent intent = new Intent(context, Add_Appointment.class);
//            intent.putExtra("id", appointmentList.get(position).getId());
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return appointmentList.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tv_date;
//        TextView tv_time;
//        TextView tv_placeName;
//        TextView tv_type;
//        ConstraintLayout parentLayout;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tv_date = itemView.findViewById(R.id.tv_date);
//            tv_time = itemView.findViewById(R.id.tv_time);
//            tv_placeName = itemView.findViewById(R.id.tv_placeName);
//            tv_type = itemView.findViewById(R.id.tv_type);
//            parentLayout = itemView.findViewById(R.id.oneLineAppointmentLayout);
//        }
//    }
//
//    public List<C__Appointment> getAppointmentList() {
//        return appointmentList;
//    }
//    public void setAppointmentList(List<C__Appointment> appointmentList) {
//        this.appointmentList = appointmentList;
//    }
//
//    public Context getContext() {
//        return context;
//    }
//    public void setContext(Context context) {
//        this.context = context;
//    }
//}
