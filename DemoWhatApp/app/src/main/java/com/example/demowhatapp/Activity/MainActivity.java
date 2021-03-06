package com.example.demowhatapp.Activity;


import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demowhatapp.R;
import com.example.demowhatapp.TabsAccessorAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager myViewPager;

    private TabLayout myTabLayout;

    private TabsAccessorAdapter myTabsAccessorAdapter;

    private FirebaseUser currentUser;

    private FirebaseAuth mAuth;

    private DatabaseReference rootReference;

    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference();

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("ChillApps");


        myViewPager = findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        updateUserStatus("online");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUserStatus("offline");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUserStatus("offline");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.item_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_find_friends_option) {
            Intent intent1= new Intent(getApplicationContext(), FindFriendsActivity.class);
            startActivity(intent1);
        }
        if (item.getItemId() == R.id.main_setting_option) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.main_logout_option) {
            updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
        }

        if (item.getItemId() == R.id.main_create_group_option) {
            RequestNewGroup();
        }

        return true;

    }

    private void RequestNewGroup() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_group, null);

        final EditText groupNameField = dialogView.findViewById(R.id.etgroupName);
        TextView txtError = dialogView.findViewById(R.id.tvErrorMessage);
        Button button1 =  dialogView.findViewById(R.id.btnCancel);
        Button button2 = dialogView.findViewById(R.id.btnOk);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = groupNameField.getText().toString().trim();

                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(MainActivity.this, "Please enter group name !!!", Toast.LENGTH_SHORT).show();
                } else {
                    VerifyUserExitsGroupName(groupName);

                    dialogBuilder.dismiss();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.cancel();
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void VerifyUserExitsGroupName(final String txtName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Groups");
        ref.orderByChild("Groups").equalTo(txtName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Toast.makeText(MainActivity.this, "Group already exsits", Toast.LENGTH_SHORT).show();
                } else {
                    CreateNewGroup(txtName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void CreateNewGroup(String groupName) {
        rootReference.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Group create successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void updateUserStatus(String state){
        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTIme = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTIme.format(calendar.getTime());

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime);
        onlineStateMap.put("date", saveCurrentDate);
        onlineStateMap.put("state", state);

        currentUserId = mAuth.getCurrentUser().getUid();
        rootReference.child("Users").child(currentUserId).child("userState").updateChildren(onlineStateMap);

    }

}
