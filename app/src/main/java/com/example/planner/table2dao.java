package com.example.planner;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface table2dao {

    @Query("select * from level2")
    List<table2> getAll();

    @Query("select * from level2 where parent = :par")
    List<table2> getChildren(String par);

    @Query("select * from level2 where child = :c")
    List<table2> getDes(String c);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(table2 ... table2s);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(table2 t);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(table2 t);

    @Delete
    void delete(table2 t);

    @Query("select * from level2 where date = :d")
    List<table2> getDate(String d);

}
