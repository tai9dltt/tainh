package com.example.i_tainh.democonnectfirebase.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.i_tainh.democonnectfirebase.R;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView listChat;
    private Button btn_send;
    private EditText chatContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listChat = findViewById(R.id.list_chat);
        btn_send = findViewById(R.id.btn_sendMessage);
        chatContent = findViewById(R.id.txtMessage);





    }
}
