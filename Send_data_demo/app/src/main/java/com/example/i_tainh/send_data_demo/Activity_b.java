package com.example.i_tainh.send_data_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_b extends AppCompatActivity {

    private TextView title;
    private TextView des;
    private Button btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        title = (TextView) findViewById(R.id.title2);
        des = (TextView) findViewById(R.id.description2);
       setDatabyBundle();

       btnReturn = (Button) findViewById(R.id.btnReturnLogin);
       btnReturn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Activity_b.this, LoginActivity.class);
               startActivity(intent);
           }
       });
    }




    public void setDatabyBundle(){



        Intent  intent = getIntent();

        Bundle bundle = intent.getBundleExtra(Activity_a.BUNDLE);

        String  title2 = bundle.getString(Activity_a.TITLE);
        String  description2 = bundle.getString(Activity_a.DESCRIPTION);

        title.setText(title2);
        des.setText(description2);


    }

    public void setDataByIntent(){


        Intent intent = getIntent();
        String  title2 = intent.getStringExtra(Activity_a.TITLE);
        String  description2 = intent.getStringExtra(Activity_a.DESCRIPTION);

        title.setText(title2);
        des.setText(description2);
    }
}
