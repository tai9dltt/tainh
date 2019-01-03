package com.example.i_tainh.demoorderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.i_tainh.demoorderfood.entity.NhanVien;
import com.example.i_tainh.demoorderfood.Database.CreateDatabase;

public class NhanVienDAO {
    SQLiteDatabase sqLiteDatabase;
    NhanVienDAO nhanVienDAO;
    public  NhanVienDAO (Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();


    }
    public long themNhanVien(NhanVien dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, dto.getTenDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, dto.getCmnd());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, dto.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, dto.getMatKhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, dto.getNgaySinh());



        long check = sqLiteDatabase.insert(CreateDatabase.TB_NHANVIEN,null, contentValues);
        return check;
    }

    public boolean kiemTraNhanVien(){
        String query = "Select * from " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.getCount() != 0)
            return true;
        else
            return false;
    }


}
