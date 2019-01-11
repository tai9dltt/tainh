package com.example.i_tainh.democonnectfirebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.i_tainh.democonnectfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    EditText statusInput;
    Button saveChangeButton;
    private DatabaseReference changeStatusRef;
    private FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        changeStatusRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        mToolbar = findViewById(R.id.status_app_bar);
        loadingBar = new ProgressDialog(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Change status");

        statusInput = findViewById(R.id.status_input);
        saveChangeButton = findViewById(R.id.save_status_change_button);
        saveChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_status = statusInput.getText().toString();
                ChangeProfileStatus(new_status);
            }
        });

    }

    private void ChangeProfileStatus(String new_status) {
        if(TextUtils.isEmpty(new_status)){
            return;
        }
        else{
            loadingBar.setTitle("Change Profile Status");
            loadingBar.setMessage("Loading ... ");
            loadingBar.show();
            changeStatusRef.child("user_status").setValue(new_status).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        loadingBar.dismiss();
                        Intent settingIntent = new Intent(StatusActivity.this, SettingAcctivity.class);
                        Toast.makeText(StatusActivity.this,"Update status successful", Toast.LENGTH_SHORT).show();
                        startActivity(settingIntent);
                    }
                   else{
                        Toast.makeText(StatusActivity.this,"Update status Fail", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
    }
}
