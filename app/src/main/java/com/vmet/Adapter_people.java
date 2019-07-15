package com.vmet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vmet.BaseApplication.BaseApplication;

import java.util.List;

public class Adapter_people extends RecyclerView.Adapter<Adapter_people.ViewHolder> {

    private Context context;
    private List<User> userList;

    public Adapter_people(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public Adapter_people.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_chat_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_people.ViewHolder holder, final int i) {
        //  holder.circularImageView.setImageResource(myChatArray.get(i).getPeopleImage());
        holder.nameTextView.setText(userList.get(i).getUsername());
      /*  holder.chatContentTextView.setText(myChatArray.get(i).getChatting());
        holder.timeTextView.setText(myChatArray.get(i).getLastMsgTime());*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userList.get(i).getUserid();
                BaseApplication.getInstance().getSession().setChatWith(id);
                String name = userList.get(i).getUsername();
                Intent intent = new Intent(context,Chatting.class);
                intent.putExtra("chatWith",id);
                intent.putExtra("name",name);
                context.startActivity(intent);
                Toast.makeText(context, "id"+id, Toast.LENGTH_SHORT).show();
            }
        });
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

