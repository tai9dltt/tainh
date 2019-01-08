package com.example.tainh.democonnectfirebase.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tainh.democonnectfirebase.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    Button buttonSub, logoutGG;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    Button moveToChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    buttonSub = findViewById(R.id.btnSubr);
    logoutGG = findViewById(R.id.logoutButton);

        moveToChat = findViewById(R.id.btn_moveToChat);
        moveToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    buttonSub.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseMessaging.getInstance().subscribeToTopic("Technology");
        }
    });



    }


}
