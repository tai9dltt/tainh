package com.example.i_tainh.demoorderfood.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class NhanVien  extends BaseObservable {
   private int MANV;
   private String tenDN, matKhau, gioiTinh, ngaySinh, cmnd;

    public NhanVien(int MANV, String tenDN, String matKhau, String gioiTinh, String ngaySinh, String cmnd) {
        this.MANV = MANV;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.cmnd = cmnd;
    }

    public NhanVien(String tenDN, String matKhau, String gioiTinh, String ngaySinh, String cmnd) {

        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.cmnd = cmnd;
    }

    public NhanVien() {
    }

    @Bindable
    public int getMANV() {
        return MANV;
    }
    @Bindable
    public void setMANV(int MANV) {
        this.MANV = MANV;
    }
    @Bindable
    public String getTenDN() {
        return tenDN;
    }
    @Bindable
    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }
    @Bindable
    public String getMatKhau() {
        return matKhau;
    }
    @Bindable
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    @Bindable
    public String getGioiTinh() {
        return gioiTinh;
    }
    @Bindable
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    @Bindable
    public String getNgaySinh() {
        return ngaySinh;
    }
    @Bindable
    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    @Bindable
    public String getCmnd() {
        return cmnd;
    }
    @Bindable
    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
}
