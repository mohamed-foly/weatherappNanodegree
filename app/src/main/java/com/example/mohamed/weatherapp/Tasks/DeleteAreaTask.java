package com.example.mohamed.weatherapp.Tasks;

import android.os.AsyncTask;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Models.AreaModel;

public class DeleteAreaTask extends AsyncTask<AreaModel, Void, Void> {
    private AppDatabase db;

    public DeleteAreaTask(AppDatabase appDatabase) {
        db = appDatabase;
    }

    @Override
    protected Void doInBackground(final AreaModel... params) {
        db.areaModelDao().deleteArea(params[0]);
        return null;
    }
}

