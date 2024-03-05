package com.example.calculator.history;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Ignore;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1)
public abstract class CalculatorDatabase extends RoomDatabase {

    private static CalculatorDatabase database;

    @Ignore
    public CalculatorDatabase() {
    }

    public abstract HistoryDao historyDao();

    public synchronized static CalculatorDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            CalculatorDatabase.class, "calculator_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
