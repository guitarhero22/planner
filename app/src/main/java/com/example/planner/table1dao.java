package com.example.planner;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Collection;
import java.util.List;

@Dao
public interface table1dao {

    @Query("select * from level1")
    List<table1> getAll();

    @Query("select * from level1 where child = :c")
    List<table1> getDes(String c);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(table1 ... table1s);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(table1 t);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(table1 t);

    @Query("delete from level1 where child = :c")
    void delete(String c);

    @Query("select * from level1 where date = :d")
    List<table1> getDate(String d);

}
