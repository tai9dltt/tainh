//package com.example.i_tainh.democonnectfirebase.Fragment;
//
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.i_tainh.democonnectfirebase.Model.Friends;
//import com.example.i_tainh.democonnectfirebase.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class FriendsFragment extends Fragment {
//    private RecyclerView myFriendLists;
//    private DatabaseReference friendsReference;
//    private FirebaseAuth mAuth;
//    String online_user_id;
//    private View myMainViewl;
//    private DatabaseReference userReference;
//
//
//
//
//    public FriendsFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//          myMainViewl =  inflater.inflate(R.layout.fragment_friends, container, false);
//
//          myFriendLists = myMainViewl.findViewById(R.id.friends_list);
//          online_user_id = mAuth.getCurrentUser().getUid();
//          friendsReference = FirebaseDatabase.getInstance().getReference().child("Friends").child(online_user_id);
//          userReference = FirebaseDatabase.getInstance().getReference().child("Users");
//
//          myFriendLists.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//        return myMainViewl;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Friends> options = new FirebaseRecyclerOptions.Builder<Friends>()
//                .setQuery(friendsReference, Friends.class)
//                .build();
//
//        userReference.child(online_user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String username = dataSnapshot.child("user_name").getValue().toString();
//                String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        FirebaseRecyclerAdapter<Friends, FriendsViewHolder> friendsRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>() {
//            @Override
//            protected void onBindViewHolder(@NonNull FriendsViewHolder holder, int position, @NonNull Friends model) {
//
//
//            }
//
//            @NonNull
//            @Override
//            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friend_display_layout, viewGroup,false);
//                FriendsViewHolder viewHolder = new FriendsViewHolder(view);
//                return viewHolder;
//            }
//        };
//
//    }
//
//    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
//
//        TextView name;
//        TextView date;
//        CircleImageView image;
//
//        public FriendsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.all_user_username);
//            date = itemView.findViewById(R.id.all_user_userDate);
//            image = itemView.findViewById(R.id.all_user_profile_image);
//
//        }
//    }
//}
