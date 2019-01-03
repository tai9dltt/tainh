package com.example.i_tainh.democonnectfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    Button buttonSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    buttonSub = findViewById(R.id.btnSubr);

    buttonSub.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseMessaging.getInstance().subscribeToTopic("Technology");
        }
    });

    }
}
