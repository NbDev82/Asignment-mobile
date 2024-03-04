package com.example.calculator.history;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY savedTimestamp DESC")
    List<History> getAll();

    @Insert
    void insert(History history);

    @Query("DELETE FROM history")
    void deleteAll();
}
