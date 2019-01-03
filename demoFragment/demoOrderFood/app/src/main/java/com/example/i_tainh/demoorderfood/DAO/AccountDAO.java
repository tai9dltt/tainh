package com.example.i_tainh.demoorderfood.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Query;

import com.example.i_tainh.demoorderfood.entity.Account;

import java.util.List;

@Dao
public interface AccountDAO {
    @Query("select * from Accounts")
    List<Account> selectAllAccount(int id);
}
