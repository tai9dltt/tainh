package com.example.i_tainh.democonnectfirebase.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private Button sendFriendRequest;
    private Button declineFirendRequest;
    TextView profileName;
    TextView profileStatus;
    ImageView profileImage;
    DatabaseReference userReference;
    private String CURRENT_STATE;
    private  DatabaseReference friendRequestReference;
    private FirebaseAuth mAuth;
    String sender_user_id;
    String receiver_user_id;
    private DatabaseReference friendsReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        friendRequestReference = FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        mAuth = FirebaseAuth.getInstance();
        sender_user_id = mAuth.getCurrentUser().getUid();

        userReference = FirebaseDatabase.getInstance().getReference().child("Users");
        String visit_user_id = getIntent().getExtras().get("Visit_user_id").toString();
        friendsReference = FirebaseDatabase.getInstance().getReference().child("Friends");

        receiver_user_id = getIntent().getExtras().get("Visit_user_id").toString();

        sendFriendRequest =findViewById(R.id.profile_visit_send_req_button);
        declineFirendRequest = findViewById(R.id.profile_decline_friend_req_button);
        profileImage = findViewById(R.id.profile_visit_user_image);
        profileName = findViewById(R.id.profile_visit_username);
        profileStatus = findViewById(R.id.profile_visit_user_status);


        CURRENT_STATE = "not friends";


        Toast.makeText(ProfileActivity.this,visit_user_id,Toast.LENGTH_SHORT).show();

        userReference.child(visit_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameTemp = dataSnapshot.child("user_name").getValue()+"";

                Log.d("UserNameTemp", nameTemp);
                String status = dataSnapshot.child("user_status").getValue()+ "";
                Log.d("UserStatus", status);
                String image = dataSnapshot.child("user_image").getValue()+"";

                profileName.setText(nameTemp);
                profileStatus.setText(status);
                Picasso.get().load(image).placeholder(R.drawable.p4).into(profileImage);
                friendRequestReference.child(sender_user_id)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.hasChild(receiver_user_id)){
                                    String req_type = dataSnapshot.child(receiver_user_id).child("request_type").getValue().toString();

                                    if(req_type.equals("sent")){
                                        CURRENT_STATE = "request_sent";
                                        sendFriendRequest.setText("Cancel Friend Request");
                                    }
                                    else if(req_type.equals("received")){
                                        CURRENT_STATE = "request_received";
                                        sendFriendRequest.setText("Accept friend request");
                                    }
                                }
                                else{
                                    friendsReference.child(sender_user_id)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.hasChild(receiver_user_id)){
                                                        CURRENT_STATE = "friends";
                                                        sendFriendRequest.setText("Unfriend this person");
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                }
                            }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(!sender_user_id.equals(receiver_user_id)){
            sendFriendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendFriendRequest.setEnabled(false);

                    if(CURRENT_STATE.equals("not friends")){
                        sendRequestToAPerson();
                    }

                    if(CURRENT_STATE.equals("request_sent")){
                        CancelFriendRequest();
                    }
                    if(CURRENT_STATE.equals("request_received")){
                        AcceptFriendRequest();
                    }
                    if(CURRENT_STATE.equals("friends")){
                        UnFriend();
                    }
                }
            });
        }
        else{
            declineFirendRequest.setVisibility(View.INVISIBLE);
            sendFriendRequest.setVisibility(View.INVISIBLE);
        }
    }

    private void UnFriend() {

        friendsReference.child(sender_user_id).child(receiver_user_id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            friendsReference.child(receiver_user_id).child(sender_user_id).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                sendFriendRequest.setEnabled(true);
                                                CURRENT_STATE = "not_friends";
                                                sendFriendRequest.setText("Send Friend Request");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void AcceptFriendRequest() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
        final  String saveCurrentDate = currentDate.format(calendar.getTime());

        friendsReference.child(sender_user_id).child(receiver_user_id).setValue(saveCurrentDate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        friendsReference.child(receiver_user_id).child(sender_user_id).setValue(saveCurrentDate)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        friendRequestReference.child(sender_user_id).child(receiver_user_id).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            friendRequestReference.child(receiver_user_id).child(sender_user_id).removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                sendFriendRequest.setEnabled(true);
                                                                                CURRENT_STATE = "friends";
                                                                                sendFriendRequest.setText("Unfriend this person");
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void CancelFriendRequest() {
        friendRequestReference.child(sender_user_id).child(receiver_user_id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            friendRequestReference.child(receiver_user_id).child(sender_user_id).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                sendFriendRequest.setEnabled(true);
                                                CURRENT_STATE = "not friends";
                                                sendFriendRequest.setText("Send Friend Request");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void sendRequestToAPerson() {
        friendRequestReference.child(sender_user_id).child(receiver_user_id).child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            friendRequestReference.child(receiver_user_id).child(sender_user_id)
                                    .child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                sendFriendRequest.setEnabled(true);
                                                CURRENT_STATE = "request_sent";
                                                sendFriendRequest.setText("Cancel Friend Request");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}


