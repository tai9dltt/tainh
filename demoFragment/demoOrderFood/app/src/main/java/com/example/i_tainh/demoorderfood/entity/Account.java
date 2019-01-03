package com.example.i_tainh.demoorderfood.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

@Entity(tableName = "Accounts", primaryKeys = {"id"})
public class Account  {


    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    @Ignore
    public Account(@NonNull int mId, String mUsername, String mPassword) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "mId=" + mId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
