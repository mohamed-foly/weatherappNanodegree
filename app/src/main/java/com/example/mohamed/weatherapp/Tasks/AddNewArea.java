package com.example.mohamed.weatherapp.Tasks;

import android.os.AsyncTask;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Interfaces.AddNewAreaInterface;
import com.example.mohamed.weatherapp.Models.AreaModel;

public class AddNewArea extends AsyncTask <AreaModel, Void, Long> {
    private AppDatabase db;
    private AddNewAreaInterface addNewAreaInterface;
   public AddNewArea(AppDatabase appDatabase ,AddNewAreaInterface addNewAreaInterface) {
        db = appDatabase;
        this.addNewAreaInterface = addNewAreaInterface;
    }
    @Override
    protected Long doInBackground(AreaModel... areaModels) {
        long result = db.areaModelDao().addArea(areaModels[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long res) {
        super.onPostExecute(res);
        addNewAreaInterface.OnAreaAdded(res);
    }
}
