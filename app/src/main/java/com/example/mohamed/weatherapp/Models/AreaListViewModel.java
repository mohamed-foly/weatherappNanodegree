package com.example.mohamed.weatherapp.Models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mohamed.weatherapp.DB.AppDatabase;

import java.util.List;

public class AreaListViewModel extends AndroidViewModel {
    private final LiveData<List<AreaModel>> areaModels;
    private AppDatabase appDatabase;

    public AreaListViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        areaModels = appDatabase.areaModelDao().getAll();
    }

    public LiveData<List<AreaModel>> getAreaModels() {
        return areaModels;
    }

//    public void deleteItem(BorrowModel borrowModel) {
//        new deleteAsyncTask(appDatabase).execute(borrowModel);
//    }
}
