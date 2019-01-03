package com.example.i_tainh.demoorderfood.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {

   final public static String TB_NHANVIEN = "NHANVIEN";
    final public static String TB_MONAN = "MONAN";
    final public static String TB_LOAIMONAN = "LOAIMONAN";
    final public static String TB_BANAN = "BANAN";
    final public static String TB_GOIMON = "GOIMON";
    final public static String TB_CHITIETGOIMON = "CHITIETGOIMON";

    final public static String TB_NHANVIEN_MANV = "MANV";
    final public static String TB_NHANVIEN_TENDN = "TENDN";
    final public static String TB_NHANVIEN_MATKHAU = "MATKHAU";
    final public static String TB_NHANVIEN_GIOITINH = "GIOITINH";
    final public static String TB_NHANVIEN_NGAYSINH = "NGAYSINH";
    final public static String TB_NHANVIEN_CMND = "CMND";

    final public static String TB_MONAN_MAMON = "MAMON";
    final public static String TB_MONAN_TENMONAN = "TENMONAN";
    final public static String TB_MONAN_GIATIEN = "GIATIEN";
    final public static String TB_MONAN_MALOAI = "MALOAI";

    final public static String TB_LOAIMONAN_MALOAI = "MALOAI";
    final public static String TB_LOAIMONAN_TENLOAI = "TENLOAI";

    final public static String TB_BANAN_MABAN = "MABAN";
    final public static String TB_BANAN_TENBAN = "TENBAN";
    final public static String TB_BANAN_TINHTRANG = "TINHTRANG";

    final public static String TB_GOIMON_MAGOIMON = "MAGOIMON";
    final public static String TB_GOIMON_MANV = "MANV";
    final public static String TB_GOIMON_NGAYGOI = "NGAYGOI";
    final public static String TB_GOIMON_TINHTRANG = "TINHTRANG";
    final public static String TB_GOIMON_MABAN = "MABAN";

    final public static String TB_CHITIETGOIMON_MAGOIMON  = "MAGOIMON";
    final public static String TB_CHITIETGOIMON_MAMONAN  = "MAMONAN";
    final public static String TB_CHITIETGOIMON_SOLUONG  = "SOLUONG";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_NHANVIEN = "CREATE TABLE " + TB_NHANVIEN +" ( " + TB_NHANVIEN_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TB_NHANVIEN_TENDN +" TEXT, " + TB_NHANVIEN_MATKHAU + " TEXT,"
            + TB_NHANVIEN_GIOITINH + " TEXT," + TB_NHANVIEN_NGAYSINH + " TEXT, "
            + TB_NHANVIEN_CMND + " INTEGER )";

    String tb_BANAN = "CREATE TABLE "+ TB_BANAN + " ( "+TB_BANAN_MABAN+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TB_BANAN_TENBAN + " TEXT," +TB_BANAN_TINHTRANG + " TEXT )";

    String tb_MONAN = "CREATE TABLE " + TB_MONAN + " ( "+TB_MONAN_MAMON + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TB_MONAN_TENMONAN + " TEXT, " + TB_MONAN_MALOAI + " INTEGER, "
            + TB_MONAN_GIATIEN + " TEXT ) ";
    String tb_LOAIMONAN = "CREATE TABLE "+ TB_LOAIMONAN + " ( "+ TB_LOAIMONAN_MALOAI + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TB_LOAIMONAN_TENLOAI + " TEXT ) ";
    String tb_GOIMON = "CREATE TABLE " + TB_GOIMON_MAGOIMON + " ( " +TB_GOIMON_MAGOIMON + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TB_GOIMON_MABAN + " INTEGER, " + TB_GOIMON_MANV + " INTEGER, "
            +TB_GOIMON_NGAYGOI + " TEXT, " + TB_GOIMON_TINHTRANG + " TEXT )";
    String tb_CHITIETGOIMON = "CREATE TABLE " + TB_CHITIETGOIMON + " ( "+TB_CHITIETGOIMON_MAGOIMON + " INTEGER, "
            + TB_CHITIETGOIMON_MAMONAN + " INTEGER, " + TB_CHITIETGOIMON_SOLUONG
            + " INTEGER, PRIMARY KEY ( "+ TB_CHITIETGOIMON_MAGOIMON + "," +TB_CHITIETGOIMON_MAMONAN + " ))";
        db.execSQL(tb_NHANVIEN);
        db.execSQL(tb_BANAN);
        db.execSQL(tb_MONAN);
        db.execSQL(tb_LOAIMONAN);
        db.execSQL(tb_GOIMON);
        db.execSQL(tb_CHITIETGOIMON);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public CreateDatabase(Context context ) {
        super(context, "OderFood", null, 1);
    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }


}
