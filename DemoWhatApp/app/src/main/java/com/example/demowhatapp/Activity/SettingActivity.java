package com.example.demowhatapp.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demowhatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private final String  LOG_NAME = SettingActivity.class.toString();

    private Button updateAccountButton;

    private EditText userName, userStatus;

    private CircleImageView userProfileImage;

    private String currentUserID;

    private FirebaseAuth mAuth;

    private DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        rootReference = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        RetriveUserInfo();

    }


    private void InitializeFields(){

        updateAccountButton = (Button) findViewById(R.id.update_setting_button);
        userName = findViewById(R.id.set_username);
        userStatus = findViewById(R.id.set_profile_status);
        userProfileImage = (CircleImageView) findViewById(R.id.profile_image);

    }

    public void OnUpdateProfile(View view){
        String setUserName = userName.getText().toString().trim();
        String setUserStatus = userStatus.getText().toString().trim();

        if (TextUtils.isEmpty(setUserName)) {
            Toast.makeText(this, "Please enter your name ....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(setUserStatus)) {
            Toast.makeText(this, "Please enter your status ....", Toast.LENGTH_SHORT).show();
        }
        else{
            Map<String,String> profileMap = new HashMap<>();

            profileMap.put("uid", currentUserID);
            profileMap.put("name",setUserName);
            profileMap.put("status",setUserStatus);

            rootReference.child("Users").child(currentUserID).setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SettingActivity.this, "Profile update successfully ....", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String message = task.getException().toString();
                                Log.d(LOG_NAME, message);
                            }
                        }
                    });
        }
    }

    private void RetriveUserInfo(){
        rootReference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("image")){
                    String retriveUserName = dataSnapshot.child("name").getValue().toString();
                    String retriveUserStatus = dataSnapshot.child("status").getValue().toString();
                    String retriveUserImage = dataSnapshot.child("image").getValue().toString();

                    userName.setText(retriveUserName);
                    userStatus.setText(retriveUserStatus);
                }
                else if(dataSnapshot.exists() && dataSnapshot.hasChild("name")){
                    String retriveUserName = dataSnapshot.child("name").getValue().toString();
                    String retriveUserStatus = dataSnapshot.child("status").getValue().toString();

                    userName.setText(retriveUserName);
                    userStatus.setText(retriveUserStatus);

                }
                else{
                    //only edit user name in the first login
//                    userName.setVisibility(View.VISIBLE);
                    Toast.makeText(SettingActivity.this, "Please set & update your information", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}