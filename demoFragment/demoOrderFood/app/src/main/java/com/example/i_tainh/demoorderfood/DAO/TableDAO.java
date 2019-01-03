package com.example.i_tainh.demoorderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.i_tainh.demoorderfood.Database.CreateDatabase;
import com.example.i_tainh.demoorderfood.entity.Table;

import java.util.ArrayList;
import java.util.List;

public class TableDAO   {

    SQLiteDatabase sqLiteDatabase;

    public TableDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();

    }

    public boolean addTable(String tableName){
        ContentValues contentValues  = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tableName);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG," false");

        long check = sqLiteDatabase.insert(CreateDatabase.TB_BANAN, null,contentValues);
        if(check != 0){
            return true;
        }else
            return false;
    }


    public List<Table> getAllTable(){
        List<Table> listTable = new ArrayList<>();

        String query = "Select * from "+CreateDatabase.TB_BANAN;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Table table = new Table();
            table.setTableID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            table.setTableName(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));
            listTable.add(table);
            cursor.moveToNext();
        }

        return listTable;
    }
}
