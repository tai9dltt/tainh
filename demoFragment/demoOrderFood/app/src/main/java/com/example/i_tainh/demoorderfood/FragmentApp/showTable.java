package com.example.i_tainh.demoorderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.i_tainh.demoorderfood.Activity.AddTable;
import com.example.i_tainh.demoorderfood.Adapter.ShowTableAdapter;
import com.example.i_tainh.demoorderfood.DAO.TableDAO;
import com.example.i_tainh.demoorderfood.R;
import com.example.i_tainh.demoorderfood.entity.Table;

import java.util.List;

public class showTable extends Fragment {

    public static  int REQUEST_CODE_ADD = 111;
    GridView gridView;
    List<Table> tableList;
    TableDAO tableDAO;
    ShowTableAdapter showTableAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_table,container ,false);
        setHasOptionsMenu(true);
        gridView = (GridView) view.findViewById(R.id.viewShowTable);
        tableDAO = new TableDAO(getActivity());
        tableList = tableDAO.getAllTable();
        showListTable();
        return view;


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1, R.id.itThembanAn, 1 , R.string.thembanAn);
        item.setIcon(R.drawable.thembanan);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThembanAn:
                Intent intent = new Intent(getActivity(), AddTable.class );
                startActivityForResult(intent,REQUEST_CODE_ADD);
                break;
        }

        return true;
    }

    public void showListTable(){
        tableList = tableDAO.getAllTable();
        showTableAdapter = new ShowTableAdapter(getActivity(),R.layout.custom_layout_showtable,tableList);
        gridView.setAdapter(showTableAdapter);
        showTableAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean check = intent.getBooleanExtra("data", false);
                if(check){
                   showListTable();
                    Toast.makeText(getActivity(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getString(R.string.add_fail), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
