package com.example.i_tainh.demoorderfood.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.i_tainh.demoorderfood.DAO.TableDAO;
import com.example.i_tainh.demoorderfood.R;

public class AddTable extends AppCompatActivity implements View.OnClickListener {

    EditText txt_add_dialog;
    Button btnOK;
    TableDAO tableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

    txt_add_dialog = findViewById(R.id.txt_add_dialog);
    btnOK  = findViewById(R.id.btn_ok_dialog);

    tableDAO = new TableDAO(this);
    btnOK.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String tableName = txt_add_dialog.getText().toString();
        if(tableName!= null || !tableName.equals("")){
           boolean check =  tableDAO.addTable(tableName);
           Intent intent = new Intent();
           intent.putExtra("data",check);
            setResult(Activity.RESULT_OK, intent);
            Log.d("CheckResult", check+ "");
            finish();
        }
    }
}
