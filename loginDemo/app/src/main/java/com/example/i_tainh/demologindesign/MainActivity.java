package com.example.i_tainh.demologindesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
//    private static final String EMAIL = "email";

    ProfilePictureView profilePictureView;

    CallbackManager callbackManager;
    LoginButton loginFace;
    Button btn_login;
    TextView txtName, txtPass;
    String email, name, dob, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.btn_login);
        loginFace = (LoginButton) findViewById(R.id.facebook_button);
        txtName = (TextView) findViewById(R.id.ed_Username);
        txtPass = (TextView) findViewById(R.id.ed_password);
        profilePictureView = (ProfilePictureView) findViewById(R.id.imgUser);

        loginFace.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_gender"));

        setLogin_Button();


    }
    

    private void setLogin_Button() {
        loginFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
            }
        });
        Bundle parameters = new Bundle();

        parameters.putString("fields", "name, email ,gender ,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void wiget(){

    }
}
