package com.example.i_tainh.democonnectfirebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private Button register;
    private EditText pass1, name1;
    private EditText email1;
    private ProgressDialog loadingBar;
    private DatabaseReference storeUserDefault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        email1 = findViewById(R.id.register_email);
        pass1 = findViewById(R.id.register_pass);
        name1 = findViewById(R.id.register_name);

        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }


    private void Register() {

        final String name = name1.getText().toString().trim();
        String email = email1.getText().toString().trim();
        String password = pass1.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "Please write your password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create new account");
            loadingBar.setMessage("Please wait, while we are creating account for you.");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String current_user_id = mAuth.getCurrentUser().getUid();
                                storeUserDefault = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);
                                storeUserDefault.child("user_name").setValue(name);
                                storeUserDefault.child("user_status").setValue("I am using app chat");
                                storeUserDefault.child("user_image").setValue("default_profile");
                                storeUserDefault.child("user_thumb_image").setValue("default_image")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

//                                Toast.makeText(RegisterActivity.this, "Register successfull.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });

                                } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Register failed.", Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    });

        }
    }
}
