package com.docsapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.docsapp.bean.model.MyMessage;

import java.util.List;

/**
 * Created by kushaal singla on 02-Jul-18.
 */
@Dao
public abstract class MyMessageDoa {
    @Query("SELECT * FROM MyMessage where externalId=:s order by createdOn")
    public abstract List<MyMessage> getAll(String s);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAll(List<MyMessage> myMessages);

}
