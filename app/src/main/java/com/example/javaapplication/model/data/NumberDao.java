package com.example.javaapplication.model.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface NumberDao {

    @Query("SELECT * FROM numbers_table")
    List<NumberData> getNumbers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNumberData(NumberData... data);

    @Delete
    void delete(NumberData data);

}
