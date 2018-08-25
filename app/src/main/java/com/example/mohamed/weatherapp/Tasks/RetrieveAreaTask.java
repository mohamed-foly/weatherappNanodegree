package com.example.mohamed.weatherapp.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Interfaces.AreaRetrieved;
import com.example.mohamed.weatherapp.Models.AreaModel;

public class RetrieveAreaTask extends AsyncTask<Integer,Void,AreaModel> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private AreaRetrieved areaRetrieved;

    public RetrieveAreaTask(AreaRetrieved areaRetrieved, Context context ){
        this.context = context;
        this.areaRetrieved=areaRetrieved;
    }
    @Override
    protected AreaModel doInBackground(Integer... integers) {
        AppDatabase appDatabase;
        appDatabase = AppDatabase.getDatabase(context);
        return appDatabase.areaModelDao().getAllstat().get(integers[0]);
    }
    @Override
    protected void onPostExecute(AreaModel areaModel) {
        super.onPostExecute(areaModel);
        areaRetrieved.onAreaRetrieved(areaModel);
    }
}