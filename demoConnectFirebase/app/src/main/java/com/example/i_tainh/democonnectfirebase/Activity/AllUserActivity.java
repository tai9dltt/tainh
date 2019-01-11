package com.example.i_tainh.democonnectfirebase.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.i_tainh.democonnectfirebase.Model.AllUser;
import com.example.i_tainh.democonnectfirebase.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.i_tainh.democonnectfirebase.R.layout.all_user_display_layout;

public class AllUserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView allUsersList;
    private DatabaseReference allDatabaseUserReference;
    private FirebaseRecyclerAdapter<AllUser, AllUserViewHoldel> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        allUsersList = findViewById(R.id.all_users_list);
        mToolbar = findViewById(R.id.all_user_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");

        allUsersList.setLayoutManager(new LinearLayoutManager(this));
        allDatabaseUserReference = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<AllUser> options = new FirebaseRecyclerOptions.Builder<AllUser>()
                .setQuery(allDatabaseUserReference, AllUser.class).build();

        adapter = new FirebaseRecyclerAdapter<AllUser, AllUserViewHoldel>(options) {
            @NonNull
            @Override
            public AllUserViewHoldel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_user_display_layout, viewGroup, false);
                AllUserViewHoldel holder = new AllUserViewHoldel(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull AllUserViewHoldel holder, int position, @NonNull AllUser model) {
                String imgUrl = model.getUser_image();
                Picasso.get().load(imgUrl).into(holder.image);
                holder.name.setText(model.getUser_name());
                holder.status.setText(model.getUser_status());

            }
        };
        allUsersList.setAdapter(adapter);
    }


    public static class AllUserViewHoldel extends RecyclerView.ViewHolder {

        TextView name;
        TextView status;
        CircleImageView image;

        public AllUserViewHoldel(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.all_user_username);
            status = itemView.findViewById(R.id.all_user_userStatus);
             image = itemView.findViewById(R.id.all_user_profile_image);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d("Onstart", "onStart: init");
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.d("onStop", "onStop: stop users list");
    }


}








//    public AllUserViewHoldel(@NonNull View itemView) {
//            super(itemView);
//            mView = itemView;
//
//        }
//
//        private void setUser_name(String user_name){
//            TextView name = mView.findViewById(R.id.all_user_username);
//            name.setText(user_name);
//        }
//
//        public void setUser_status(String user_status){
//            TextView status = mView.findViewById(R.id.all_user_userStatus);
//            status.setText(user_status);
//        }
//        public void setUser_image( String user_image){
//            CircleImageView image = mView.findViewById(R.id.all_user_profile_image);
//            Picasso.get().load(user_image).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(image);
//        }