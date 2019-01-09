package com.example.i_tainh.democonnectfirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingAcctivity extends AppCompatActivity {

    private CircleImageView settingDisplayImage;
    private TextView settingDisplayName;
    private TextView settingDisplayStatus;
    private Button settingChangeProfileImangeButton;
    private Button settingChangeStausButton;
    private final static int GALLAY_PICK = 1;
    private DatabaseReference getUserReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acctivity);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        getUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);

        settingDisplayImage = findViewById(R.id.img_user);
        settingDisplayName = findViewById(R.id.textViewUsername);
        settingDisplayStatus = findViewById(R.id.user_status);
        settingChangeProfileImangeButton = findViewById(R.id.setting_chan_imangeProfile_button);
        settingChangeStausButton = findViewById(R.id.setting_change_profile_status_button);

        getUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("user_name").getValue().toString();
                String status = dataSnapshot.child("user_status").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumb = dataSnapshot.child("user_thumb_image").getValue().toString();

                settingDisplayName.setText(name);
                settingDisplayStatus.setText(status);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        settingChangeProfileImangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLAY_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
