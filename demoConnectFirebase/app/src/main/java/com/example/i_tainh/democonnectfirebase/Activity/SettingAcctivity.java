package com.example.i_tainh.democonnectfirebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;


public class SettingAcctivity extends AppCompatActivity {

    private CircleImageView settingDisplayImage;
    private TextView settingDisplayName;
    private TextView settingDisplayStatus;
    private Button settingChangeProfileImangeButton;
    private Button settingChangeStatusButton;
    private final static int GALLAY_PICK = 1;
    private DatabaseReference getUserReference;
    private FirebaseAuth mAuth;
    private StorageReference storeProfileImageStoreageRef;
    ProgressDialog loadingBar;
    Bitmap thumbBitmap = null;
    private StorageReference thumbImageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_acctivity);

        mAuth = FirebaseAuth.getInstance();
        String online_user_id = mAuth.getCurrentUser().getUid();
        getUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(online_user_id);
        storeProfileImageStoreageRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");
        loadingBar = new ProgressDialog(this);
        thumbImageRef = FirebaseStorage.getInstance().getReference().child("Thumb_Images");

        settingDisplayImage = (CircleImageView) findViewById(R.id.img_user);
        settingDisplayName = findViewById(R.id.textViewUsername);
        settingDisplayStatus = findViewById(R.id.user_status);
        settingChangeProfileImangeButton = findViewById(R.id.setting_chan_imangeProfile_button);
        settingChangeStatusButton = findViewById(R.id.setting_change_profile_status_button);

        getUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("user_name").getValue().toString();
                String status = dataSnapshot.child("user_status").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                String thumb_image = dataSnapshot.child("user_thumb_image").getValue().toString();

                settingDisplayName.setText(name);
                settingDisplayStatus.setText(status);
                Picasso.get().load(image).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(settingDisplayImage);

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
        settingChangeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingAcctivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLAY_PICK && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            Log.d("imageURI", imageUri.toString());
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                loadingBar.setMessage("Updating Profile image ... ");
                loadingBar.show();
                 Uri resultUri = result.getUri();

                File thumb_filePathUri = new File(resultUri.getPath());


                String user_id = mAuth.getCurrentUser().getUid();

                try{
                    thumbBitmap = new Compressor(this).setMaxHeight(200).setMaxHeight(200).setQuality(50).compressToBitmap(thumb_filePathUri);

                }catch (Exception e){
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();

                 StorageReference  filePath = storeProfileImageStoreageRef.child(user_id + ".jpg");

                final StorageReference thumb_filePath = thumbImageRef.child(user_id + ".jpg");



                filePath.putFile(resultUri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SettingAcctivity.this,"Saving your profile image to firebase",Toast.LENGTH_SHORT).show();

                            final String downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl()+"";

                            UploadTask uploadTask = thumb_filePath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                    String thumb_downloadUrl = task.getResult().getMetadata().getReference().getDownloadUrl()+"";
                                    Log.d("UrlThumbImage", thumb_downloadUrl);
                                    if (task.isSuccessful()){
                                        Map update_user_data = new HashMap();
                                        update_user_data.put("user_image",downloadUrl);
                                        update_user_data.put("user_thumb_image",thumb_downloadUrl);

                                        getUserReference.updateChildren(update_user_data)
                                                .addOnCompleteListener(new OnCompleteListener() {
                                                    @Override
                                                    public void onComplete(@NonNull Task task) {
                                                        Toast.makeText(SettingAcctivity.this,"Image upload successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                            Log.d("downloadUrl", downloadUrl);
                            getUserReference.child("user_image").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SettingAcctivity.this,"Image upload successful",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }else {
                            Toast.makeText(SettingAcctivity.this,"Something wrong, try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                }else{
                Toast.makeText(SettingAcctivity.this,"Something wrong, try again",Toast.LENGTH_SHORT).show();


            }
        }

    }
}
