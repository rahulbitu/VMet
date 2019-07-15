package com.vmet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vmet.BaseApplication.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class Adapter_newGroup  extends RecyclerView.Adapter<Adapter_newGroup.ViewHolder> {

    private Context context;
    private List<User> userList;

    public Adapter_newGroup(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public Adapter_newGroup.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_chat_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_newGroup.ViewHolder holder, final int i) {


      /*  final User model = userList.get(i);
        holder.itemView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                model.setSelected(!model.isSelected());
                holder.itemView.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                return false;
            }
        });*/

        //  holder.circularImageView.setImageResource(myChatArray.get(i).getPeopleImage());
        holder.nameTextView.setText(userList.get(i).getUsername());
      /*  holder.chatContentTextView.setText(myChatArray.get(i).getChatting());
        holder.timeTextView.setText(myChatArray.get(i).getLastMsgTime());*/

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView circularImageView;
        TextView nameTextView,chatContentTextView,timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularImageView = itemView.findViewById(R.id.circularImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            chatContentTextView = itemView.findViewById(R.id.chatContentTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}