package com.example.mohamed.weatherapp.DB.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.mohamed.weatherapp.DateConverter;
import com.example.mohamed.weatherapp.Models.AreaModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface AreaModelDao {

    @Query("select * from AreaModel")
    LiveData<List<AreaModel>> getAll();
    @Query("select * from AreaModel")
    List<AreaModel> getAllstat();

    @Query("select * from AreaModel where id = :id")
    AreaModel getById(String id);

    @Insert(onConflict = REPLACE)
    Long addArea(AreaModel areaModel);

    @Update
    int updateArea(AreaModel areaModel);

    @Delete
    void deleteArea(AreaModel areaModel);

}