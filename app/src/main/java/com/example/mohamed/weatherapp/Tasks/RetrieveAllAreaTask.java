package com.example.mohamed.weatherapp.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Interfaces.AllAreaRetrieved;
import com.example.mohamed.weatherapp.Models.AreaModel;

import java.util.List;

public class RetrieveAllAreaTask extends AsyncTask<Void,Void,List<AreaModel>> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private AllAreaRetrieved areaRetrieved;

    public RetrieveAllAreaTask(AllAreaRetrieved areaRetrieved, Context context ){
        this.context = context;
        this.areaRetrieved=areaRetrieved;
    }
    @Override
    protected List<AreaModel>  doInBackground(Void... voids) {
        AppDatabase appDatabase;
        appDatabase = AppDatabase.getDatabase(context);
        return appDatabase.areaModelDao().getAllstat();
    }
    @Override
    protected void onPostExecute(List<AreaModel> areasModel) {
        super.onPostExecute(areasModel);
        areaRetrieved.onAllAreaRetrieved(areasModel);
    }
}