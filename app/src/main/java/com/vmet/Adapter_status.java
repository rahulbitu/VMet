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

public class Adapter_status extends RecyclerView.Adapter<Adapter_status.ViewHolder> {

    private ArrayList<Model_status> myStatusArray;
    private Context context;

    public Adapter_status(ArrayList<Model_status> myStatusArray, Context context) {
        this.myStatusArray = myStatusArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_status_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.profilePicImageView.setImageResource(myStatusArray.get(i).getPeopleProfilePic());
        holder.peopleProfileNameTextView.setText(myStatusArray.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return myStatusArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profilePicImageView;
        TextView peopleProfileNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicImageView = itemView.findViewById(R.id.profilePicImageView);
            peopleProfileNameTextView = itemView.findViewById(R.id.peopleProfileNameTextView);
        }
    }
}
