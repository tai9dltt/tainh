package com.example.i_tainh.send_data_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_a extends AppCompatActivity {

    private EditText title, description;
    private Button btnSend;
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String BUNDLE= "BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        title = (EditText) findViewById(R.id.tv_title);
        description = (EditText) findViewById(R.id.tv_des);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTitle = title.getText().toString().trim();
                String textDescription = description.getText().toString().trim();
//                byExtras(textTitle,textDescription);
                byBundle(textTitle,textDescription);
            }
        });

    }

    public void  byExtras(String  title, String  description){
        Intent intent = new Intent(Activity_a.this, Activity_b.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, description);

        startActivity(intent);

    }

    public void byBundle(String  title, String  description){
        Intent intent = new Intent(Activity_a.this, Activity_b.class);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(DESCRIPTION, description);
        intent.putExtra(BUNDLE,bundle);
        startActivity(intent);


    }
}
