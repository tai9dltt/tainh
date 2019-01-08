package com.example.tainh.democonnectfirebase.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tainh.democonnectfirebase.R;

public class ChatRecycleViewHolder extends RecyclerView.ViewHolder {

    public TextView authorChat;
    public TextView contentChat;

    public ChatRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        authorChat = itemView.findViewById(R.id.txt_author);
        contentChat = itemView.findViewById(R.id.txt_contentChat);
    }




}
