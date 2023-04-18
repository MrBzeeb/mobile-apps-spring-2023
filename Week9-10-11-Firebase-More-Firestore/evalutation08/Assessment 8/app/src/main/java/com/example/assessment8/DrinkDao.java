package com.example.assessment8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assessment8.db.Drink;

import java.util.List;

@Dao
public interface DrinkDao {
    @Query("SELECT * FROM drinks")
    List<Drink> getAll();

    @Update
    void update(Drink drink);

    @Insert
    void insertAll(Drink... drinks);

    @Delete
    void delete(Drink drink);

    @Query("DELETE FROM drinks")
    void deleteAll();
}
