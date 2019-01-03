package com.example.i_tainh.demofragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnA, btnB;
    FragmentTransaction fragmentTransaction = null;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);


//        addFragment(R.layout.activity_main);
    }

    public void addFragment(View view){
        FragmentManager manager = getSupportFragmentManager();
        fragmentTransaction  = manager.beginTransaction();
        switch (view.getId()){
            case    R.id.btnA:
                 fragment = new FragmentA();
                Toast.makeText(this,"A", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.btnB:
                fragment = new FragmentB();
                Toast.makeText(this,"B", Toast.LENGTH_SHORT).show();
                break;
        }
//        fragmentTransaction.add(R.id.frameContent,fragment);
        fragmentTransaction.replace(R.id.frameContent,fragment);
        fragmentTransaction.commit();
    }
}
