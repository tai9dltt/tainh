package com.example.tainh.democonnectfirebase.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tainh.democonnectfirebase.Model.ChatItem;
import com.example.tainh.democonnectfirebase.R;
import com.example.tainh.democonnectfirebase.viewHolder.ChatRecycleViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView listChat;
    private Button btn_send;
    private EditText chatContent;
    DatabaseReference databaseReference;
    private LinearLayoutManager linearLayoutManager;

    private FirebaseRecyclerAdapter<ChatItem, ChatRecycleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

    databaseReference =  FirebaseDatabase.getInstance().getReference();

        listChat = findViewById(R.id.list_chat);
        btn_send = findViewById(R.id.btn_sendMessage);
        chatContent = findViewById(R.id.txtMessage);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChatConntenToDb();
                chatContent.setText("");
            }
        });

        // set up layout
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listChat.setLayoutManager(linearLayoutManager);

        //get data chat to database

        Query query = databaseReference.child("chats");

        FirebaseRecyclerOptions<ChatItem> options=
                new FirebaseRecyclerOptions.Builder<ChatItem>()
                        .setQuery(query,ChatItem.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ChatItem, ChatRecycleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatRecycleViewHolder holder, int position, @NonNull ChatItem model) {
                holder.contentChat.setText(model.getChatConnten());
                holder.authorChat.setText(model.getChatAuthor());

            }

            @NonNull
            @Override
            public ChatRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat,viewGroup,false);


                return new ChatRecycleViewHolder(view);
            }
        };
        listChat.setAdapter(adapter);



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerOptions<User> options=
//                new FirebaseRecyclerOptions.Builder<Users>()
//                        .setQuery(mDatabase,Users.class)
//                        .setLifecycleOwner(this)
//                        .build();
//    }

    private void submitChatConntenToDb(){
        String chat = chatContent.getText().toString().trim();

        if(TextUtils.isEmpty(chat)){
            chatContent.setError("fill in blank");
            return;
        }else{
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            String userEmail = user.getEmail();

            ChatItem chatItem = new ChatItem(userId, userEmail, chat);
            Map<String , Object> chatValue = chatItem.toMap();
            Map<String ,Object> childValue = new HashMap<>();
            String key = databaseReference.child("chats").push().getKey();

            childValue.put("/chats/" + key ,chatValue);
            childValue.put("/user-chat/" + userId + "/" + key,chatValue);
            databaseReference.updateChildren(childValue);
        }
    }


}
