package com.example.i_tainh.send_data_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText password;
    Button  btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    id = (EditText) findViewById(R.id.txtID);
    password =(EditText) findViewById(R.id.txtPassword);
    btnLogin = (Button) findViewById(R.id.btnLogin);

    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                if(id.getText().toString().trim().equalsIgnoreCase("tainh") && password.getText().toString().trim().equalsIgnoreCase("123")){

                    Intent intent = new Intent(LoginActivity.this, Activity_a.class);
                    startActivity(intent);
                }
                else{
                    return;
                }
            }catch (Exception ex){
                Toast.makeText(LoginActivity.this,"something wrong", Toast.LENGTH_SHORT);
            }

        }
    });

    }
}
