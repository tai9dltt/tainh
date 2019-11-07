package com.example.demowhatapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demowhatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatsActivity extends AppCompatActivity {

    private String currentGroupName, currentUserID, currentUserName, currentDate, currentTime;

    private FirebaseAuth mAuth;

    private DatabaseReference userReference, groupReference, groupMessageKeyReference;


    private Toolbar mToolbar;
    private ImageButton sendMessageButton;
    private EditText userMessageTextInput;
    private ScrollView mScrollView;
    private TextView displayTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chats);
        intilizeFiled();

        currentGroupName = getIntent().getExtras().getString("groupName").toString();
        Toast.makeText(this, currentGroupName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();

        userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        groupReference  = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);


        getUserInFo();

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageToDatabase();
                userMessageTextInput.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        groupReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayMessages(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            String chatDate = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatMessage = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatName = (String) ((DataSnapshot) iterator.next()).getValue();
            String chatTime = (String) ((DataSnapshot) iterator.next()).getValue();

            displayTextMessage.append(chatName +" :\n" + chatMessage + "\n" + chatTime +"       "+ chatDate+"\n\n");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    private void SaveMessageToDatabase() {
        String message = userMessageTextInput.getText().toString();
        String messageKey = groupReference.push().getKey();
        if(TextUtils.isEmpty(message)){
            return;
        }
        else{
            Calendar calendarDate = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = simpleDateFormat.format(calendarDate.getTime());

            Calendar calendarTime = Calendar.getInstance();
            SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm a");
            currentTime = simpleDateFormatTime.format(calendarTime.getTime());

            HashMap<String, Object> groupMessagerKey = new HashMap<>();
            groupReference.updateChildren(groupMessagerKey);

            assert messageKey != null;
            groupMessageKeyReference = groupReference.child(messageKey);

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("name", currentUserName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);

            groupMessageKeyReference.updateChildren(messageInfoMap);

        }
    }

    private void getUserInFo() {
        userReference.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intilizeFiled(){
        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Group name");
        sendMessageButton = (ImageButton) findViewById(R.id.send_message_button);
        userMessageTextInput = (EditText) findViewById(R.id.input_group_message);
        mScrollView = findViewById(R.id.my_Scroll_view);
        displayTextMessage = findViewById(R.id.group_chat_text_display);
    }


}
