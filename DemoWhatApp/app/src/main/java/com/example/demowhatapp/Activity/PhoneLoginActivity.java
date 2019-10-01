package com.example.demowhatapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demowhatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {

    private Button verifyButton;
    private Button tv_sendVerificationCode;
    private EditText ed_inputPhoneNumber, ed_input_code_verification;

    private FirebaseAuth mAuth;

    private String mVerificationId;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        Intilizefield();

        mAuth = FirebaseAuth.getInstance();



        tv_sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = ed_inputPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ed_inputPhoneNumber.setError("Phone Number is required");
                } else {
                    loadingBar.setTitle("Phone verification");
                    loadingBar.setMessage("Please wait while we are authentication your phone...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           phoneNumber,60, TimeUnit.SECONDS, PhoneLoginActivity.this, callbacks);
                }

            }
        });


                callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        ed_inputPhoneNumber.setError("Invalid Phone number");
                        Log.w("ONERROR", "onVerificationFailed", e);

                        loadingBar.dismiss();
                        ed_inputPhoneNumber.setVisibility(View.INVISIBLE);
                        tv_sendVerificationCode.setVisibility(View.INVISIBLE);

                        ed_input_code_verification.setVisibility(View.VISIBLE);
                        verifyButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCodeSent(String veryficationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        mVerificationId = veryficationId;
                        mResendToken = forceResendingToken;
                        loadingBar.dismiss();


                        ed_inputPhoneNumber.setVisibility(View.INVISIBLE);
                        tv_sendVerificationCode.setVisibility(View.INVISIBLE);

                        ed_input_code_verification.setVisibility(View.VISIBLE);
                        verifyButton.setVisibility(View.VISIBLE);

                    }
                };

    }



    public void OnVerify(View v) {

        String verifyCode = ed_input_code_verification.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            ed_input_code_verification.setError("Phone Number is required");
        } else {
            loadingBar.setTitle("Verification Code!");
            loadingBar.setMessage("Please wai, while we are verifying Verification code ...");
            loadingBar.setCancelable(false);
            loadingBar.show();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verifyCode);
            signInWithPhoneAuthCredential(credential);
        }
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String messageError = task.getException().toString();
                            Toast.makeText(PhoneLoginActivity.this, messageError, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }


    private void Intilizefield() {
        verifyButton = findViewById(R.id.btnVerify);
        tv_sendVerificationCode = findViewById(R.id.tv_send_verification_code);
        ed_input_code_verification = findViewById(R.id.verification_input_code_verification);
        ed_inputPhoneNumber = findViewById(R.id.verification_input_phone_number);
        loadingBar = new ProgressDialog(this);
    }
}
