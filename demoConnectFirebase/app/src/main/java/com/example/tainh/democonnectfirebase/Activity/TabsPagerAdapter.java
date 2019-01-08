package com.example.tainh.democonnectfirebase.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tainh.democonnectfirebase.Fragment.ChatsFragment;
import com.example.tainh.democonnectfirebase.Fragment.FriendsFragment;
import com.example.tainh.democonnectfirebase.Fragment.RequestFragment;

class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;
            case 1 :
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
            case 2 :
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0 :
                return "Requests";
            case 1 :
                return "Chats";
            case 2 :
                return "Friends";
            default:
                return null;
        }
    }
}
