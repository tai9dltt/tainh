package com.example.i_tainh.demoorderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.i_tainh.demoorderfood.Activity.AddCruiseActivity;
import com.example.i_tainh.demoorderfood.R;

public class showMenuCruise extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_eating_cruise,container,false);
        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1, R.id.itThemThucDon, 1 , R.string.thumthudon);
        item.setIcon(R.drawable.logodangnhap);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent intent = new Intent(getActivity(), AddCruiseActivity.class);
                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
