package com.example.i_tainh.demosharepreferences;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private  final  String TAG = getClass().getSimpleName();

    private final  String SHARE_PREFERENCES = "tainh";
    private final String MY_NAME = "my_name";
    private final String  MY_AGE = "my_age";

    private Button btnSave, btnReadData, btnRemoveByKey, btnRemoveAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        btnReadData = (Button) findViewById(R.id.btnReadData);
        btnReadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
        btnRemoveByKey = (Button) findViewById(R.id.btnRemoveByKey);
        btnRemoveByKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeByKey(MY_NAME);

            }
        });

        btnRemoveAll = (Button) findViewById(R.id.btnRemoveAll);
        btnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();

            }
        });

    }

    // Save data in: data/data/[application package name]/shared_prefs/shared_preferences_name.xml
    public void addData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MY_NAME, "nguyen huu tai");
        editor.putString(MY_AGE, "21");
        editor.apply();
        Toast.makeText(MainActivity.this,"save successfull",Toast.LENGTH_SHORT).show();
    }

    public void readData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
        // defauvalue: set My_NAME = dafault value if user not put value in sharePreferences
        String name = sharedPreferences.getString(MY_NAME,"tainh");
        String age = sharedPreferences.getString(MY_AGE, "20");
        String address = sharedPreferences.getString("ADDRESS", "viet nam");

        Log.i(TAG, "tainh : "+" \nname: " +name+ "\nAge : "+age+ "\naddress : "+address);
    }

    public void removeByKey(String key){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(key);
        editor.apply();
    }

    public void removeAll(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
