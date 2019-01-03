package com.example.i_tainh.demoorderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.i_tainh.demoorderfood.DAO.NhanVienDAO;
import com.example.i_tainh.demoorderfood.R;

public class Login_activity extends AppCompatActivity {
    Button btnOk, btnCancel;
    NhanVienDAO nhanVienDAO;
    EditText username, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        wiget();
    }



    public void goLogin(View view){
        Intent intent = new Intent(Login_activity.this, Register_Activity.class);
        startActivity(intent);
    }
    public void demoLogin(View view){
        Intent intent = new Intent(Login_activity.this, Home_Activity.class);
        intent.putExtra("username_temp", username.getText().toString().trim());
        startActivity(intent);
    }
    public void wiget(){
        btnOk = findViewById(R.id.btn_login);
        username = findViewById(R.id.txt_Username_login);
        pass = findViewById(R.id.txt_Password_login);
    }
}
