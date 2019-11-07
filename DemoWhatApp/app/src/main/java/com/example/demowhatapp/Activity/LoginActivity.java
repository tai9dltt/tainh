package com.example.demowhatapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demowhatapp.R;
import com.example.demowhatapp.Ultil.MainThreadExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.biometric.BiometricPrompt;

import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {

    public FirebaseUser currentUser;

    public static FirebaseAuth mAuth;

    public static DatabaseReference rootReference;

    public static Button LoginButton, PhoneLoginButton;

    public static EditText UserEmail, UserPassword;

    public TextView NeedNewAccountLink, ForgetPasswordLink;

    private static ProgressDialog loadingBar;

    private static CheckBox checkBoxSavePassword;

    private static DatabaseReference UserRef;

    ImageButton loginFinger;

    public static SharedPreferences preferences;

    private BiometricPrompt biometricPrompt = null;
    private Executor executor = new MainThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        rootReference = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        InitializeFields();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        if (biometricPrompt == null)
            biometricPrompt = new androidx.biometric.BiometricPrompt(this, executor, callback);

        findViewById(R.id.btnLoginWithFinger).setOnClickListener(view -> {
            androidx.biometric.BiometricPrompt.PromptInfo promptInfo = buildBiometricPrompt();
            biometricPrompt.authenticate(promptInfo);
        });


        // On Check Save password
        preferences = getSharedPreferences("passwordSave", Context.MODE_PRIVATE);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        UserEmail.setText(email);
        UserPassword.setText(password);

        checkBoxSavePassword.setOnClickListener(view -> {
            if (UserEmail != null && UserPassword != null) {
                if (checkBoxSavePassword.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("passwordSave", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", UserEmail.getText().toString());
                    editor.putString("password", UserPassword.getText().toString());
                    editor.commit();
                }
            } else {
                return;
            }

        });

        // On Forgot password handel
        ForgetPasswordLink.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
            startActivity(intent);
        });

    }

    // On login with FingerPrint and Biometric
    private BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.d("ERROR", "Error message " + errorCode + ": " + errString);
//            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            String email = preferences.getString("email", null);
            String password = preferences.getString("password", null);
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while logging....");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentUserId = mAuth.getCurrentUser().getUid();
                        String deviceToken = FirebaseInstanceId.getInstance().getToken();
                        UserRef.child(currentUserId).child("device_token").setValue(deviceToken)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Logged", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    private BiometricPrompt.PromptInfo buildBiometricPrompt() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Login into your account")
                .setDescription("Touch your finger on the finger print sensor to authorise your account.")
                .setNegativeButtonText("Cancel")
                .build();
    }

    public void OnLoginWithPhoneNumber(View v) {
        Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void OnMoveRegisterActivity(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void SendUserToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void OnLogin(View view) {
        String email = UserEmail.getText().toString().trim();
        String password = UserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email ....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password ....", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while logging....");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentUserId = mAuth.getCurrentUser().getUid();
                        String deviceToken = FirebaseInstanceId.getInstance().getToken();
                        UserRef.child(currentUserId).child("device_token").setValue(deviceToken)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SendUserToMainActivity();
                                            Toast.makeText(LoginActivity.this, "Logged", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

    }


    public void InitializeFields() {
        LoginButton = findViewById(R.id.btn_login);
        PhoneLoginButton = findViewById(R.id.btn_login_by_phone);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        NeedNewAccountLink = findViewById(R.id.login_need_new_account);
        ForgetPasswordLink = findViewById(R.id.login_forget_password);
        checkBoxSavePassword = findViewById(R.id.checkboxSavePassword);
        loginFinger = findViewById(R.id.btnLoginWithFinger);
        loadingBar = new ProgressDialog(this);

    }
}
