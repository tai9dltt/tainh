package com.example.demowhatapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demowhatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.disposables.Disposable;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_NAME = RegisterActivity.class.toString();

    private Button CreateAccountButton;

    private EditText UserEmail, UserPassword, UserConfirmPassword;

    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;

    private DatabaseReference rootReference;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        InitializeFields();

    }

    public void OnCreateAccount(View v) {
        String email = UserEmail.getText().toString().trim();
        String password = UserPassword.getText().toString().trim();
        String comfirmPasswork = UserConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email ....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password ....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(comfirmPasswork)) {
            Toast.makeText(this, "Please confirm password ....", Toast.LENGTH_SHORT).show();
        }
        if (!password.equals(comfirmPasswork)) {
            Toast.makeText(this, "Confirm password is wrong ....", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create new account");
            loadingBar.setMessage("Please wait, while we are creating account for you.");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String currentUsetID = mAuth.getCurrentUser().getUid();
                                rootReference.child("Users").child(currentUsetID).setValue("");

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                Log.e("RegisterError", message);
                            }
                            loadingBar.dismiss();
                        }
                    });
        }
    }

    public void onMoveLoginActivity(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void InitializeFields() {
        CreateAccountButton = findViewById(R.id.btn_Register);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        UserPassword = (EditText) findViewById(R.id.register_password);
        AlreadyHaveAccountLink = findViewById(R.id.already_have_a_account);
        loadingBar = new ProgressDialog(this);
    }




}
