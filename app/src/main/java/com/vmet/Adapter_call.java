package com.vmet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_call extends RecyclerView.Adapter<Adapter_call.ViewHolder> {

    private ArrayList<Model_call> myCallArray;
    private Context context;

    public Adapter_call(ArrayList<Model_call> myCallArray, Context context) {
        this.myCallArray = myCallArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_call_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.circularImageView.setImageResource(myCallArray.get(i).getPeopleImage());
        holder.nameTextView.setText(myCallArray.get(i).getPeopleName());
        holder.dateTextView.setText(myCallArray.get(i).getDate());
        holder.timeTextView.setText(myCallArray.get(i).getLastMsgTime());
        holder.callStatusImageView.setImageResource(myCallArray.get(i).getCallArrowImage());
        holder.callTypeImageView.setImageResource(myCallArray.get(i).getCallTypeImage());
    }

    @Override
    public int getItemCount() {
        return myCallArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView circularImageView,callStatusImageView,callTypeImageView;
        TextView nameTextView,dateTextView,timeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.circularImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            callStatusImageView = itemView.findViewById(R.id.callStatusImageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            callTypeImageView = itemView.findViewById(R.id.callTypeImageView);
        }
    }
}
