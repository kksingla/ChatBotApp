package com.docsapp.db.processor;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.docsapp.bean.model.MyChatHead;
import com.docsapp.bean.model.MyMessage;
import com.docsapp.db.dao.MyChatHeadDoa;
import com.docsapp.db.dao.MyMessageDoa;

/**
 * Created by kushaal singla on 02-Jul-18.
 */
@Database(entities = {MyChatHead.class,
        MyMessage.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    private static MyDB INSTANCE;
    private static Migration Migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract MyChatHeadDoa myChatDao();

    public abstract MyMessageDoa myMessageDao();

    static MyDB getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDB.class, "MyDB.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
