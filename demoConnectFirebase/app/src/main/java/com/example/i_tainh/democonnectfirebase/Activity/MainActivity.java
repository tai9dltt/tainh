package com.example.i_tainh.democonnectfirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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



    buttonSub.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseMessaging.getInstance().subscribeToTopic("Technology");
        }
    });

    logoutGG.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signOut();
        }
    });

    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
