package com.example.covid_19trackingsystem;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    ArrayList<WarningNotification> list;

    public void setList(ArrayList<WarningNotification> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows_of_notification,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  NotificationAdapter.Holder holder, int position) {

        holder.msgs.setText(list.get(position).getMsg());
        holder.dates.setText(list.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }else {
            return list.size();

        }

    }

    class Holder extends RecyclerView.ViewHolder{

        TextView msgs;
        TextView dates;

    public Holder(@NonNull View view) {
        super(view);
        msgs = view.findViewById(R.id.msg);
        dates = view.findViewById(R.id.date);

    }

}




}
