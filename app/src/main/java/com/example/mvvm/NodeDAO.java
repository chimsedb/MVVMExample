package com.example.mvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NodeDAO {
    @Insert
    void insert(Node node);

    @Update
    void update(Node node);

    @Delete
    void delete(Node node);

    @Query("Delete From node_table")
    void deleteAllNode();

    @Query("Select * From node_table order by priority DESC")
    LiveData<List<Node>> getAllNodes();
}
