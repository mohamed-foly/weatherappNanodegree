package com.example.mohamed.weatherapp.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohamed.weatherapp.DB.DAO.AreaModelDao;
import com.example.mohamed.weatherapp.Models.AreaModel;

@Database(entities = {AreaModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "weather_app_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract AreaModelDao areaModelDao();

}