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

public class Adapter_broadcast extends RecyclerView.Adapter<Adapter_broadcast.ViewHolder> {
    private ArrayList<Model_newBroadcast> myNewBroadcastArray;
    private Context context;

    public Adapter_broadcast(ArrayList<Model_newBroadcast> myNewBroadcastArray, Context context) {
        this.myNewBroadcastArray = myNewBroadcastArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_broadcast_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.circularImageView.setImageResource(myNewBroadcastArray.get(i).getPeopleImageView());
        holder.statusTextView.setText(myNewBroadcastArray.get(i).getStatus());
        holder.nameTextView.setText(myNewBroadcastArray.get(i).getPeopleName());
    }

    @Override
    public int getItemCount() {
        return myNewBroadcastArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView circularImageView;
        TextView nameTextView,statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.circularImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
