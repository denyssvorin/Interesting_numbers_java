package com.example.javaapplication.model.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NumberData.class}, version = 1)
public abstract class NumberDatabase extends RoomDatabase {

    public abstract NumberDao numberDao();

    private static NumberDatabase INSTANCE;

    public static NumberDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NumberDatabase.class, "number_db")
                    //.allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}