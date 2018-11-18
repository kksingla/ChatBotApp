package com.docsapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.docsapp.bean.model.MyChatHead;

import java.util.List;

/**
 * Created by kushaal singla on 02-Jul-18.
 */
@Dao
public abstract class MyChatHeadDoa {
    @Query("SELECT * FROM MyChatHead")
    public abstract List<MyChatHead> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(MyChatHead chatHead);
}
