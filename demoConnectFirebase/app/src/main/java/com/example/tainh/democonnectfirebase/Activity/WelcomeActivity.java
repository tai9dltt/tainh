package com.example.tainh.democonnectfirebase.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tainh.democonnectfirebase.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}
