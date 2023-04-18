package com.example.assessment8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assessment8.db.Drink;

@Database(version = 1, entities = {Drink.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrinkDao drinkDao();
}
